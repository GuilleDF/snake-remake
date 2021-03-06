package com.snakeremake.utils;

import com.snakeremake.core.snake.Snake;
import com.snakeremake.main.Level;
import com.snakeremake.views.LevelView;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class GestureProcessor implements OnGestureListener {
	private Snake snake;
	private Level level;

	public GestureProcessor( Level level) {
		this.snake = level.getSnake();
		this.level = level;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (e.getRawX() < 500 && e.getRawY() < 500) //This causes bug #3 (it activates on the top left 500x500px, surely there is another way)
			level.onPauseButtonPressed();
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

        float threshold = 50;

        if(Math.abs(distanceX) < threshold && Math.abs(distanceY) < threshold)
            return true;

		if (level.isPaused())
			return true;

		Direction direction = snake.getDirection();
		int currentAxis = -1; // -1 = no axis, 0 = x axis, 1 = y axis
		if (direction.equals(Direction.RIGHT)
				|| direction.equals(Direction.LEFT))
			currentAxis = 0;
		else
			currentAxis = 1;

		int futureAxis = -1; // -1 = no axis, 0 = x axis, 1 = y axis

		if (Math.abs(distanceY) > Math.abs(distanceX)) {
			// Y axis
			futureAxis = 1;
		} else {
			// X axis
			futureAxis = 0;
		}

		if (futureAxis == 0 && currentAxis == 1) {
			if (distanceX < 0) {
				snake.setDirection(Direction.RIGHT);
			} else {
				snake.setDirection(Direction.LEFT);
			}
		} else if (futureAxis == 1 && currentAxis == 0) {
			if (distanceY < 0) {
				snake.setDirection(Direction.DOWN);
			} else {
				snake.setDirection(Direction.UP);
			}
		}

		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

}
