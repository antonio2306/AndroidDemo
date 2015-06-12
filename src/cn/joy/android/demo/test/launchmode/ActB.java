package cn.joy.android.demo.test.launchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActB extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText(this + "");

		TextView textView2 = new TextView(this);
		textView2.setText("task id: " + this.getTaskId());

		Button button = new Button(this);
		button.setText("go actA");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ActB.this, ActA.class);
				startActivity(intent);
			}
		});
		
		Button button2 = new Button(this);
		button2.setText("go actC");
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ActB.this, ActC.class);
				startActivity(intent);
			}
		});
		LinearLayout layout = new LinearLayout(this);
		layout.addView(textView); 
        layout.addView(textView2); 
		layout.addView(button);
		layout.addView(button2);
		this.setContentView(layout);
	}
}
