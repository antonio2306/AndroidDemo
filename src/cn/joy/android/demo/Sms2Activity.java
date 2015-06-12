package cn.joy.android.demo;

import java.util.ArrayList;

import cn.joy.android.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sms2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_main);

		Button btn = (Button) this.findViewById(R.id.btnSend);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phoneNo = ((EditText) Sms2Activity.this
						.findViewById(R.id.edtPhoneNo)).getText().toString();
				String message = ((EditText) Sms2Activity.this
						.findViewById(R.id.edtContent)).getText().toString();
				
				if (phoneNo.length() > 0 && message.length() > 0) {
					 Intent smsIntent=new Intent(Intent.ACTION_SENDTO,
							 Uri.parse("sms:"+phoneNo));
					 smsIntent.putExtra("sms_body", message);
					 Sms2Activity.this.startActivity(smsIntent);
					 
					 /*
					 Uri attached_Uri = Uri.parse("content://media/external/images/media/1");
					// Create a new MMS intent
					Intent mmsIntent = new Intent(Intent.ACTION_SEND, attached_Uri);
					mmsIntent.putExtra("sms_body", edtContent.getText().toString());
					mmsIntent.putExtra("address", edtPhoneNo.getText().toString());
					mmsIntent.putExtra(Intent.EXTRA_STREAM, attached_Uri);
					mmsIntent.setType("image/png");
					startActivity(mmsIntent);*/
				} else
					Toast.makeText(getBaseContext(),
							"Please enter both phone number and message.",
							Toast.LENGTH_SHORT).show();

			}
		});

	}

	
}
