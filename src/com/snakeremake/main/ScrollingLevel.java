package com.snakeremake.main;

import android.content.Context;
import android.graphics.Point;

import com.snakeremake.views.BaseLevelView;
import com.snakeremake.views.ScrollingLevelView;

public class ScrollingLevel extends Level {

	private Point visibleAreaPosition;
	private Point visibleBlocks;

	public ScrollingLevel(String name, int levelID, LevelType type, Point spawnPoint,
			int snakeSize, boolean spawnFruits, int levelResourceID,
			Point visibleAreaPosition, Point visibleBlocks) {
		super(name, levelID, type, spawnPoint, snakeSize, spawnFruits,
				levelResourceID);
		this.visibleAreaPosition = visibleAreaPosition;
		this.visibleBlocks = visibleBlocks;
	}

	@Override
	public BaseLevelView getView(Context ctx) {
		return new ScrollingLevelView(ctx, spawnPoint, snakeSize, spawnFruits,
				levelResourceID, visibleAreaPosition, visibleBlocks);
	}
}
