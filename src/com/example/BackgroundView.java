package com.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.android2d.R;

public class BackgroundView extends View {

	private Bitmap background;
	private Point screenSize;
	private ScaledBitmap resizedBg;

	private Snake snake;

	private GestureDetector gestureDetector;

	private TextureMapper mapper;

	public BackgroundView(Context context) {
		super(context);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inScaled = false;

		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_2020, options);
		new Paint();

		screenSize = ExtraTools.getScreenSize(context);

		resizedBg = new ScaledBitmap(background);
		resizedBg.scaleByWidth(screenSize.x);

		snake = new Snake(5, 15);
		snake.addBlock();
		snake.setDirection(Direction.UP);
		snake.move();
		snake.move();
		snake.addBlock();
		snake.move();
		snake.addBlock();
		snake.move();
		snake.move();
		snake.addBlock();
		snake.move();
		snake.addBlock();
		snake.move();
		snake.addBlock();
		snake.move();

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
			if(snake.hasEatenFruit()){
				Point pos = ExtraTools.placeRandomFruit(resizedBg);
				mapper.mapBlock(pos.x, pos.y, resizedBg);
			}
			snake.calculateColors();
			snake.draw(resizedBg);
			mapper.mapSnake(snake, resizedBg);
			resizedBg.draw(canvas, 0,
					(screenSize.y - resizedBg.getHeight()) / 2);
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
