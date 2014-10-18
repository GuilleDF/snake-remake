package com.snakeremake.activity;

import com.snakeremake.main.Game;
import com.snakeremake.views.LevelView;
import com.snakeremake.views.GameOverView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Extend this class and override {@link #setLevelView()} to return
 * the desired level
 * 
 * @author GuilleDF
 * 
 */


public class LevelActivity extends Activity {

	private LevelView levelView;
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

	protected LevelView setLevelView(){
		return Game.inst().getLevel().getView(this);
	}

	public void onGameOver(int score) {
		gameOverView.setScore(score);
		setContentView(gameOverView);
		gameOverView.requestFocus();
	}

	public void restartGame() {
		// Closes this activity, falling back to it's parent
		finish();
	}

}
