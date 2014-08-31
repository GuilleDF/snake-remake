package com.snakeremake.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.util.Log;

import com.snakeremake.R;
import com.snakeremake.views.BaseLevelView;

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
			Log.e("Snake-Remake", "Unexpected error");
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

	public abstract BaseLevelView getView(Context context);

}
