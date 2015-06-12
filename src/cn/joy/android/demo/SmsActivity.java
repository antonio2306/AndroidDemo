package cn.joy.android.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.joy.android.R;

public class SmsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_main);

		Button btn = (Button) this.findViewById(R.id.btnSend);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phoneNo = ((EditText) SmsActivity.this
						.findViewById(R.id.edtPhoneNo)).getText().toString();
				String message = ((EditText) SmsActivity.this
						.findViewById(R.id.edtContent)).getText().toString();
				if (phoneNo.length() > 0 && message.length() > 0) {
					// call sendSMS to send message to phoneNo
					sendSMS(phoneNo, message);
				} else
					Toast.makeText(
							getBaseContext(),
							getResources().getString(
									R.string.bothphoneandcontent),
							Toast.LENGTH_SHORT).show();

			}
		});

	}

	private void sendSMS(String phoneNumber, String message) {
		// ---sends an SMS message to another device---
		SmsManager sms = SmsManager.getDefault();
		String SENT_SMS_ACTION = "send_action";
		String DELIVERED_SMS_ACTION = "rec_action";
		String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

		// create the sentIntent parameter
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
				0);

		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
				deliverIntent, 0);

		// register the Broadcast Receivers
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(),
							"发送成功", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(),
							"发送失败", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(),
							"SMS radio off failure actions", Toast.LENGTH_SHORT)
							.show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(),
							"SMS null PDU failure actions", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
		}, new IntentFilter(SENT_SMS_ACTION));
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				Toast.makeText(getBaseContext(), "SMS delivered actions",
						Toast.LENGTH_SHORT).show();
			}
		}, new IntentFilter(DELIVERED_SMS_ACTION));
		
		//registerReceiver(new SmsReceiver(), new IntentFilter(SMS_RECEIVED));
		
		// if message's length more than 70 ,
		// then call divideMessage to dive message into several part ,and call
		// sendTextMessage()
		// else direct call sendTextMessage()
		if (message.length() > 70) {
			ArrayList<String> msgs = sms.divideMessage(message);
			for (String msg : msgs) {
				sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);
			}
		} else {
			sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
		}
	}
	
}
