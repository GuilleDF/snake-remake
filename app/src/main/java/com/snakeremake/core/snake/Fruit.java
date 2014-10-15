package com.snakeremake.core.snake;

import android.graphics.Point;

import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;

public class Fruit {
	private Point position;
	private int color;
	
	public Fruit(Point pos){
		position = new Point(pos);
		color = TextureMap.FRUIT;
	}
	
	public Fruit(int x, int y){
		position = new Point(x,y);
		color = TextureMap.FRUIT;
	}
	
	public int getColor(){
		return color;
	}
	
	public void setColor(int color){
		this.color = color;
	}

	public Point getPosition() {
		return position;
	}

	public void place(ScaledBitmap bitmap){
		bitmap.drawToOriginal(position.x, position.y, color);
	}
}
