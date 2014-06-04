package com.example;

import java.util.Random;

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

	public static Point placeRandomFruit(ScaledBitmap bitmap){
		int x,y;
		do{
		Random rand = new Random();
		x = rand.nextInt(bitmap.numBlocksX());
		y = rand.nextInt(bitmap.numBlocksY());
		}while(bitmap.getBlock(x, y) != TextureMap.FLOOR);
		
		Fruit fruit = new Fruit(x,y);
		fruit.place(bitmap);
		
		return new Point(x,y);
		
	}
}