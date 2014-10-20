package com.snakeremake.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.snakeremake.R;
import com.snakeremake.menu.Action;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class MenuActivity extends Activity {
	
	private HashMap<String,Action> actions;
	MenuActivity ma = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.level_select_layout);
				
		actions = (HashMap<String, Action>) getIntent().getSerializableExtra("list");
		String[] names = getNames();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
		
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(adapter);
		
		listView1.setOnItemClickListener(mMessageClickedHandler); 
	}
	
	
	private OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	for(Entry<String,Action> element: actions.entrySet()){
                int pos = Integer.parseInt(element.getKey().split(":")[0]);
	    		if(pos==position){
	    			element.getValue().runAction(ma, parent, v, position, element.getKey(), id);
	    			break;
	    		}
	    	}
	    }
	};
	
	private String[] getNames(){ 
		String[] names = new String[actions.size()];
		Set<Entry<String, Action>> set = actions.entrySet();
		for (Entry<String,Action> element:set){			
			String key = element.getKey();
            String[] parsed = key.split(":");
            names[Integer.parseInt(parsed[0])] = parsed[1];
		}
		return names;
	}
	

}
