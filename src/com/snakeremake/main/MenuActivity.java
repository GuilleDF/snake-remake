package com.snakeremake.main;

import com.example.android2d.R;
import com.example.android2d.R.layout;
import com.snakeremake.activity.Level1Activity;
import com.snakeremake.activity.Level2Activity;
import com.snakeremake.activity.Level3Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MenuActivity extends Activity {
	private Intent level1;
	private Intent level2;
	private Intent level3;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		level1 = new Intent(this, Level1Activity.class);
		level2 = new Intent(this, Level2Activity.class);
		level3 = new Intent(this, Level3Activity.class);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.menu_layout);
	}
	
	public void onClickLevel1(View view){
		startActivity(level1);
	}
	
	public void onClickLevel2(View view){
		startActivity(level2);
	}
	
	public void onClickLevel3(View view){
		startActivity(level3);
	}

}
