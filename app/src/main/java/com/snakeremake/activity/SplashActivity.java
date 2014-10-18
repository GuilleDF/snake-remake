package com.snakeremake.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.snakeremake.main.Level;
import com.snakeremake.views.SplashView;

public class SplashActivity extends Activity {

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
        SplashView splash = new SplashView(this);
        setContentView(splash);
        super.onStart();
        if(!BaseActivity.mExplicitSignOut){BaseActivity.googleApiClient.connect();}
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i<=5;i++){
                        if(BaseActivity.isLoggedIn()){
                            start();
                            break;
                        }
                        Thread.sleep(200);
                    }
                    start();
                } catch (InterruptedException e) {}
            }
        });
        t.start();
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
