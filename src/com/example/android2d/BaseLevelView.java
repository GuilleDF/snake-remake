package com.example.android2d;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseLevelView extends View{

	private Bitmap background;
	private Point screenSize;
	protected ScaledBitmap resizedBg;

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

	// XML parsing isn't working
	public BaseLevelView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.StaticLevelView, 0, 0);

		snakePosition = new Point(a.getInteger(
				R.styleable.StaticLevelView_snakePositionX, 0), a.getInteger(
				R.styleable.StaticLevelView_snakePositionY, 0));

		numberOfBlocks = a.getInteger(
				R.styleable.StaticLevelView_numberOfBlocks, 0);

		spawnFruits = a.getBoolean(R.styleable.StaticLevelView_spawnFruits,
				false);

		levelResourceId = a.getResourceId(
				R.styleable.StaticLevelView_levelImage, 0);

		screenSize = new Point(a.getLayoutDimension(
				android.R.attr.layout_width, 0), a.getLayoutDimension(
				android.R.attr.layout_height, 0));

		a.recycle();

		onCreate();
	}

	public BaseLevelView(Context context, Point snakePosition,
			int numberOfBlocks, boolean spawnFruits, int levelResourceId) {
		super(context);
		this.levelResourceId = levelResourceId;
		this.snakePosition = snakePosition;
		this.numberOfBlocks = numberOfBlocks;
		this.spawnFruits = spawnFruits;

		// call onCreate(); when extending this class
	}

	protected void onCreate() {
		paused = false;
		score = 0;
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

	protected void configureScreen() {
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

	protected void scaleMap(){
		configureScreen();
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			resizedBg.scaleByWidth(screenSize.x*resizedBg.numBlocksX()/visibleBlocksX());
		} else {
			resizedBg.scaleByHeight(screenSize.y*resizedBg.numBlocksY()/visibleBlocksY());
		}
	}

	protected abstract int visibleBlocksY();

	protected abstract int visibleBlocksX();

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
		canvas.drawText("Score: " + score, screenSize.x - 10, 50, paint);
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
		snakePosition = snake.getPosition();
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

	protected abstract void updateVisibleArea();

	private void drawMap(Canvas canvas) {
		updateVisibleArea();
		Bitmap bitmapToDraw = visibleBitmap();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			canvas.drawBitmap(bitmapToDraw, 0,
					(screenSize.y - bitmapToDraw.getHeight()) / 2, paint);
		} else {
			canvas
					.drawBitmap(bitmapToDraw, (screenSize.x - bitmapToDraw.getWidth()) / 2, 0, paint);

		}
	}

	protected abstract Bitmap visibleBitmap();

	public Bitmap currentScreen() {
		setDrawingCacheEnabled(true);
		return getDrawingCache();
	}
	
	protected Point currentPosition() {
		return snakePosition;
	}
	
}
