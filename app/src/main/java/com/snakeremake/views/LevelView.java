package com.snakeremake.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.snakeremake.R;
import com.snakeremake.activity.LevelActivity;
import com.snakeremake.core.snake.Snake;
import com.snakeremake.main.Game;
import com.snakeremake.main.Level;
import com.snakeremake.utils.ExtraTools;
import com.snakeremake.utils.GestureProcessor;

public class LevelView extends View {

	// ---------------------------------------//


	private GestureDetector gestureDetector;

	private boolean spawnFruits;

	private Bitmap pause;
	private Paint paint;

    public void setLevel(Level level) {
        this.level = level;
    }

    protected Level level;

    public LevelActivity getHostActivity() {
        return hostActivity;
    }

    private LevelActivity hostActivity;

	public LevelView(Context context, boolean spawnFruits, Level level) {
		super(context);
		this.spawnFruits = spawnFruits;
        this.level = level;

        hostActivity = (LevelActivity) context;

        onCreate();
	}



    /**
     * Draws every bitmap onto a canvas.<br>
     *     It calls {@link com.snakeremake.main.Level#updateVisibleArea()} first
     * @param canvas canvas to draw on
     */
	private void drawMap(Canvas canvas) {
		level.updateVisibleArea();
        Bitmap [] bitmapsToDraw = {level.visibleLevelBitmap(), level.visibleFruitBitmap(),
                level.getSnake().getScaledBitmap().getScaledBitmap()};
        for(Bitmap bitmap: bitmapsToDraw) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                canvas.drawBitmap(bitmap, 0,
                        (level.getScreenSize().y - bitmap.getHeight()) / 2, paint);
            } else {
                canvas.drawBitmap(bitmap,
                        (level.getScreenSize().x - bitmap.getWidth()) / 2, 0, paint);

            }
        }
	}


	protected void onCreate() {
        level.setView(this);
		level.setPaused(false);
		level.setScore(0);
		level.loadMap();
		level.generateSnake();
        level.scaleMap();

		gestureDetector = new GestureDetector(getContext(),
				new GestureProcessor(level));

		if (spawnFruits) {
			ExtraTools.placeRandomFruit(level);
			ExtraTools.placeRandomFruit(level);
		}

		paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		pause = BitmapFactory.decodeResource(getResources(), R.drawable.pause);

		level.mapTextures();
        Game.inst().clock.setLevel(level);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(pause, 0, 0, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText("Score: " + level.getScore(), level.getScreenSize().x - 10, 50, paint);

		// Rescale map on orientation changes
		if (level.getOrientation() != getResources().getConfiguration().orientation) {
			level.scaleMap();
			level.mapTextures();
		}
		if (level.getSnake().hasDied())
			level.onLose();

		drawMap(canvas);
	}




	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return gestureDetector.onTouchEvent(e);
	}

}
