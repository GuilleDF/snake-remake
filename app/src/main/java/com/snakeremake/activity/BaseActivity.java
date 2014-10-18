package com.snakeremake.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
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

    public static boolean mExplicitSignOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.base_layout);
        enableDebugLog(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mExplicitSignOut)start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void start(){
        if(isLoggedIn()){
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            TextView textview = (TextView) findViewById(R.id.textSignedIn);
            try{
                textview.setText("Logged in as "+Games.getCurrentAccountName(googleApiClient));
            }catch(Exception ex){}
        }
    }

    @Override
    public void onSignInFailed() {
        Log.i("Snake-Remake", "Sign in failed.");
        loggedIn=1;
    }

    @Override
    public void onSignInSucceeded() {
        Log.i("Snake-Remake", "Sign in succeeded");
        start();
    }

    public void onClick (View view) {
        if( view.getId() == R.id.test_button){
            Log.i("Snake-Remake",isLoggedIn()+"");
        }
        if (view.getId() == R.id.sign_in_button) {
            beginUserInitiatedSignIn();
            start();
            loggedIn=0;
        } else if (view.getId() == R.id.sign_out_button) {
            signOut();
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
            TextView textview = (TextView) findViewById(R.id.textSignedIn);
            textview.setText("You are not logged in.");
            mExplicitSignOut = true;
            loggedIn=1;
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

            try{
                String s = Games.getCurrentAccountName(googleApiClient);
                loggedIn = (s!=null) ? 0 : 1;
            }catch(Exception ex){
                loggedIn = 1;
            }

        return loggedIn==0;
    }
}
