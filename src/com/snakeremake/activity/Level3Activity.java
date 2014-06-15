package com.snakeremake.activity;

import com.example.android2d.R;
import com.example.android2d.R.drawable;
import com.snakeremake.views.BaseLevelView;
import com.snakeremake.views.ScrollingLevelView;

import android.graphics.Point;

public class Level3Activity extends BaseLevelActivity {

	@Override
	protected BaseLevelView setLevelView() {
		return new ScrollingLevelView(this, new Point(3, 27), 5, true,
				R.drawable.bg_3030, new Point(0, 10), new Point(20, 20));
	}

}