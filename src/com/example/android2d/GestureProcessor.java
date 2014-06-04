package com.example.android2d;

import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

public class GestureProcessor implements OnGestureListener {
	private Snake snake;
	private BackgroundView view;

	public GestureProcessor(Snake snake, BackgroundView view) {
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
		if(view.isPaused())
			return true;
		
		int axis = -1; // -1 = no axis, 0 = x axis, 1 = y axis

		if (Math.abs(distanceY) > Math.abs(distanceX)) {
			// Y axis
			axis = 1;
		} else {
			// X axis
			axis = 0;
		}

		if (axis == 0) {
			if (distanceX < 0) {
				if (!snake.getDirection().equals(Direction.LEFT))
					snake.setDirection(Direction.RIGHT);
			} else {
				if (!snake.getDirection().equals(Direction.RIGHT))
					snake.setDirection(Direction.LEFT);
			}
		} else if (axis == 1) {
			if (distanceY < 0) {
				if (!snake.getDirection().equals(Direction.UP))
					snake.setDirection(Direction.DOWN);
			} else {
				if (!snake.getDirection().equals(Direction.DOWN))
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
