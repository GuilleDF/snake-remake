package com.example.android2d;

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
public abstract class BaseLevelActivity extends Activity {

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

	/**
	 * Override this method to return the desired level view
	 * 
	 * @return levelView
	 */
	protected abstract BaseLevelView setLevelView();

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
