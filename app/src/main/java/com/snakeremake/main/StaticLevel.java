package com.snakeremake.main;

import android.content.Context;

import com.snakeremake.views.LevelView;

import java.util.List;

public class StaticLevel extends Level {

	public StaticLevel(String name, int levelID, LevelType type, int snakeSize,
                       boolean spawnFruits, int levelResourceID, List<PixelData> pixelData) {
		super(name, levelID, type, snakeSize, spawnFruits, levelResourceID, pixelData);
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
