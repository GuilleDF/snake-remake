package com.snakeremake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.snakeremake.R;
import com.snakeremake.basegame.BaseGameActivity;
import com.snakeremake.main.Level;
import com.snakeremake.menu.Action;
import com.snakeremake.menu.ActionEnterMenu;
import com.snakeremake.menu.ActionScoreboard;

import java.util.HashMap;

/**
 * Created by mcat on 18/10/14.
 */
public class BaseActivity  extends BaseGameActivity {

    public static GoogleApiClient googleApiClient;
    private static int loggedIn = -1;

    private GoogleApiClient mGoogleApiClient;
    private boolean mExplicitSignOut = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.base_layout);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
        enableDebugLog(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mExplicitSignOut)mGoogleApiClient.connect();
        Log.i("Snake-Remake","Connecting.");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void start(){
        if(mGoogleApiClient.isConnected()){
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            TextView textview = (TextView) findViewById(R.id.textSignedIn);
            try{
                textview.setText("Logged in as "+Games.getCurrentAccountName(mGoogleApiClient));
                BaseActivity.googleApiClient = mGoogleApiClient;
            }catch(Exception ex){}
        }
    }

    @Override
    public void onSignInFailed() {
        Log.i("Snake-Remake","Sign in failed.");
        start();
    }

    @Override
    public void onSignInSucceeded() {
        Log.i("Snake-Remake", "Sign in succeeded");
        start();
    }

    public void onClick (View view) {
        if (view.getId() == R.id.sign_in_button) {
            beginUserInitiatedSignIn();
            start();
        } else if (view.getId() == R.id.sign_out_button) {
            signOut();
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            TextView textview = (TextView) findViewById(R.id.textSignedIn);
            textview.setText("You are not logged in.");
            mExplicitSignOut = true;
        } else if(view.getId() == R.id.start_button) {
            Intent in = new Intent(this,MenuActivity.class);
            HashMap<String, Action> map = new HashMap<String, Action>();
            map.put((String) getResources().getText(R.string.single_player),
                    new ActionEnterMenu(Level.generateHashMap()));
            map.put((String) getResources().getText(R.string.multiplayer),
                    null);
            if(isLoggedIn())map.put((String) getResources().getText(R.string.scoreboards), new ActionScoreboard());
            in.putExtra("list", map);
            startActivity(in);
        }
    }

    public static boolean isLoggedIn(){
        if(loggedIn == -1){
            try{
                loggedIn = ( Games.getCurrentAccountName(googleApiClient)!=null) ? 0 : 1;
            }catch(Exception ex){
                loggedIn = 1;
            }
        }
        return loggedIn==0;
    }
}
