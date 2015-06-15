package cn.joy.android.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.joy.android.demo.listener.OnOptionsSelectedLinstener;

import cn.joy.android.R;

public class BaseOptionsAdapter extends BaseAdapter {

	protected List<String> list; 
	protected Context context = null; 
    protected OnOptionsSelectedLinstener listener;
    protected String flag;
    protected int optionView;
    
    public void setList(List<String> list){
    	this.list=list;
    }

    public BaseOptionsAdapter(Context context,List<String> list, OnOptionsSelectedLinstener listener, int optionView){
    	this.context = context;
    	this.list = list;
    	this.listener=listener;
    	this.optionView = optionView;
    }
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null; 
        if (convertView == null) { 
        	view = LayoutInflater.from(context).inflate(optionView, null); 
        } else { 
        	view = convertView; 
        } 
        String[] ss=list.get(position).split(",");
        ((TextView)view.findViewById(R.id.option)).setText(ss[ss.length==2?1:0]);
        view.setTag(list.get(position)); 
        view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onSelected(v.getTag().toString());
			}
		});
        
        afterViewHandle(ss, view);
        return view; 
	}
	
	public void afterViewHandle(String[] ss, View view){
		
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}