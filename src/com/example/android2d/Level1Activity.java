package com.example.android2d;

import android.graphics.Point;
import android.os.Bundle;

public class Level1Activity extends BaseLevelActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		levelView = new BackgroundView(this, new Point(5, 15), 5, true,
				R.drawable.bg_2020);
		super.onCreate(savedInstanceState);
	}

}
