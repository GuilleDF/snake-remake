package com.snakeremake.views;

import com.snakeremake.utils.Direction;

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

	/**
	 * If the snake is at {@link #scrollDistance} from the edge and we haven't
	 * reached {@link com.snakeremake.views.BaseLevelView#levelScaledBitmap levelScaledBitmap}'s
	 * bounds, it scrolls the bitmap.
	 */
	@Override
	protected void updateVisibleArea() {
		if (currentRelativePosition().x < scrollDistance
				&& visibleAreaPosition.x > 0) {
			scroll(Direction.LEFT);
		} else if (currentRelativePosition().x > visibleBlocks.x
				- scrollDistance
				&& visibleAreaPosition.x + visibleBlocks.x < levelScaledBitmap
						.numBlocksX()) {
			scroll(Direction.RIGHT);
		} else if (currentRelativePosition().y < scrollDistance
				&& visibleAreaPosition.y > 0) {
			scroll(Direction.UP);
		} else if (currentRelativePosition().y > visibleBlocks.y
				- scrollDistance
				&& visibleAreaPosition.y + visibleBlocks.y < levelScaledBitmap
						.numBlocksY()) {
			scroll(Direction.DOWN);
		} else {
			setVisibleArea(visibleAreaPosition, visibleBlocks);
		}
	}

	/**
	 * Sets the visible area
	 * 
	 * @param origin
	 *            Start of visible area
	 * @param numBlocks
	 *            Number of blocks in each axis
	 */
	private void setVisibleArea(Point origin, Point numBlocks) {

		// First we convert 'blocks' into pixels
		int originPixelsX = origin.x * levelScaledBitmap.getWidth()
				/ levelScaledBitmap.numBlocksX();
		int originPixelsY = origin.y * levelScaledBitmap.getHeight()
				/ levelScaledBitmap.numBlocksY();
		int numPixelsX = numBlocks.x * levelScaledBitmap.getWidth()
				/ levelScaledBitmap.numBlocksX();
		int numPixelsY = numBlocks.y * levelScaledBitmap.getHeight()
				/ levelScaledBitmap.numBlocksY();

		// Then we make the visible area bitmap
		visibleBitmap = Bitmap.createBitmap(
				levelScaledBitmap.getScaledBitmap(), originPixelsX,
				originPixelsY, numPixelsX, numPixelsY);

		// We update the position and blocks fields
		visibleAreaPosition = new Point(origin);
		visibleBlocks = new Point(numBlocks);
	}

	/**
	 * The snake's position relative to the visible bitmap
	 * 
	 * @return position
	 */
	private Point currentRelativePosition() {
		return new Point(currentPosition().x - visibleAreaPosition.x,
				currentPosition().y - visibleAreaPosition.y);
	}

	/**
	 * Advances the visible area by one block.<br>
	 * Caller must ensure it doesn't go out of bounds.
	 * 
	 * @param direction
	 *            The direction on which to advance
	 */
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
