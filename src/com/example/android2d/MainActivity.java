package com.example.android2d;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity{
	
	private BackgroundView bgView;
	private GameOverView gameOverView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
					getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		bgView = new BackgroundView(this);
		gameOverView = new GameOverView(this);
		
		setContentView(bgView);
		bgView.requestFocus();
		
	}
	
	public void onGameOver(){
		setContentView(gameOverView);
		gameOverView.requestFocus();
	}
	
	public void restartGame(){
		finish();
	}

}
