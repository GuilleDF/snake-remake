package com.snakeremake.main;

import android.content.Context;
import android.graphics.Point;

import com.snakeremake.views.BaseLevelView;
import com.snakeremake.views.StaticLevelView;

public class StaticLevel extends Level {



	public StaticLevel(String name, int levelID, LevelType type, Point spawnPoint,
			int snakeSize, boolean spawnFruits, int levelResourceID) {
		super(name, levelID, type, spawnPoint, snakeSize, spawnFruits, levelResourceID);
	}

	@Override
	public BaseLevelView getView(Context ctx) {
		return new StaticLevelView(ctx, spawnPoint,
				snakeSize, spawnFruits, levelResourceID);
				

	}

}
