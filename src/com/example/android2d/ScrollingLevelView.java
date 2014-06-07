package com.example.android2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

public class ScrollingLevelView extends BaseLevelView {

	private Bitmap visibleBitmap;
	private Point visibleBlocks;
	private Point visibleAreaPosition;
	private final int scrollDistance = 5;

	public ScrollingLevelView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId,
			Point visibleAreaPosition, Point visibleBlocks) {
		super(context, snakePosition, numberOfBlocks, spawnFruits,
				levelResourceId);
		this.visibleBlocks = new Point(visibleBlocks);
		this.visibleAreaPosition = new Point(visibleAreaPosition);

		onCreate();

		setVisibleArea(visibleAreaPosition, visibleBlocks);
	}

	@Override
	protected int visibleBlocksX() {
		return visibleBlocks.x;
	}

	@Override
	protected int visibleBlocksY() {
		return visibleBlocks.y;
	}

	@Override
	protected Bitmap visibleBitmap() {
		return visibleBitmap;
	}

	@Override
	protected void updateVisibleArea() {
		if (currentRelativePosition().x < scrollDistance
				&& visibleAreaPosition.x > 0) {
			scroll(Direction.LEFT);
		} else if (currentRelativePosition().x > visibleBlocks.x
				- scrollDistance
				&& visibleAreaPosition.x + visibleBlocks.x < resizedBg.numBlocksX()) {
			scroll(Direction.RIGHT);
		} else if (currentRelativePosition().y < scrollDistance
				&& visibleAreaPosition.y > 0) {
			scroll(Direction.UP);
		} else if (currentRelativePosition().y > visibleBlocks.y
				- scrollDistance
				&& visibleAreaPosition.y + visibleBlocks.y < resizedBg.numBlocksY()) {
			scroll(Direction.DOWN);
		} else {
			setVisibleArea(visibleAreaPosition, visibleBlocks);
		}
	}

	private void setVisibleArea(Point origin, Point numBlocks) {
		int originPixelsX = origin.x * resizedBg.getWidth()
				/ resizedBg.numBlocksX();
		int originPixelsY = origin.y * resizedBg.getHeight()
				/ resizedBg.numBlocksY();
		int numPixelsX = numBlocks.x * resizedBg.getWidth()
				/ resizedBg.numBlocksX();
		int numPixelsY = numBlocks.y * resizedBg.getHeight()
				/ resizedBg.numBlocksY();
		visibleBitmap = Bitmap.createBitmap(resizedBg.getScaledBitmap(),
				originPixelsX, originPixelsY, numPixelsX, numPixelsY);
		visibleAreaPosition = new Point(origin);
		visibleBlocks = new Point(numBlocks);
	}

	private Point currentRelativePosition() {
		return new Point(currentPosition().x - visibleAreaPosition.x,
				currentPosition().y - visibleAreaPosition.y);
	}

	private void scroll(Direction direction) {
		switch (direction) {
		case UP:
			setVisibleArea(new Point(visibleAreaPosition.x,
					visibleAreaPosition.y - 1), visibleBlocks);
			break;
		case DOWN:
			setVisibleArea(new Point(visibleAreaPosition.x,
					visibleAreaPosition.y + 1), visibleBlocks);
			break;
		case LEFT:
			setVisibleArea(new Point(visibleAreaPosition.x - 1,
					visibleAreaPosition.y), visibleBlocks);
			break;
		case RIGHT:
			setVisibleArea(new Point(visibleAreaPosition.x + 1,
					visibleAreaPosition.y), visibleBlocks);
		}
	}

}
