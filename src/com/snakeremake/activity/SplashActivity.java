package com.snakeremake.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.snakeremake.main.Level;
import com.snakeremake.menu.Action;
import com.snakeremake.menu.ActionEnterMenu;
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
		Thread background = new Thread(){
			public void run(){
				Level.loadLevels(SplashActivity.this);
				Intent in = new Intent(SplashActivity.this, MenuActivity.class);
				HashMap<String,Action> map = new HashMap<String,Action>();
				map.put("Un jugador", new ActionEnterMenu(Level.generateHashMap()));
				map.put("Multijugador", null);
				in.putExtra("list", map);
				startActivity(in);				
				finish();
			}
		};
		
		background.start();
	}
}
