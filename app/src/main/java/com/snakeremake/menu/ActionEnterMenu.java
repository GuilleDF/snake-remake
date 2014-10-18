package com.snakeremake.menu;

import java.io.Serializable;
import java.util.HashMap;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.snakeremake.activity.MenuActivity;

public class ActionEnterMenu implements Action, Serializable {

	private static final long serialVersionUID = 7146419597953950994L;
	private HashMap<String, Action> extra;

	public ActionEnterMenu(HashMap<String, Action> extra) {
		this.extra = extra;

	}

	@Override
	public void runAction(MenuActivity ma, AdapterView<?> parent, View v,
			int position, String param, long id) {
		Intent i = new Intent(ma, MenuActivity.class);
		if (extra != null){
			i.putExtra("list", extra);
			ma.startActivity(i);			
		}

	}

}
