package com.example.android2d;

import android.graphics.Point;

public class Level3Activity extends BaseLevelActivity {

	@Override
	protected BaseLevelView setLevelView() {
		return new ScrollingLevelView(this, new Point(3, 27), 5, true,
				R.drawable.bg_3030, new Point(0, 10), new Point(20, 20));
	}

}
