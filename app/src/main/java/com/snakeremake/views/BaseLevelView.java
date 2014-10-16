package com.snakeremake.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.snakeremake.R;
import com.snakeremake.activity.BaseLevelActivity;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.main.Game;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMapper;
import com.snakeremake.utils.Direction;
import com.snakeremake.utils.ExtraTools;
import com.snakeremake.utils.GestureProcessor;

public abstract class BaseLevelView extends View {

	protected ScaledBitmap levelScaledBitmap;

	// ---------------------------------------//

	private Point screenSize;
	private Snake snake;

	private GestureDetector gestureDetector;

	private TextureMapper mapper;
	private int levelResourceId;
	private Point snakePosition;
	private int numberOfBlocks;
	private boolean spawnFruits;

	private Bitmap pause;
	private Paint paint;
	private boolean paused;

	private int orientation;

	private int score;

    private Bitmap background;

    private int tickCount;
    private int speed = 5; //This will be default for now

	public BaseLevelView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId) {
		super(context);
		this.levelResourceId = levelResourceId;
		this.snakePosition = snakePosition;
		this.numberOfBlocks = numberOfBlocks;
		this.spawnFruits = spawnFruits;

		// call onCreate(); when extending this class
	}

	protected void configureScreen() {
		screenSize = ExtraTools.getScreenSize(getContext());
		orientation = getResources().getConfiguration().orientation;
	}

	protected Point currentPosition() {
		return snakePosition;
	}

	private void drawMap(Canvas canvas) {
		updateVisibleArea();
		Bitmap bitmapToDraw = visibleBitmap();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			canvas.drawBitmap(bitmapToDraw, 0,
					(screenSize.y - bitmapToDraw.getHeight()) / 2, paint);
		} else {
			canvas.drawBitmap(bitmapToDraw,
					(screenSize.x - bitmapToDraw.getWidth()) / 2, 0, paint);

		}
	}

	private void generateSnake() {
        snake = new Snake(snakePosition.x, snakePosition.y);

        snake.setScaledBitmap(new ScaledBitmap(
                Bitmap.createBitmap(background.getWidth(),
                        background.getHeight(), Config.ARGB_8888)));

		snake.setDirection(Direction.UP);
		for (int i = 0; i < numberOfBlocks; i++) {
			snake.addBlock();
		}
	}

	public boolean isPaused() {
		return paused;
	}

	private void loadMap() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inScaled = false;
		background = BitmapFactory.decodeResource(getResources(),
				levelResourceId, options);
		levelScaledBitmap = new ScaledBitmap(background);

	}

	private void mapTextures() {
		mapper = new TextureMapper(getResources());
		mapper.loadTextures();
		mapper.mapBitmap(levelScaledBitmap);
	}

	protected void onCreate() {
		paused = false;
		score = 0;
		loadMap();
		generateSnake();
        scaleMap();

		gestureDetector = new GestureDetector(getContext(),
				new GestureProcessor(snake, this));

		if (spawnFruits) {
			ExtraTools.placeRandomFruit(levelScaledBitmap);
			ExtraTools.placeRandomFruit(levelScaledBitmap);
		}

		paint = new Paint();
		pause = BitmapFactory.decodeResource(getResources(), R.drawable.pause);

		mapTextures();
        Game.clock.setView(this);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(pause, 0, 0, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText("Score: " + score, screenSize.x - 10, 50, paint);
		// Rescale map on orientation changes
		if (orientation != getResources().getConfiguration().orientation) {
			scaleMap();
			mapTextures();
		}
		if (snake.hasDied())
			onLose();

		drawMap(canvas);
	}

	public void onLose() {
		Game.clock.stopClock();
		BaseLevelActivity host = (BaseLevelActivity) getContext();
		host.onGameOver(score);
	}

	public void onPauseButtonPressed() {
		paused = !paused;
		if (paused) {
			Game.clock.pauseClock();
		} else {
			Game.clock.resumeClock();
		}

	}

	/**
	 * A clock is used to call this method periodically
	 */
	public void onTick() {
        tickCount++;
        if(tickCount != (int)Game.clock.getTicksPerSecond()/speed - 1) return;
        tickCount=0;

		snake.moveOnBitmap(levelScaledBitmap);
		snakePosition = snake.getPosition();
		if (spawnFruits && snake.hasEatenFruit()) {
			score++;

			// To 'eat' the fruit, we map where the fruit was to white
			levelScaledBitmap.drawToOriginal(snakePosition.x, snakePosition.y,
					Color.WHITE);

			Point pos = ExtraTools.placeRandomFruit(levelScaledBitmap);
			mapper.mapBlock(pos.x, pos.y, levelScaledBitmap);
		}
		snake.draw();
		mapper.mapSnake(snake, levelScaledBitmap);
		postInvalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return gestureDetector.onTouchEvent(e);
	}

    /**
     * Map has to be loaded and snake has to be generated
     * This should throw exceptions in the future
     */
	protected void scaleMap() {
		configureScreen();
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			int desiredWidth = screenSize.x * levelScaledBitmap.numBlocksX()
					/ visibleBlocksX();
			levelScaledBitmap.scaleByWidth(desiredWidth);
			snake.getScaledBitmap().scaleByWidth(desiredWidth);
		} else {
			int desiredHeight = screenSize.y * levelScaledBitmap.numBlocksY()
					/ visibleBlocksY();
			levelScaledBitmap.scaleByHeight(desiredHeight);
			snake.getScaledBitmap().scaleByHeight(desiredHeight);
		}
	}

	protected abstract void updateVisibleArea();

	protected abstract Bitmap visibleBitmap();

	protected abstract int visibleBlocksX();

	protected abstract int visibleBlocksY();

}
