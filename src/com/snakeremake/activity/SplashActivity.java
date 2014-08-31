package com.snakeremake.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.snakeremake.main.Level;
import com.snakeremake.views.SplashView;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		SplashView splash = new SplashView(this);
		setContentView(splash);
		
		Level.loadLevels(this);
		startActivity(new Intent(this, MenuActivity.class));
		finish();
	}
}
