package cn.joy.android.demo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.joy.android.demo.adapter.MyListAdapter;

import cn.joy.android.R;

public class CustomListviewActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customlistview);

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		map1.put("user_name", "zhangsan");
		map1.put("user_ip", "192.168.0.1");
		map2.put("user_name", "zhangsan");
		map2.put("user_ip", "192.168.0.2");
		map3.put("user_name", "wangwu");
		map3.put("user_ip", "192.168.0.3");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		MyListAdapter listAdapter = new MyListAdapter(this, list, R.layout.custom_list_item, new String[] { "user_name", "user_ip" }, new int[] { R.id.user_name, R.id.user_ip });
		setListAdapter(listAdapter);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "您长按的是：" + ((TextView)view.findViewById(R.id.user_name)).getText(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println("id----------------" + id);
		System.out.println("position----------" + position);
		
	}

}
