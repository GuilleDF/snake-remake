package com.snakeremake.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.snakeremake.core.snake.Fruit;
import com.snakeremake.main.Level;
import com.snakeremake.render.ScaledBitmap;
import com.snakeremake.render.TextureMap;

import java.util.Random;

public class ExtraTools {

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static Point getScreenSize(Context context) {
        Point size = new Point();
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT >= 13)
            display.getSize(size);
        else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }

        return size;

    }

    public static Point placeRandomFruit(Level level) {
        int x, y;
        do {
            Random rand = new Random();
            x = rand.nextInt(level.getMaps()[1].numBlocksX());
            y = rand.nextInt(level.getMaps()[1].numBlocksY());
        } while (level.getMaps()[1].getBlock(x, y) != TextureMap.TRANSPARENT ||
                level.getMaps()[2].getBlock(x, y) != TextureMap.TRANSPARENT ||
                level.getMaps()[0].getBlock(x, y) != TextureMap.FLOOR
                );

        Fruit fruit = new Fruit(x, y);
        fruit.place(level.getMaps()[1]);

        return new Point(x, y);

    }
}