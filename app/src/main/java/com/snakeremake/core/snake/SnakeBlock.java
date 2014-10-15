package com.snakeremake.core.snake;

import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;
import com.snakeremake.utils.Direction;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class SnakeBlock {

	/** Last position the block was at */
	private Point lastPosition;
	/** current position of the block */
	private Point currentPosition;
	private Paint paint;
	private boolean isTail;
	private boolean isHead;
	private int color = Color.GREEN;
	
	public SnakeBlock(int x, int y){
		currentPosition = new Point(x, y);
		paint = new Paint();
		paint.setColor(color);
	}
	
	public SnakeBlock(Point pos){
		currentPosition = new Point(pos);
		paint = new Paint();
		paint.setColor(color);
	}
	
	public void setTail(boolean value){
		isTail = value;
	}
	
	public void setHead(boolean value){
		isHead = value;
	}
	
	public boolean isHead(){
		return isHead;
	}
	
	public boolean isTail(){
		return isTail;
	}
	
	public void move(Direction direction){
		lastPosition = new Point(currentPosition);
		
		switch(direction){
		case LEFT:
			currentPosition.x--;
			break;
		case UP:
			currentPosition.y--;
			break;
		case RIGHT:
			currentPosition.x++;
			break;
		case DOWN:
			currentPosition.y++;
		}
	}
	
	public void move(int x, int y){
		lastPosition = new Point(currentPosition);
		currentPosition = new Point(x, y);
	}
	
	public void draw(ScaledBitmap bitmap){
		if(isTail && lastPosition != null){
			bitmap.drawToOriginal(lastPosition.x, lastPosition.y, TextureMap.FLOOR);

		}
		bitmap.drawToOriginal(currentPosition.x, currentPosition.y, color);
	}

	public Point getCurrentPosition() {
		return currentPosition;
	}

	public Point getLastPosition() {
		return lastPosition;
	}
	
	public void setColor(int color){
		this.color = color;
	}
}
