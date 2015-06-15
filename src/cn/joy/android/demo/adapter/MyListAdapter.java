package cn.joy.android.demo.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import cn.joy.android.R;

public class MyListAdapter extends SimpleAdapter {
	// 用于动态的载入一个界面文件
	private LayoutInflater inflater = null;

	private List<Map<String, Object>> styles = null;

	public List<Map<String, Object>> getStyles() {
		return styles;
	}

	public void setStyles(List<Map<String, Object>> styles) {
		this.styles = styles;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View result = super.getView(position, convertView, parent);
		System.out.println("positon---->" + position);
		if (result != null) {
			inflater.inflate(R.layout.custom_list_item, null);
		}
		return result;
	}

	public MyListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// 获取inflater对象
		inflater = LayoutInflater.from(context);
	}

}
