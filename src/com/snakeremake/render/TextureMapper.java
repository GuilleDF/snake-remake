package com.snakeremake.render;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.android2d.R;
import com.example.android2d.R.drawable;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.core.snake.SnakeBlock;

/**
 * Assigns a {@link Bitmap} to a color (specified in {@link TextureMap}) <br>
 * <br>
 * To add a new texture, add the appropriate field, initialize it in
 * {@link #loadTextures()} and assign it to a color in
 * {@link #mapBlock(int, int, ScaledBitmap, ScaledBitmap) mapBlock()}
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
	}

	/**
	 * Maps a whole {@link ScaledBitmap}
	 * 
	 * @param bitmap The {@link ScaledBitmap} to map
	 */
	public void mapBitmap(ScaledBitmap bitmap) {
		for (int i = 0; i < bitmap.numBlocksX(); i++) {
			for (int j = 0; j < bitmap.numBlocksY(); j++) {
				mapBlock(i, j, bitmap);
			}
		}
	}

	/**
	 * Maps a <b>snake</b> onto a <b>bitmap</b>
	 * 
	 * @param snake
	 * @param bitmap
	 */
	public void mapSnake(Snake snake, ScaledBitmap bitmap) {
		for (SnakeBlock block : snake.getBlocks()) {

			// First we make sure the floor is mapped properly
			mapBlock(block.getCurrentPosition().x,
					block.getCurrentPosition().y, bitmap);

			// Then we map the snake
			mapBlock(block.getCurrentPosition().x,
					block.getCurrentPosition().y, snake.getScaledBitmap(), bitmap);

			// We also make sure to remap the floor after the snake has passed it
			if (block.isTail() && block.getLastPosition() != null) {
				mapBlock(block.getLastPosition().x, block.getLastPosition().y,
						bitmap);
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
	 *            The source {@link ScaledBitmap}
	 * @param target
	 *            The target {@link ScaledBitmap}
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
	}

	/**
	 * Same as {@link #mapBlock(int, int, ScaledBitmap, ScaledBitmap)
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