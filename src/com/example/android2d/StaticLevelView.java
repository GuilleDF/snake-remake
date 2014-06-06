package com.example.android2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

public class StaticLevelView extends BaseLevelView {

	public StaticLevelView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId) {
		super(context, snakePosition, numberOfBlocks, spawnFruits, levelResourceId);
		onCreate();
	}

	@Override
	protected int visibleBlocksY() {
		return resizedBg.numBlocksY();
	}

	@Override
	protected int visibleBlocksX() {
		return resizedBg.numBlocksX();
	}

	@Override
	protected void updateVisibleArea() {}

	@Override
	protected Bitmap visibleBitmap() {
		return resizedBg.getScaledBitmap();
	}

}
