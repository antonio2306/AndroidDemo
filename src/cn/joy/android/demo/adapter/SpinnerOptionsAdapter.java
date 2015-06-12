package cn.joy.android.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import cn.joy.android.R;
import cn.joy.android.demo.listener.OnOptionsSelectedLinstener;

public class SpinnerOptionsAdapter extends BaseOptionsAdapter {

    public SpinnerOptionsAdapter(Context context,List<String> list,OnOptionsSelectedLinstener listener){
    	super(context, list, listener, R.layout.spinner_option_item);
    }
    
    public SpinnerOptionsAdapter(Context context,List<String> list,OnOptionsSelectedLinstener listener, int optionView){
    	super(context, list, listener, optionView);
    }
	
    @Override
    public void afterViewHandle(String[] ss, View view) {
    	 if(flag!=null&&flag.equals(ss[0])){
         	view.setBackgroundResource(R.drawable.option_item_selected);
         }else{
         	view.setBackgroundResource(R.drawable.option_item_bg);
 	    }
    }
	
}