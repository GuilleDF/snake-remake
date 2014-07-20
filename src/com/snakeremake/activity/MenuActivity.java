package com.snakeremake.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.snakeremake.R;
import com.snakeremake.main.Game;
import com.snakeremake.main.Level;

public class MenuActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		
		setContentView(R.layout.menu_layout);
				
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getLevelNames());
		
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(adapter);
		
		listView1.setOnItemClickListener(mMessageClickedHandler); 
	}
	
	
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
	    	new Game(ma, Level.levels.get(position));
	    }
	};
	
	private MenuActivity ma = this;
	
	
	private String[] getLevelNames(){
		String[] levelNames = new String[Level.levels.size()];
		int index = 0;
		for(Level level: Level.levels){
			levelNames[index] = new String(level.getName());
			index++;
			Log.i("Snake-Remake", level.getName());
		}
		return levelNames;
	}
	

}
