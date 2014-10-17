package com.snakeremake.render;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.snakeremake.R;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.core.snake.SnakeBlock;
import com.snakeremake.utils.ExtraTools;

/**
 * Assigns a {@link android.graphics.Bitmap} to a color (specified in {@link com.snakeremake.render.TextureMap}) <br>
 * <br>
 * To add a new texture, add the appropriate field, initialize it in
 * {@link #loadTextures()} and assign it to a color in
 * {@link #mapBlock(int, int, com.snakeremake.render.ScaledBitmap, com.snakeremake.render.ScaledBitmap) mapBlock()}
 * 
 * @author GuilleDF
 * 
 */
public class TextureMapper {
	private Resources resources;

	private Bitmap floor;
	private Bitmap wall;
	private Bitmap fruit;

	private Bitmap snakeHeadUp;
	private Bitmap snakeHeadRight;
	private Bitmap snakeHeadDown;
	private Bitmap snakeHeadLeft;

	private Bitmap snakeTailUp;
	private Bitmap snakeTailRight;
	private Bitmap snakeTailDown;
	private Bitmap snakeTailLeft;

	private Bitmap snakeBodyVertical;
	private Bitmap snakeBodyHorizontal;

	private Bitmap snakeBendDR;
	private Bitmap snakeBendDL;
	private Bitmap snakeBendUR;
	private Bitmap snakeBendUL;

    private Bitmap transparent;

	public TextureMapper(Resources res) {
		resources = res;
	}

	public void loadTextures() {
		floor = BitmapFactory.decodeResource(resources,
				R.drawable.floor_texture);
		wall = BitmapFactory.decodeResource(resources, R.drawable.wall_texture);
		fruit = BitmapFactory.decodeResource(resources, R.drawable.fruit);

		snakeHeadUp = BitmapFactory.decodeResource(resources,
				R.drawable.snake_head_up);
		snakeHeadRight = BitmapFactory.decodeResource(resources,
				R.drawable.snake_head_right);
		snakeHeadDown = BitmapFactory.decodeResource(resources,
				R.drawable.snake_head_down);
		snakeHeadLeft = BitmapFactory.decodeResource(resources,
				R.drawable.snake_head_left);

		snakeTailUp = BitmapFactory.decodeResource(resources,
				R.drawable.snake_tail_up);
		snakeTailRight = BitmapFactory.decodeResource(resources,
				R.drawable.snake_tail_right);
		snakeTailDown = BitmapFactory.decodeResource(resources,
				R.drawable.snake_tail_down);
		snakeTailLeft = BitmapFactory.decodeResource(resources,
				R.drawable.snake_tail_left);

		snakeBodyVertical = BitmapFactory.decodeResource(resources,
				R.drawable.snake_body_vertical);
		snakeBodyHorizontal = BitmapFactory.decodeResource(resources,
				R.drawable.snake_body_horizontal);

		snakeBendDR = BitmapFactory.decodeResource(resources,
				R.drawable.snake_bend_dr);
		snakeBendDL = BitmapFactory.decodeResource(resources,
				R.drawable.snake_bend_dl);
		snakeBendUR = BitmapFactory.decodeResource(resources,
				R.drawable.snake_bend_ur);
		snakeBendUL = BitmapFactory.decodeResource(resources,
				R.drawable.snake_bend_ul);

        transparent = Bitmap.createBitmap(20,20, Bitmap.Config.ARGB_8888);
        transparent.setHasAlpha(true);
        transparent.eraseColor(Color.TRANSPARENT);
	}

	/**
	 * Maps a whole {@link com.snakeremake.render.ScaledBitmap}
	 * 
	 * @param bitmap The {@link com.snakeremake.render.ScaledBitmap} to map
	 */
	public void mapBitmap(ScaledBitmap bitmap) {
		for (int i = 0; i < bitmap.numBlocksX(); i++) {
			for (int j = 0; j < bitmap.numBlocksY(); j++) {
				mapBlock(i, j, bitmap);
			}
		}
	}

	/**
	 * Maps a <b>snake</b> onto its bitmap
	 * 
	 * @param snake
	 *
	 */
	public void mapSnake(Snake snake) {
		for (SnakeBlock block : snake.getBlocks()) {

			mapBlock(block.getCurrentPosition().x,
					block.getCurrentPosition().y, snake.getScaledBitmap());

			// We make sure to remap after the snake if it has moved
			if (block.isTail() && block.getLastPosition() != null) {
				mapBlock(block.getLastPosition().x, block.getLastPosition().y,
                        snake.getScaledBitmap());
			}
		}
	}

	/**
	 * Maps a block from <b>source</b> onto <b>target</b>
	 * 
	 * @param x
	 *            The block's x coordinate
	 * @param y
	 *            The block's y coordinate
	 * @param source
	 *            The source {@link com.snakeremake.render.ScaledBitmap}
	 * @param target
	 *            The target {@link com.snakeremake.render.ScaledBitmap}
	 */
	public void mapBlock(int x, int y, ScaledBitmap source, ScaledBitmap target) {
		int color = source.getBlock(x, y);
		if (color == TextureMap.FLOOR)
			target.drawBlock(x, y, floor);
		else if (color == TextureMap.SNAKE_BEND_DL)
			target.drawBlock(x, y, snakeBendDL);
		else if (color == TextureMap.SNAKE_BEND_DR)
			target.drawBlock(x, y, snakeBendDR);
		else if (color == TextureMap.SNAKE_BEND_UL)
			target.drawBlock(x, y, snakeBendUL);
		else if (color == TextureMap.SNAKE_BEND_UR)
			target.drawBlock(x, y, snakeBendUR);
		else if (color == TextureMap.SNAKE_BODY_HORIZONTAL)
			target.drawBlock(x, y, snakeBodyHorizontal);
		else if (color == TextureMap.SNAKE_BODY_VERTICAL)
			target.drawBlock(x, y, snakeBodyVertical);
		else if (color == TextureMap.SNAKE_HEAD_DOWN)
			target.drawBlock(x, y, snakeHeadDown);
		else if (color == TextureMap.SNAKE_HEAD_LEFT)
			target.drawBlock(x, y, snakeHeadLeft);
		else if (color == TextureMap.SNAKE_HEAD_RIGHT)
			target.drawBlock(x, y, snakeHeadRight);
		else if (color == TextureMap.SNAKE_HEAD_UP)
			target.drawBlock(x, y, snakeHeadUp);
		else if (color == TextureMap.SNAKE_TAIL_DOWN)
			target.drawBlock(x, y, snakeTailDown);
		else if (color == TextureMap.SNAKE_TAIL_LEFT)
			target.drawBlock(x, y, snakeTailLeft);
		else if (color == TextureMap.SNAKE_TAIL_RIGHT)
			target.drawBlock(x, y, snakeTailRight);
		else if (color == TextureMap.SNAKE_TAIL_UP)
			target.drawBlock(x, y, snakeTailUp);
		else if (color == TextureMap.WALL)
			target.drawBlock(x, y, wall);
		else if (color == TextureMap.FRUIT)
			target.drawBlock(x, y, fruit);
        else if (color == TextureMap.TRANSPARENT)
            target.drawBlock(x, y, transparent);
	}

	/**
	 * Same as {@link #mapBlock(int, int, com.snakeremake.render.ScaledBitmap, com.snakeremake.render.ScaledBitmap)
	 * mapBlock(x, y, bitmap, bitmap)}
	 * 
	 * @param x
	 * @param y
	 * @param bitmap
	 */
	public void mapBlock(int x, int y, ScaledBitmap bitmap) {
		mapBlock(x, y, bitmap, bitmap);
	}
}
