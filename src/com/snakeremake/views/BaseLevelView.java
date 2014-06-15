package com.snakeremake.views;

import com.example.android2d.R;
import com.example.android2d.R.drawable;
import com.example.android2d.R.styleable;
import com.snakeremake.activity.BaseLevelActivity;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMapper;
import com.snakeremake.utils.Direction;
import com.snakeremake.utils.ExtraTools;
import com.snakeremake.utils.GestureProcessor;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
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

public abstract class BaseLevelView extends View {

	protected ScaledBitmap levelScaledBitmap;

	//---------------------------------------//
	
	private Point screenSize;
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
	private final long updateInterval = 200;

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
			ExtraTools.placeRandomFruit(levelScaledBitmap);
			ExtraTools.placeRandomFruit(levelScaledBitmap);
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
		Bitmap background = BitmapFactory.decodeResource(getResources(),
				levelResourceId, options);
		levelScaledBitmap = new ScaledBitmap(background);

		// Maybe not the best place for this line...
		snake = new Snake(snakePosition.x, snakePosition.y);

		snake.setScaledBitmap(new ScaledBitmap(
				Bitmap.createBitmap(background.getWidth(),
						background.getHeight(), Config.ARGB_8888)));

		scaleMap();
	}

	protected void scaleMap() {
		configureScreen();
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			int desiredWidth = screenSize.x * levelScaledBitmap.numBlocksX()
					/ visibleBlocksX();
			levelScaledBitmap.scaleByWidth(desiredWidth);
			snake.getScaledBitmap().scaleByWidth(desiredWidth);
		} else {
			int desiredHeight = screenSize.y * levelScaledBitmap.numBlocksY()
					/ visibleBlocksY();
			levelScaledBitmap.scaleByHeight(desiredHeight);
			snake.getScaledBitmap().scaleByHeight(desiredHeight);
		}
	}

	protected abstract int visibleBlocksY();

	protected abstract int visibleBlocksX();

	private void generateSnake() {
		snake.setDirection(Direction.UP);
		for (int i = 0; i < numberOfBlocks; i++) {
			snake.addBlock();
		}
	}

	private void mapTextures() {
		mapper = new TextureMapper(getResources());
		mapper.loadTextures();
		mapper.mapBitmap(levelScaledBitmap);
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
			postInvalidateDelayed(updateInterval );

	}

	public boolean isPaused() {
		return paused;
	}

	private void playGame(Canvas canvas) {
		snake.moveOnBitmap(levelScaledBitmap);
		snakePosition = snake.getPosition();
		if (snake.hasDied())
			onLose();
		else {
			if (spawnFruits && snake.hasEatenFruit()) {
				score++;

				// To 'eat' the fruit, we map where the fruit was to white
				levelScaledBitmap.drawToOriginal(snakePosition.x,
						snakePosition.y, Color.WHITE);

				Point pos = ExtraTools.placeRandomFruit(levelScaledBitmap);
				mapper.mapBlock(pos.x, pos.y, levelScaledBitmap);
			}
			snake.draw();
			mapper.mapSnake(snake, levelScaledBitmap);
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
			canvas.drawBitmap(bitmapToDraw,
					(screenSize.x - bitmapToDraw.getWidth()) / 2, 0, paint);

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
