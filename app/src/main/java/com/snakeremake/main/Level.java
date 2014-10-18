package com.snakeremake.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import com.snakeremake.R;
import com.snakeremake.activity.LevelActivity;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.menu.Action;
import com.snakeremake.menu.ActionGame;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;
import com.snakeremake.render.TextureMapper;
import com.snakeremake.utils.Direction;
import com.snakeremake.utils.ExtraTools;
import com.snakeremake.views.LevelView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public abstract class Level {
	public static List<Level> levels = new ArrayList<Level>();
	
	private static Context context;
	private static AssetManager assetManager;

    public static void loadLevels(Context ctx) {
		context = ctx;
		assetManager = context.getAssets();

		String[] levelFileNames = new String[1];
		try {
			levelFileNames = assetManager.list("levels");
		} catch (IOException e) {
			Log.e("Snake-Remake", e.toString());
		}

		for (String fileName : levelFileNames) {
			if (fileName.substring(fileName.lastIndexOf("."))
							.equals(".level")) {
				levels.add(loadLevel(fileName));
			}
		}
	}

	private static Level loadLevel(String fileName) {
		try {
			Properties prop = new Properties();
			InputStream stream = assetManager.open("levels/" + fileName);
			prop.load(stream);
			String name = prop.getProperty("name");
			LevelType type = LevelType.valueOf(prop.getProperty("type"));
			int levelID = Integer.parseInt(prop.getProperty("levelID"));
			Point spawnPoint = decodePoint(prop.getProperty("spawnPoint"));
			int snakeSize = Integer.parseInt(prop.getProperty("snakeSize"));
			boolean spawnFruits = Boolean.parseBoolean(prop
					.getProperty("spawnFruits"));
			int levelResourceID = R.drawable.class.getDeclaredField(
					prop.getProperty("levelImageName")).getInt(null);
			if (type.equals(LevelType.fixed)) {
				return new StaticLevel(name, levelID, type, spawnPoint, snakeSize,
						spawnFruits, levelResourceID);
			} else {
				Point visibleAreaPosition = decodePoint(prop
						.getProperty("visibleAreaPosition"));
				Point visibleBlocks = decodePoint(prop
						.getProperty("visibleBlocks"));
				return new ScrollingLevel(name, levelID, type, spawnPoint, snakeSize,
						spawnFruits, levelResourceID, visibleAreaPosition,
						visibleBlocks);
			}
		} catch (Exception e) {
			Log.e("Snake-Remake", "Exception in loadLevel(): " + e.toString());
		}
		return null;
	}

	public String getName() {
		return name;
	}

	private static Point decodePoint(String property) {
		String[] str = property.split(",");
		return new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
	}

	protected String name;
	protected LevelType type;
	protected int levelID;
	protected Point spawnPoint;
	protected int snakeSize;
	protected boolean spawnFruits;
	protected int levelResourceID;

    protected LevelView view;

	public Level(String name, int levelID, LevelType type, Point spawnPoint, int snakeSize,
			boolean spawnFruits, int levelResourceID) {
		this.name = name;
		this.levelID = levelID;
		this.type = type;
		this.spawnPoint = spawnPoint;
		this.snakeSize = snakeSize;
		this.spawnFruits = spawnFruits;
		this.levelResourceID = levelResourceID;
	}

	public abstract LevelView getView(Context context);

	public static HashMap<String,Action> generateHashMap() {
		HashMap<String, Action> map = new HashMap<String,Action>();
		for(Level l:levels){
			map.put(l.getName(), new ActionGame());
		}
		return map;
	}

    //------------------------------------------------------------------//

    private Snake snake;
    private Bitmap background;
    /**
     * Stores the level, fruit and snake bitmaps, in that order
     */
    private ScaledBitmap[] maps = new ScaledBitmap[3]; //0 is level, 1 is fruits, 2 is snake
    private Point screenSize;
    private int orientation;
    private TextureMapper mapper;

    private int score;
    private boolean paused;

    private int tickCount;
    private int speed = 5; // Default for now

    /**
     * Creates the snake and gives it the desired length
     * Caller must ensure snake doesn't go out of bounds while adding blocks
     */
    public void generateSnake() {
        snake = new Snake(spawnPoint.x, spawnPoint.y);

        maps[2]=new ScaledBitmap(
                Bitmap.createBitmap(background.getWidth(),
                        background.getHeight(), Bitmap.Config.ARGB_8888));

        snake.setScaledBitmap(maps[2]);

        snake.setDirection(Direction.UP);
        for (int i = 0; i < snakeSize; i++) {
            snake.addBlock();
        }
    }

    /**
     * Loads the map and creates the fruitmap
     */
    public void loadMap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        background = BitmapFactory.decodeResource(view.getResources(),
                levelID, options);

        maps[0] = new ScaledBitmap(background);

        maps[1] = new ScaledBitmap(
                Bitmap.createBitmap(background.getWidth(),
                        background.getHeight(), Bitmap.Config.ARGB_8888)
        );
       maps[1].getOriginalBitmap().eraseColor(TextureMap.TRANSPARENT);

    }

    /**
     * Calculates screen size and orientation
     */
    private void configureScreen() {
        screenSize = ExtraTools.getScreenSize(view.getContext());
        orientation = view.getResources().getConfiguration().orientation;
    }

    /**
     * Map has to be loaded and snake has to be generated
     * This should throw exceptions in the future
     */
    public void scaleMap() {
        configureScreen();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            int desiredWidth = screenSize.x * maps[0].numBlocksX()
                    / visibleBlocksX();

            for(ScaledBitmap map: maps)
                map.scaleByWidth(desiredWidth);

        } else {
            int desiredHeight = screenSize.y * maps[0].numBlocksY()
                    / visibleBlocksY();

            for(ScaledBitmap map: maps)
                map.scaleByHeight(desiredHeight);

        }
    }

    /**
     * Gives the fruitmap and levelmap the appropiate textures
     */
    public void mapTextures() {
        mapper = new TextureMapper(view.getResources());
        mapper.loadTextures();
        for(ScaledBitmap map: maps)
            mapper.mapBitmap(map);
    }




    public void onLose() {
        Game.inst().clock.stopClock();
        LevelActivity host = (LevelActivity) view.getContext();
        host.onGameOver(score);
    }

    public void onPauseButtonPressed() {
        paused = !paused;
        if (paused) {
            Game.inst().clock.pauseClock();
        } else {
            Game.inst().clock.resumeClock();
        }

    }



    /**
     * A clock is used to call this method periodically
     */
    public void onTick() {
        tickCount++;
        if(tickCount != (int)Game.inst().clock.getTicksPerSecond()/speed - 1) return;
        tickCount=0;

        snake.moveOnLevel(this);
        Point snakePosition = snake.getPosition();
        if (snake.hasEatenFruit()) {
            score++;

            // To 'eat' the fruit, we map where the fruit was to transparent
            maps[1].drawToOriginal(snakePosition.x, snakePosition.y,
                    TextureMap.TRANSPARENT);

            // And we render its texture
            mapper.mapBlock(snakePosition.x, snakePosition.y,
                    maps[1]);

            if(spawnFruits) {
                Point pos = ExtraTools.placeRandomFruit(this);
                mapper.mapBlock(pos.x, pos.y, maps[1]);
            }
        }
        snake.draw();
        mapper.mapSnake(snake);

        view.postInvalidate();
    }




    public void setScore(int score) {
        this.score = score;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getScore() {
        return score;
    }

    public int getOrientation() {
        return orientation;
    }

    public Point getScreenSize() {
        return screenSize;
    }

    public ScaledBitmap getMaps(int i) {
        return maps[i];
    }

    public Point currentPosition() { return snake.getPosition(); }


    //-----------------------------------------------------------------//

    public abstract void updateVisibleArea();

    public abstract Bitmap visibleLevelBitmap();

    public abstract Bitmap visibleFruitBitmap();

    public abstract int visibleBlocksX();

    public abstract int visibleBlocksY();

    public boolean isPaused() {
        return paused;
    }
}
