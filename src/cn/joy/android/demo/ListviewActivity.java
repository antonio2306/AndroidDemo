package cn.joy.android.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.joy.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		ListView listView = (ListView) findViewById(R.id.my_list);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> item = new HashMap<String, String>();
		item.put("name", "刘备");
		item.put("address", "刘备是三国时期的著名政治家");
		data.add(item);

		item = new HashMap<String, String>();
		item.put("name", "关羽");
		item.put("address", "忠义神武灵佑仁勇威显关圣大帝");
		data.add(item);

		item = new HashMap<String, String>();
		item.put("name", "张飞");
		item.put("address", "三国时期蜀汉重要将领。官至车骑将军。");
		data.add(item);

		item = new HashMap<String, String>();
		item.put("name", "赵云");
		item.put("address", "长坂坡。被刘备誉为“子龙一身都是胆也”");
		data.add(item);
		listView.setAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, 
				new String[] { "name", "address" }, new int[] { android.R.id.text1, android.R.id.text2 }));

		/*
		 * // 定义数组 String[] data = { "Android开发基础篇 第一讲", "Android开发基础篇 第二讲",
		 * "Android开发基础篇 第三讲", "Android开发基础篇 第四讲", "Android开发基础篇 第五讲",
		 * "Android开发基础篇 第六讲", "Android开发基础篇 第七讲", "Android开发基础篇 第八讲",
		 * "Android开发基础篇 第九讲" };
		 * 
		 * // 为ListView提供数组适配器 //listView.setAdapter(new ArrayAdapter(this,
		 * android.R.layout.simple_list_item_1, data)); listView.setAdapter(new
		 * ArrayAdapter(this, R.layout.simple_list_item, data));
		 */

		// 为ListView设置列表项点击监听器
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getApplicationContext(), "您点击的是：" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "您点击的是：" + ((TextView) view.findViewById(android.R.id.text1)).getText(), Toast.LENGTH_SHORT).show();
			}
		});

		// 为ListView设置长按监听器
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), "您长按的是：" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}


}
