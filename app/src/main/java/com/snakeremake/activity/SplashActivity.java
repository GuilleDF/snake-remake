package com.snakeremake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.snakeremake.R;
import com.snakeremake.basegame.BaseGameActivity;
import com.snakeremake.main.Level;
import com.snakeremake.menu.Action;
import com.snakeremake.menu.ActionEnterMenu;
import com.snakeremake.menu.ActionScoreboard;
import com.snakeremake.views.SplashView;

import java.util.HashMap;

public class SplashActivity extends BaseGameActivity{
    private static GoogleApiClient mGoogleApiClient;
    private static GoogleApiClient api;

    private static final int RC_SIGN_IN = 9001;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
	}

    @Override
    protected void onStart() {
        SplashView splash = new SplashView(this);
        setContentView(splash);
        mGoogleApiClient.connect();
        super.onStart();

    }

    private void start(){
        Log.i("Snake-Remake","Connection status:"+mGoogleApiClient.isConnected());
        if(mGoogleApiClient.isConnected()){
            Log.i("Snake-Remake","Welcome back, "+Games.getCurrentAccountName(getApiClient()));
        }
        Thread background = new Thread() {
            public void run() {
                Level.loadLevels(SplashActivity.this);
                Intent in = new Intent(SplashActivity.this, MenuActivity.class);
                HashMap<String, Action> map = new HashMap<String, Action>();
                map.put((String) getResources().getText(R.string.single_player),
                        new ActionEnterMenu(Level.generateHashMap()));
                map.put((String) getResources().getText(R.string.multiplayer),
                        null);
                map.put((String) getResources().getText(R.string.scoreboards), new ActionScoreboard());
                map.put("Connected to google play:"+mGoogleApiClient.isConnected(), null);
                in.putExtra("list", map);
                startActivity(in);
                finish();
            }
        };

        background.start();
        api = getApiClient();
    }

	@Override
	protected void onStop() {
		super.onStop();
        mGoogleApiClient.disconnect();
	}


    @Override
    public void onSignInFailed() {
        Log.i("Snake-Remake","Failed while signing in!");
        start();
    }

    @Override
    public void onSignInSucceeded() {
        Log.i("Snake-Remake","Signed in successfully!");
        start();
    }

    public static GoogleApiClient getApi(){
        return api;
    }
}
