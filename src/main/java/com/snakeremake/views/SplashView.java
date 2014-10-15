package com.snakeremake.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import com.snakeremake.R;
import com.snakeremake.utils.ExtraTools;

public class SplashView extends View {
	private Point scrSize;
	private Bitmap logo;
	private Paint paint;

	public SplashView(Context context) {
		super(context);
		scrSize = ExtraTools.getScreenSize(context);
		Bitmap logoRaw = BitmapFactory.decodeResource(getResources(),
				R.drawable.logo);
		logo = Bitmap.createScaledBitmap(logoRaw, scrSize.x * 3 / 4,
				scrSize.y * 1/4,
				false);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawARGB(255, 255, 255, 255);
		canvas.drawBitmap(logo, (scrSize.x - logo.getWidth()) / 2,
				(scrSize.y - logo.getHeight()) / 2, paint);
	}

}
