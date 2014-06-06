package com.example.android2d;

import android.graphics.Point;

public class Level2Activity extends BaseLevelActivity {

	@Override
	protected BaseLevelView setLevelView() {
		return new StaticLevelView(this, new Point(4, 27), 5, true,
				R.drawable.bg_3030);
	}

}
