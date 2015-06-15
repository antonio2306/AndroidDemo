package cn.joy.android.demo.test.launchmode;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.LinearLayout; 
import android.widget.TextView;
public class ActA extends Activity { 
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        TextView textView = new TextView(this); 
        textView.setText(this + ""); 
        
        TextView textView2 = new TextView(this); 
        textView2.setText("task id: "+this.getTaskId());
        
        Button button1 = new Button(this); 
        button1.setText("go actA"); 
        button1.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View v) { 
                Intent intent = new Intent(); 
                intent.setClass(ActA.this, ActA.class); 
                startActivity(intent); 
            } 
        }); 
        
        Button button = new Button(this); 
        button.setText("go actB"); 
        button.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View v) { 
                Intent intent = new Intent(); 
                intent.setClass(ActA.this, ActB.class); 
                startActivity(intent); 
            } 
        }); 
        LinearLayout layout = new LinearLayout(this); 
        layout.setOrientation(LinearLayout.VERTICAL); 
        layout.addView(textView); 
        layout.addView(textView2); 
        layout.addView(button1); 
        layout.addView(button); 
        this.setContentView(layout); 
    } 
}