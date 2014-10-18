package com.snakeremake.main;

import android.content.Intent;

import com.snakeremake.activity.LevelActivity;
import com.snakeremake.activity.MenuActivity;

public class Game {

	public static Game inst;
	public static Game inst(){
		return inst;
	}
	
	
	private Level level;

    public Clock clock;
	
	public Game(MenuActivity ma,Level level){
		inst = this;
		setLevel(level);
        clock = new Clock(20);
        clock.start();
		ma.startActivity(new Intent(ma, LevelActivity.class));
	}

	public void setLevel(Level level){
		this.level = level;
	}
	
	public Level getLevel() {
		return level;

	}

    public Clock getClock() {return clock;}

}
