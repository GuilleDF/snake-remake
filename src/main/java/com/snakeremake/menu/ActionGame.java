package com.snakeremake.menu;

import java.io.Serializable;

import android.view.View;
import android.widget.AdapterView;

import com.snakeremake.activity.MenuActivity;
import com.snakeremake.main.Game;
import com.snakeremake.main.Level;

public class ActionGame implements Action,Serializable {


	private static final long serialVersionUID = 604272286108451048L;
	private String name;
	

	@Override
	public void runAction(MenuActivity ma,AdapterView<?> parent, View v, int position,
			String param, long id) {
		new Game(ma,Level.levels.get(position));
	}


	

}
