package cn.joy.android.demo;

import org.json.JSONArray;

import cn.joy.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        System.out.println("Start...");
        /*
        TextView textView = (TextView)this.findViewById(R.id.display);
        //textView.setText(Html.fromHtml("http://mp.weixin.qq.com/s?__biz=MjM5NTQ2Njc2MQ==&mid=200697712&idx=2&sn=a412790110ab24019c70f6f63f85c79c&scene=1&from=groupmessage&isappinstalled=0#rd"));
        textView.setText(Html.fromHtml("&mid&bcy&blank", null, new TagHandler() {
			@Override
			public void handleTag(boolean arg0, String arg1, Editable arg2, XMLReader arg3) {
				Log.e("", arg1);
				
			}
		}));
        */
        Button listview1Btn = (Button)this.findViewById(R.id.listview1);
        listview1Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toListview1Intent = new Intent(MainActivity.this, ListviewActivity.class);
				startActivity(toListview1Intent);
			}
		});
        
    }
    
    public void toListView2(View source){
    	Intent toListview2Intent = new Intent(getApplicationContext(), CustomListviewActivity.class);
		startActivity(toListview2Intent);
    }
    
    public void toSendMsg(View source){
    	Intent toSendMsgIntent = new Intent(getApplicationContext(), SmsActivity.class);
		startActivity(toSendMsgIntent);
    }
    
    public void toSendMsg2(View source){
    	Intent toSendMsg2Intent = new Intent(getApplicationContext(), Sms2Activity.class);
		startActivity(toSendMsg2Intent);
    }
    
    public void toTestFormSpinner(View source){
    	Intent intent = new Intent(getApplicationContext(), TestFormActivity.class);
		startActivity(intent);
    }
    
    public void toTestArcgis(View source){
    	Intent intent = new Intent(getApplicationContext(), TestArcgisActivity.class);
		startActivity(intent);
    }
    
    public void toTestGaode(View source){
    	Intent intent = new Intent(getApplicationContext(), LocationNetworkActivity.class);
		startActivity(intent);
    }
    
    public void toTestTextLink(View source){
    	Intent intent = new Intent(getApplicationContext(), TestTextViewSpanActivity.class);
    	startActivity(intent);
    }
    
    public void toTestTask(View source){
    	Intent intent = new Intent(getApplicationContext(), TestTaskActivity.class);
    	startActivity(intent);
    }
    
    public void toTestTextStyle(View source) throws Exception{
    	String s = "[\"1111111\",\"2222222\"]";
    	JSONArray ja = new JSONArray(s);
    	
    	Intent intent = new Intent(getApplicationContext(), TextStyleActivity.class);
    	startActivity(intent);
    }
    
    public void toTestVideo(View source) throws Exception{
    	Intent intent = new Intent(getApplicationContext(), TestVideoActivity.class);
    	startActivity(intent);
    }
}