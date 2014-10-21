package com.snakeremake.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.snakeremake.main.Level;
import com.snakeremake.views.SplashView;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {
    boolean initBefore;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BaseActivity.googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
	}

    @Override
    protected void onStart() {
        super.onStart();
        if(initBefore)return;
        initBefore = true;
        SplashView splash = new SplashView(this);
        setContentView(splash);
        start();
    }


    private void start(){
        Thread background = new Thread() {
            public void run() {
                Log.i("Snake-Remake","Starting Splash!");
                Level.loadLevels(SplashActivity.this);
                if(!BaseActivity.mExplicitSignOut){BaseActivity.googleApiClient.connect();
                    BaseActivity.googleApiClient.blockingConnect(5, TimeUnit.SECONDS);
                }
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
