package com.snakeremake.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

public class StaticLevelView extends BaseLevelView {

	public StaticLevelView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId) {
		super(context, snakePosition, numberOfBlocks, spawnFruits, levelResourceId);
		onCreate();
	}

	// The visible blocks don't change
	@Override
	protected int visibleBlocksY() {
		return levelScaledBitmap.numBlocksY();
	}
	@Override
	protected int visibleBlocksX() {
		return levelScaledBitmap.numBlocksX();
	}

	// Static -- visible area doesn't need to update
	@Override
	protected void updateVisibleArea() {}

	@Override
	protected Bitmap visibleLevelBitmap() {
		return levelScaledBitmap.getScaledBitmap();
	}

    @Override
    protected Bitmap visibleFruitBitmap() {
        return fruitScaledBitmap.getScaledBitmap();
    }

}
