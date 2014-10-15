package com.snakeremake.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.snakeremake.R;
import com.snakeremake.main.Level;
import com.snakeremake.menu.Action;
import com.snakeremake.menu.ActionEnterMenu;
import com.snakeremake.views.SplashView;

public class SplashActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener {
	GoogleApiClient mGoogleApiClient;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addApi(Games.API)
				.addScope(Games.SCOPE_GAMES)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SplashView splash = new SplashView(this);
		setContentView(splash);
		Thread background = new Thread() {
			public void run() {
				Level.loadLevels(SplashActivity.this);
				Intent in = new Intent(SplashActivity.this, MenuActivity.class);
				HashMap<String, Action> map = new HashMap<String, Action>();
				map.put((String) getResources().getText(R.string.single_player),
						new ActionEnterMenu(Level.generateHashMap()));
				map.put((String) getResources().getText(R.string.multiplayer),
						null);
				in.putExtra("list", map);
				startActivity(in);
				finish();
			}
		};

		background.start();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		Log.i("Snake-Remake", "Connection Failed!");
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.i("Snake-Remake", "Connected!");
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		Log.i("Snake-Remake", "Connection suspended");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		mGoogleApiClient.disconnect();
		super.onStop();
	}

}
