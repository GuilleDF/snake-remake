package com.snakeremake.menu;

import android.view.View;
import android.widget.AdapterView;

import com.snakeremake.activity.MenuActivity;

public interface Action{
	
	public void runAction(MenuActivity ma, AdapterView<?> parent,
			View v, int position, String param, long id);
}
