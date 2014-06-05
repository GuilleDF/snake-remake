package com.example.android2d;

import android.graphics.Point;

public class Level1Activity extends BaseLevelActivity {

	@Override
	protected StaticLevelView setLevelView() {
		return new StaticLevelView(this, new Point(5, 15), 5, true,
				R.drawable.bg_2020);
	}

}
