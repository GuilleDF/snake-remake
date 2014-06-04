package com.example;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.android2d.R;

public class GameOverView extends View {
	private Point screenSize;
	private Paint paint;
	private String gameOver;
	public GameOverView(Context context) {
		super(context);
		
		screenSize = new Point(ExtraTools.getScreenSize(context));
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		
		gameOver = context.getString(R.string.game_over);
		new RectF(screenSize.x/4, screenSize.y*3/8, screenSize.x*3/4, screenSize.y*5/8);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		canvas.drawRGB(0, 0, 0);
		canvas.drawText(gameOver, screenSize.x*3/8, screenSize.y/2, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e){
		MainActivity host = (MainActivity) getContext();
		host.restartGame();
		return true;
	}
	
}
