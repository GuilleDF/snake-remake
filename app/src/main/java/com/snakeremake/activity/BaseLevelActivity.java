package com.snakeremake.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.games.Games;
import com.snakeremake.R;
import com.snakeremake.main.Game;
import com.snakeremake.views.BaseLevelView;
import com.snakeremake.views.GameOverView;

/**
 * Extend this class and override {@link #setLevelView()} to return
 * the desired level
 * 
 * @author GuilleDF
 * 
 */


public class BaseLevelActivity extends Activity {

	private BaseLevelView levelView;
	private GameOverView gameOverView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// No status bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// No action bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		levelView = setLevelView();
		gameOverView = new GameOverView(this);

		// Focus levelView
		setContentView(levelView);
		levelView.requestFocus();

	}

	protected BaseLevelView setLevelView(){
		return Game.inst().getLevel().getView(this);
	}

	public void onGameOver(int score) {
		gameOverView.setScore(score);
		setContentView(gameOverView);
		gameOverView.requestFocus();
        if(SplashActivity.getApi()!=null){
            String id = getString(R.string.leaderboard_points);
            Games.Leaderboards.submitScore(SplashActivity.getApi(), id, score);
        }
    }

	public void restartGame() {
		// Closes this activity, falling back to it's parent
		finish();
	}

}
