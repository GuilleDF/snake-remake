package com.example.android2d;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

public class GestureProcessor implements OnGestureListener {
	private Snake snake;
	private StaticLevelView view;

	public GestureProcessor(Snake snake, StaticLevelView view) {
		this.snake = snake;
		this.view = view;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (e.getRawX() < 500 && e.getRawY() < 500)
			view.onPauseButtonPressed();
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
		if (view.isPaused())
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

		return false;
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
