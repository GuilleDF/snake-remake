package com.snakeremake.main;

import android.content.Intent;

import com.snakeremake.activity.BaseLevelActivity;
import com.snakeremake.activity.MenuActivity;

public class Game {
	
    //Are we using this? I Am
	public static Game inst;
	public static Game inst(){
		return inst;
	}
	
	
	private Level level;
    private Clock clock;

    public Game(MenuActivity ma,Level level){
        inst = this;
        setLevel(level);
        clock = new Clock(20);
        clock.start();
        ma.startActivity(new Intent(ma, BaseLevelActivity.class));
    }


	public void setLevel(Level level){
		Game.inst().level = level;
	}
	
	public Level getLevel() {
		return inst().level;
	}

    public Clock getClock() {return clock;}

}
