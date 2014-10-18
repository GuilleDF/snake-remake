package com.snakeremake.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import com.snakeremake.views.LevelView;

public class StaticLevel extends Level {

	public StaticLevel(String name, int levelID, LevelType type, Point spawnPoint,
			int snakeSize, boolean spawnFruits, int levelResourceID) {
		super(name, levelID, type, spawnPoint, snakeSize, spawnFruits, levelResourceID);
	}

	@Override
	public LevelView getView(Context ctx) {
        view = new LevelView(ctx, spawnFruits, this);

        for(int i=0; i<3; i++){
            visibleBitmaps[i] = getMaps()[i].getScaledBitmap();
        }

		return view;
	}


    // The visible blocks don't change
    @Override
    public int visibleBlocksY() {
        return getMaps()[0].numBlocksY();
    }
    @Override
    public int visibleBlocksX() {
        return getMaps()[0].numBlocksX();
    }

    // Static -- visible area doesn't need to update
    @Override
    public void updateVisibleArea() {}

}
