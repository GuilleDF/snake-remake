package com.snakeremake.menu;

import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.snakeremake.R;
import com.snakeremake.activity.MenuActivity;
import com.snakeremake.activity.SplashActivity;

import java.io.Serializable;


public class ActionScoreboard implements Action, Serializable{

    private static final long serialVersionUID = 7146419597953950924L;

    public void runAction(MenuActivity ma, AdapterView<?> parent,
                          View v, int position, String param, long id){
        String s = (String)ma.getText(R.string.leaderboard_points);
        GoogleApiClient gap = SplashActivity.getApi();
        ma.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gap,
                s), 5);
    }
}
