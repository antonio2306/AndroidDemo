package cn.joy.android.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context _context, Intent _intent) {
		System.out.println(111);
		if (_intent.getAction().equals(SMS_RECEIVER)) {
			SmsManager sms = SmsManager.getDefault();

			Bundle bundle = _intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				for (SmsMessage message : messages) {
					String msg = message.getMessageBody();
					String to = message.getOriginatingAddress();
					if (msg.toLowerCase().startsWith(queryString)) {
						String out = msg.substring(queryString.length());
						sms.sendTextMessage(to, null, out, null, null);

						Toast.makeText(_context, "success", 
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}
    
	private static final String queryString="@echo";
	private static final String SMS_RECEIVER=
		"android.provider.Telephony.SMS_RECEIVED";
}