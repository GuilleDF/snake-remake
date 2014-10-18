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
	public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

    @Override
    protected void onStart() {
        SplashView splash = new SplashView(this);
        setContentView(splash);
        super.onStart();
        start();
    }

    private void start(){
        Thread background = new Thread() {
            public void run() {
                Level.loadLevels(SplashActivity.this);
                Intent in = new Intent(SplashActivity.this, BaseActivity.class);
                startActivity(in);
                finish();
            }
        };

        background.start();
    }

	@Override
	protected void onStop() {
		super.onStop();
	}
}
