package com.example.android2d;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Extend this class and override {@link #onCreate(Bundle)} to set {@link #levelView}
 * to the desired level
 * @author gdefermin
 *
 */
public class BaseLevelActivity extends Activity{
	
	protected BackgroundView levelView;
	private GameOverView gameOverView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
					getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//bgView = new BackgroundView(this, R.drawable.bg_2020);
		//extend class and set bgView for each level
		gameOverView = new GameOverView(this);
		
		setContentView(levelView);
		levelView.requestFocus();
		
	}
	
	public void onGameOver(){
		setContentView(gameOverView);
		gameOverView.requestFocus();
	}
	
	public void restartGame(){
		finish();
	}

}
