package com.snakeremake.utils;

import java.util.Random;

import com.snakeremake.core.snake.Fruit;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;
import com.snakeremake.views.BaseLevelView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ExtraTools {
	private static Display display;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static Point getScreenSize(Context context) {
		Point size = new Point();
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		if (android.os.Build.VERSION.SDK_INT >= 13)
			display.getSize(size);
		else {
			size.x = display.getWidth();
			size.y = display.getHeight();
		}

		return size;

	}

	public static Point placeRandomFruit(BaseLevelView view){
        ScaledBitmap levelMap = view.levelScaledBitmap;
        ScaledBitmap fruitMap = view.fruitScaledBitmap;
        ScaledBitmap snakeMap = view.snake.getScaledBitmap();
		int x,y;
		do{
		Random rand = new Random();
		x = rand.nextInt(fruitMap.numBlocksX());
		y = rand.nextInt(fruitMap.numBlocksY());
		}while(fruitMap.getBlock(x, y) != TextureMap.FLOOR   ||
                snakeMap.getBlock(x, y) != TextureMap.FLOOR  ||
                levelMap.getBlock(x, y) != TextureMap.FLOOR
                );
		
		Fruit fruit = new Fruit(x,y);
		fruit.place(fruitMap);
		
		return new Point(x,y);
		
	}
}