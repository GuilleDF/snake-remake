package com.snakeremake.main;

import android.content.Intent;

import com.snakeremake.activity.BaseLevelActivity;
import com.snakeremake.activity.MenuActivity;

public class Game {
	

	public static Game inst;
	public static Game inst(){
		return inst;
	}
	
	
	private static Level level;
	
	public Game(MenuActivity ma,Level level){
		setLevel(level);
		ma.startActivity(new Intent(ma, BaseLevelActivity.class));
	}

	public void setLevel(Level level){
		Game.level = level;
	}
	
	public static Level getLevel() {
		return level;
	}
	
	


}
