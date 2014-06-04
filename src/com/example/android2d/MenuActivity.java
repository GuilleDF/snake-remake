package com.example.android2d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MenuActivity extends Activity {
	private Intent startGameIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		startGameIntent = new Intent(this, MainActivity.class);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.menu_layout);
	}
	
	public void onClickPlay(View view){
		startActivity(startGameIntent);
	}

}
