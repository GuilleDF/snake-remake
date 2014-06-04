package com.example;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;


public class ScaledBitmap {
	private Bitmap originalBitmap;
	private Bitmap scaledBitmap;
	private int scale;
	private Canvas canvas;
	
	public ScaledBitmap(Bitmap bitmap){
		originalBitmap = bitmap.copy(Config.ARGB_8888, true);
		scale(1); //Default scale value
	}
	
	public void scale(int ratio){
		int newWidth = originalBitmap.getWidth()*ratio;
		int newHeight = originalBitmap.getHeight()*ratio;
		
		scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Config.ARGB_8888);
		canvas = new Canvas(scaledBitmap);
		
		Paint paint = new Paint();
		for(int i=0; i<originalBitmap.getWidth(); i++){
			for(int j=0; j<originalBitmap.getHeight(); j++){
				int color = originalBitmap.getPixel(i, j);
				
				paint.setColor(color);
				drawBlock(i, j, paint);
			}
		}
		
		scale = ratio;
	}
	
	public void scaleByWidth(int desiredWidth){
		scale = desiredWidth / originalBitmap.getWidth();
		scale(scale);
	}
	
	public void scaleByHeight(int desiredHeight){
		scale = desiredHeight / originalBitmap.getHeight();
		scale(scale);
	}
	/**
	 * Returns the color of a block, according to the original bitmap,
	 * which may have been modified by {@link #drawToOriginal(int, int, int) drawToOriginal()}
	 * @param x
	 * @param y
	 * @return color
	 */
	public int getBlock(int x, int y){
		return originalBitmap.getPixel(x, y);
	}
	
	public void drawBlock(int x, int y, Paint paint){
		canvas.drawRect(x*scale, y*scale, (x+1)*scale, (y+1)*scale, paint);
	}
	/**
	 * Draws a block with the given texture.
	 * 
	 * @param x
	 * @param y
	 * @param texture
	 */
	public void drawBlock(int x, int y, Bitmap texture){
		Paint paint = new Paint();
		paint.setDither(false);
		paint.setAntiAlias(false);
		
		Bitmap bitmap = Bitmap.createScaledBitmap(texture, scale, scale, true);
		canvas.drawBitmap(bitmap, x*scale, y*scale, paint);
	}
	
	public void draw(Canvas targetCanvas, float left, float top){
		Paint paint = new Paint();
		paint.setDither(false);
		paint.setAntiAlias(false);
		
		targetCanvas.drawBitmap(scaledBitmap, left, top, paint);
	}
	/**
	 * Actual width in pixels
	 * @return width
	 */
	public int getWidth(){
		return scaledBitmap.getWidth();
	}
	/**
	 * Actual height in pixels
	 * @return height
	 */
	public int getHeight(){
		return scaledBitmap.getHeight();
	}
	/**
	 * Number of blocks in X axis
	 * @return numBlocksX
	 */
	public int numBlocksX(){
		return originalBitmap.getWidth();
	}
	/**
	 * Number of blocks in Y axis
	 * @return numBlocksY
	 */
	public int numBlocksY(){
		return originalBitmap.getHeight();
	}
	/**
	 * Draws to the original bitmap, so that the color drawn can later be detected
	 * (with {@link #getBlock(int, int) getBlock(x,y)})
	 * @param x
	 * @param y
	 * @param color
	 */
	public void drawToOriginal(int x, int y, int color){
		originalBitmap.setPixel(x, y, color);
	}
	

}
