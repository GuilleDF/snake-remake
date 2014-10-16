package com.snakeremake.render;

import android.graphics.Color;
/**
 * Gives names to the colors that will represent each texture
 * @author GuilleDF
 *
 */
public class TextureMap {
	public static final int FRUIT = Color.BLUE; // For now
	public static final int FLOOR = Color.WHITE; // For now
	public static final int WALL = Color.BLACK; // For now
	
	
	public static final int SNAKE_HEAD_UP = Color.argb(255, 0, 255, 0);
	public static final int SNAKE_HEAD_RIGHT = Color.argb(255, 0, 255, 50);
	public static final int SNAKE_HEAD_DOWN = Color.argb(255, 0, 255, 100);
	public static final int SNAKE_HEAD_LEFT = Color.argb(255, 0, 255, 150);
	
	public static final int SNAKE_BODY_VERTICAL = Color.argb(255, 50, 255, 0);
	public static final int SNAKE_BODY_HORIZONTAL = Color.argb(255, 50, 255, 100);
	
	public static final int SNAKE_TAIL_UP = Color.argb(255, 100, 255, 0);
	public static final int SNAKE_TAIL_RIGHT = Color.argb(255, 100, 255, 50);
	public static final int SNAKE_TAIL_DOWN = Color.argb(255, 100, 255, 100);
	public static final int SNAKE_TAIL_LEFT = Color.argb(255, 100, 255, 150);
	
	public static final int SNAKE_BEND_DR = Color.argb(255, 150, 255, 0);
	public static final int SNAKE_BEND_DL = Color.argb(255, 150, 255, 50);
	public static final int SNAKE_BEND_UR = Color.argb(255, 150, 255, 100);
	public static final int SNAKE_BEND_UL = Color.argb(255, 150, 255, 150);

    public static final int TRANSPARENT = Color.argb(255,1,1,1);

}
