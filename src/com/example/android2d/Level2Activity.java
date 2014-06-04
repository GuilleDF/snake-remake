package com.example.android2d;

import android.graphics.Point;
import android.os.Bundle;

public class Level2Activity extends BaseLevelActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		levelView = new BackgroundView(this, new Point(4, 27), 5, true,
				R.drawable.bg_3030);
		super.onCreate(savedInstanceState);
	}

}
