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

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		levelView = setLevelView();
		gameOverView = new GameOverView(this);

		setContentView(levelView);
		levelView.requestFocus();

	}

	protected abstract BaseLevelView setLevelView();

	public void onGameOver(int score) {
		gameOverView.setScore(score);
		setContentView(gameOverView);
		gameOverView.requestFocus();
	}

	public void restartGame() {
		finish();
	}

}
