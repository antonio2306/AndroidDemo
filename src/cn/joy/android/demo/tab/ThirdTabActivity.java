package cn.joy.android.demo.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdTabActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState); 
		 
	        TextView textview = new TextView(this); 
	        textview.setText("This is the third tab"); 
	        setContentView(textview); 
	}


}
