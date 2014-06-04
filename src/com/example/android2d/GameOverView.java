package com.example.android2d;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameOverView extends View {
	private Point screenSize;
	private Paint paint;
	private String gameOver;
	private int score;
	public GameOverView(Context context) {
		super(context);
		
		screenSize = new Point(ExtraTools.getScreenSize(context));
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		
		gameOver = context.getString(R.string.game_over);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawRGB(0, 0, 0);
		canvas.drawText(gameOver, screenSize.x/2, screenSize.y/2, paint);
		canvas.drawText("Score: " + score, screenSize.x/2, screenSize.y*3/4, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e){
		BaseLevelActivity host = (BaseLevelActivity) getContext();
		host.restartGame();
		return true;
	}

	public void setScore(int score) {
		this.score = score;		
	}
	
}
