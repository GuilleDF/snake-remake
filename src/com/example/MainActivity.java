package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class MainActivity extends Activity{
	
	private BackgroundView bgView;
	private GameOverView gameOverView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
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
		bgView = new BackgroundView(this);
		setContentView(bgView);
		bgView.requestFocus();
	}

}
