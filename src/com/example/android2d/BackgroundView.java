package com.example.android2d;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class BackgroundView extends View {

	private Bitmap background;
	private Point screenSize;
	private ScaledBitmap resizedBg;

	private Snake snake;

	private GestureDetector gestureDetector;

	private TextureMapper mapper;

	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		onCreate();
	}

	public BackgroundView(Context context) {
		super(context);
		onCreate();
	}

	private void onCreate() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inScaled = false;

		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_2020, options);
		new Paint();

		screenSize = ExtraTools.getScreenSize(getContext());

		resizedBg = new ScaledBitmap(background);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			resizedBg.scaleByWidth(screenSize.x);
		} else {
			resizedBg.scaleByHeight(screenSize.y);
		}

		snake = new Snake(5, 15);
		snake.setDirection(Direction.UP);
		for (int i = 0; i < 5; i++) {
			snake.addBlock();
		}

		gestureDetector = new GestureDetector(getContext(),
				new GestureProcessor(snake));

		ExtraTools.placeRandomFruit(resizedBg);
		ExtraTools.placeRandomFruit(resizedBg);

		mapper = new TextureMapper(getResources());
		mapper.loadTextures();
		mapper.mapBitmap(resizedBg);

	}

	@Override
	public void onDraw(Canvas canvas) {
		snake.moveOnBitmap(resizedBg);
		if (snake.hasDied())
			onLose();
		else {
			if (snake.hasEatenFruit()) {
				Point pos = ExtraTools.placeRandomFruit(resizedBg);
				mapper.mapBlock(pos.x, pos.y, resizedBg);
			}
			snake.draw(resizedBg);
			mapper.mapSnake(snake, resizedBg);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				resizedBg.draw(canvas, 0,
						(screenSize.y - resizedBg.getHeight()) / 2);
			} else {
				resizedBg.draw(canvas,
						(screenSize.x - resizedBg.getWidth()) / 2, 0);
			}
			postInvalidateDelayed(200);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return gestureDetector.onTouchEvent(e);
	}

	public void onLose() {
		MainActivity host = (MainActivity) getContext();
		host.onGameOver();
	}

}
