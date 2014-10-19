package com.snakeremake.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.snakeremake.utils.Direction;
import com.snakeremake.views.LevelView;

public class ScrollingLevel extends Level {

	private Point visibleAreaPosition;
	private Point visibleBlocks;
    private final int scrollDistance = 5; //Default for now

    public ScrollingLevel(String name, int levelID, LevelType type, Point spawnPoint,
			int snakeSize, boolean spawnFruits, int levelResourceID,
			Point visibleAreaPosition, Point visibleBlocks) {
		super(name, levelID, type, spawnPoint, snakeSize, spawnFruits,
				levelResourceID);
		this.visibleAreaPosition = visibleAreaPosition;
		this.visibleBlocks = visibleBlocks;

	}

	@Override
	public LevelView getView(Context ctx) {
        view = new LevelView(ctx, spawnFruits, this);

        setVisibleArea(visibleAreaPosition, visibleBlocks);

        return view;
	}


    /**
     * If the snake is at {@link #scrollDistance} from the edge and we haven't
     * reached the level's
     * bounds, it scrolls the bitmap.
     */
    @Override
    public void updateVisibleArea() {
        if (currentRelativePosition().x < scrollDistance
                && visibleAreaPosition.x > 0) {
            scroll(Direction.LEFT);
        } else if (currentRelativePosition().x > visibleBlocks.x
                - scrollDistance
                && visibleAreaPosition.x + visibleBlocks.x < getMaps()[0]
                .numBlocksX()) {
            scroll(Direction.RIGHT);
        } else if (currentRelativePosition().y < scrollDistance
                && visibleAreaPosition.y > 0) {
            scroll(Direction.UP);
        } else if (currentRelativePosition().y > visibleBlocks.y
                - scrollDistance
                && visibleAreaPosition.y + visibleBlocks.y < getMaps()[0]
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
        int originPixelsX = origin.x * getMaps()[0].getWidth()
                / getMaps()[0].numBlocksX();
        int originPixelsY = origin.y * getMaps()[0].getHeight()
                / getMaps()[0].numBlocksY();
        int numPixelsX = numBlocks.x * getMaps()[0].getWidth()
                / getMaps()[0].numBlocksX();
        int numPixelsY = numBlocks.y * getMaps()[0].getHeight()
                / getMaps()[0].numBlocksY();

        // Then we make the visible area bitmaps
        for(int i=0; i<3; i++) {
            visibleBitmaps[i] = Bitmap.createBitmap(
                    getMaps()[i].getScaledBitmap(), originPixelsX,
                    originPixelsY, numPixelsX, numPixelsY);
        }

        // We update the position and blocks fields
        visibleAreaPosition = new Point(origin);
        visibleBlocks = new Point(numBlocks);
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




    /**
     * The snake's position relative to the visible bitmap
     *
     * @return position
     */
    private Point currentRelativePosition() {
        return new Point(currentPosition().x - visibleAreaPosition.x,
                currentPosition().y - visibleAreaPosition.y);
    }


    @Override
    public int visibleBlocksX() {
        return visibleBlocks.x;
    }

    @Override
    public int visibleBlocksY() {
        return visibleBlocks.y;
    }
}
