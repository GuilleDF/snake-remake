package com.example.android2d;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
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
	private int levelResourceId;
	private Point snakePosition;
	private int numberOfBlocks;
	private boolean spawnFruits;

	private Bitmap pause;
	private Paint paint;
	private boolean paused;

	private int orientation;
	
	private int score;

	public BackgroundView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId) {
		super(context);
		this.levelResourceId = levelResourceId;
		this.snakePosition = snakePosition;
		this.numberOfBlocks = numberOfBlocks;
		this.spawnFruits = spawnFruits;
		paused = false;
		score = 0;
		onCreate();
	}

	private void onCreate() {
		loadMap();
		generateSnake();

		gestureDetector = new GestureDetector(getContext(),
				new GestureProcessor(snake, this));

		if (spawnFruits) {
			ExtraTools.placeRandomFruit(resizedBg);
			ExtraTools.placeRandomFruit(resizedBg);
		}

		paint = new Paint();
		pause = BitmapFactory.decodeResource(getResources(), R.drawable.pause);

		mapTextures();
	}

	private void configureScreen() {
		screenSize = ExtraTools.getScreenSize(getContext());
		orientation = getResources().getConfiguration().orientation;
	}

	private void loadMap() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inScaled = false;
		background = BitmapFactory.decodeResource(getResources(),
				levelResourceId, options);
		resizedBg = new ScaledBitmap(background);
		scaleMap();
	}

	private void scaleMap() {
		configureScreen();
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			resizedBg.scaleByWidth(screenSize.x);
		} else {
			resizedBg.scaleByHeight(screenSize.y);
		}
	}

	private void generateSnake() {
		snake = new Snake(snakePosition.x, snakePosition.y);
		snake.setDirection(Direction.UP);
		for (int i = 0; i < numberOfBlocks; i++) {
			snake.addBlock();
		}
	}

	private void mapTextures() {
		mapper = new TextureMapper(getResources());
		mapper.loadTextures();
		mapper.mapBitmap(resizedBg);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(pause, 0, 0, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText("Score: " + score, screenSize.x-10, 50, paint);
		if (orientation != getResources().getConfiguration().orientation) {
			scaleMap();
			mapTextures();
			drawMap(canvas);
		} else {
			playGame(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return gestureDetector.onTouchEvent(e);
	}

	public void onLose() {
		BaseLevelActivity host = (BaseLevelActivity) getContext();
		host.onGameOver(score);
	}

	public void onPauseButtonPressed() {
		paused = !paused;
		if (!paused)
			postInvalidateDelayed(200);

	}

	public boolean isPaused() {
		return paused;
	}

	private void playGame(Canvas canvas) {
		snake.moveOnBitmap(resizedBg);
		if (snake.hasDied())
			onLose();
		else {
			if (spawnFruits && snake.hasEatenFruit()) {
				score++;
				Point pos = ExtraTools.placeRandomFruit(resizedBg);
				mapper.mapBlock(pos.x, pos.y, resizedBg);
			}
			snake.draw(resizedBg);
			mapper.mapSnake(snake, resizedBg);
			drawMap(canvas);
		}
		if (!paused)
			postInvalidateDelayed(200);
	}

	private void drawMap(Canvas canvas) {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			resizedBg.draw(canvas, 0,
					(screenSize.y - resizedBg.getHeight()) / 2);
		} else {
			resizedBg
					.draw(canvas, (screenSize.x - resizedBg.getWidth()) / 2, 0);

		}
	}

}
