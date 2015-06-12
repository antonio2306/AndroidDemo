package cn.joy.android.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import cn.joy.android.demo.listener.OnOptionsSelectedLinstener;

import cn.joy.android.R;

public class PopselectOptionsAdapter extends BaseOptionsAdapter {

    public PopselectOptionsAdapter(Context context,List<String> list,OnOptionsSelectedLinstener listener){
    	super(context, list, listener, R.layout.popselect_option_item);
    }
    
    public PopselectOptionsAdapter(Context context,List<String> list,OnOptionsSelectedLinstener listener, int optionView){
    	super(context, list, listener, optionView);
    }
    
	
    @Override
    public void afterViewHandle(String[] ss, View view){
    	if(flag!=null&&flag.equals(ss[0]))
    		view.findViewById(R.id.selected).setVisibility(View.VISIBLE);
    	else
    		view.findViewById(R.id.selected).setVisibility(View.INVISIBLE);
    }

}