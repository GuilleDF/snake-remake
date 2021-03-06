package com.snakeremake.core.snake;

import android.graphics.Color;
import android.graphics.Point;

import com.snakeremake.main.Level;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;
import com.snakeremake.utils.Direction;
import com.snakeremake.views.LevelView;

import java.util.ArrayList;
import java.util.List;

public class Snake {
	private List<SnakeBlock> blocks;
	private Direction direction;
	private Point position;
	/** Whether the snake has moved since the last change in direction */
	private boolean hasMoved;

	private boolean hasDied;
	private boolean hasEatenFruit;
	/**
	 * The scaled bitmap the snake will be drawn to. Set it with
	 * {@link #setScaledBitmap(ScaledBitmap)}
	 */
	private ScaledBitmap scaledBitmap;

	public Snake(int x, int y) {
		blocks = new ArrayList<SnakeBlock>();
		position = new Point(x, y);
		hasMoved = true; // This is so we can call setDirection() for the first
							// time
		hasDied = false;
		hasEatenFruit = false;
		addBlock(); // So the snake has at least 1 block
	}

	public Direction getDirection() {
		return direction;
	}

	/**
	 * Adds a block to the snake after the current 'tail', becoming the 'tail' <br>
	 * <br>
	 * It moves the snake before adding the block
	 */
	public void addBlock() {
		move();
		boolean isHead = false;
		Point pos;
		if (blocks.isEmpty()) {
			pos = new Point(position);
			isHead = true;
		} else {
			pos = new Point(blocks.get(blocks.size() - 1).getLastPosition());
			blocks.get(blocks.size() - 1).setTail(false);

		}
		SnakeBlock block = new SnakeBlock(pos.x, pos.y);
		block.setHead(isHead);
		block.setTail(true);
		blocks.add(block);
	}

	/**
	 * Moves the snake in the current direction.<br>
	 * Sets {@link #hasMoved} to true
	 */
	private void move() {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isHead()) {
				blocks.get(i).move(direction);
				position = blocks.get(i).getCurrentPosition();
			} else {
				Point newPos = blocks.get(i - 1).getLastPosition();
				blocks.get(i).move(newPos.x, newPos.y);
			}
		}
		hasMoved = true;
		hasEatenFruit = false;
	}

	/**
	 * Changes the current direction and sets {@link #hasMoved} to false.
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		if (hasMoved)
			this.direction = direction;
		hasMoved = false;
	}

	/**
	 * Draws the snake onto the bitmap (the colors, not the textures)
	 */
	public void draw() {
		for (int i = 0; i < blocks.size(); i++) {
			calculateColor(i);
			blocks.get(i).draw(scaledBitmap);
		}
	}

	public List<SnakeBlock> getBlocks() {
		return blocks;
	}

	/**
	 * Sets each {@link SnakeBlock} to the appropriate color, depending on each
	 * block's type and position
	 */
	private void calculateColor(int blockIndex) {
		if (blocks.get(blockIndex).isHead()) {
			switch (direction) {
			case UP:
				blocks.get(blockIndex).setColor(TextureMap.SNAKE_HEAD_UP);
				break;
			case RIGHT:
				blocks.get(blockIndex).setColor(TextureMap.SNAKE_HEAD_RIGHT);
				break;
			case DOWN:
				blocks.get(blockIndex).setColor(TextureMap.SNAKE_HEAD_DOWN);
				break;
			case LEFT:
				blocks.get(blockIndex).setColor(TextureMap.SNAKE_HEAD_LEFT);
				break;
			}
		} else {
			if (blocks.get(blockIndex).getCurrentPosition().x == blocks.get(
					blockIndex - 1).getCurrentPosition().x) {
				if (blocks.get(blockIndex).isTail()) {
					if (blocks.get(blockIndex).getCurrentPosition().y < blocks
							.get(blockIndex - 1).getCurrentPosition().y) {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_TAIL_DOWN);
					} else {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_TAIL_UP);
					}
				} else {
					if (blocks.get(blockIndex + 1).getCurrentPosition().x == blocks
							.get(blockIndex).getCurrentPosition().x) {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_BODY_VERTICAL);
					} else if (blocks.get(blockIndex + 1).getCurrentPosition().x < blocks
							.get(blockIndex).getCurrentPosition().x) {
						if (blocks.get(blockIndex).getCurrentPosition().y < blocks
								.get(blockIndex - 1).getCurrentPosition().y) {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_DL);
						} else {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_UL);
						}

					} else {
						if (blocks.get(blockIndex).getCurrentPosition().y < blocks
								.get(blockIndex - 1).getCurrentPosition().y) {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_DR);
						} else {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_UR);
						}

					}
				}
			} else {
				if (blocks.get(blockIndex).isTail()) {
					if (blocks.get(blockIndex).getCurrentPosition().x < blocks
							.get(blockIndex - 1).getCurrentPosition().x) {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_TAIL_RIGHT);
					} else {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_TAIL_LEFT);
					}
				} else {
					if (blocks.get(blockIndex + 1).getCurrentPosition().y == blocks
							.get(blockIndex).getCurrentPosition().y) {
						blocks.get(blockIndex).setColor(
								TextureMap.SNAKE_BODY_HORIZONTAL);
					} else if (blocks.get(blockIndex + 1).getCurrentPosition().y < blocks
							.get(blockIndex).getCurrentPosition().y) {
						if (blocks.get(blockIndex).getCurrentPosition().x < blocks
								.get(blockIndex - 1).getCurrentPosition().x) {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_UR);
						} else {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_UL);
						}

					} else {
						if (blocks.get(blockIndex).getCurrentPosition().x < blocks
								.get(blockIndex - 1).getCurrentPosition().x) {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_DR);
						} else {
							blocks.get(blockIndex).setColor(
									TextureMap.SNAKE_BEND_DL);
						}

					}
				}
			}
		}
	}

	/**
	 * Does the same as {@link #move()}, but checks for collisions
	 * 
	 * @param level
     *             The Level to use for collision checking
	 */
	public void moveOnLevel(Level level) {
        ScaledBitmap levelMap = level.getMaps()[0];
        ScaledBitmap fruitMap = level.getMaps()[1];
		SnakeBlock inFront = new SnakeBlock(position.x, position.y);
		inFront.move(direction);
		Point pos = inFront.getCurrentPosition();
		if (levelMap.getBlock(pos.x, pos.y) != TextureMap.FLOOR)
            onCrash(levelMap.getBlock(pos.x, pos.y));
		else if(getScaledBitmap().getBlock(pos.x, pos.y) != TextureMap.TRANSPARENT)
            onCrash(getScaledBitmap().getBlock(pos.x, pos.y));
        else if(fruitMap.getBlock(pos.x, pos.y) != TextureMap.TRANSPARENT) {
            onCrash(fruitMap.getBlock(pos.x, pos.y));
		} else {
            move();
		}
	}

	/**
	 * Called when the snake tries to walk on a block other than
	 * {@link TextureMap#FLOOR FLOOR}
	 * 
	 * @param block
	 *            The block color it crashed into
	 */
	private void onCrash(int block) {
		if (block == Color.BLACK) { // WALL (for now)
			hasDied = true;
		} else if (Color.green(block) == 255) { // SNAKE
			hasDied = true;
		} else if (Color.blue(block) == 255) { // FRUIT (for now)
            addBlock();
			hasEatenFruit = true;
		}
	}

	public boolean hasDied() {
		return hasDied;
	}

	public boolean hasEatenFruit() {
		return hasEatenFruit;
	}

	public Point getPosition() {
		return position;
	}

	public ScaledBitmap getScaledBitmap() {
		return scaledBitmap;
	}

	public void setScaledBitmap(ScaledBitmap sb) {
		sb.getOriginalBitmap().eraseColor(TextureMap.TRANSPARENT);
		scaledBitmap = sb;
	}
}
