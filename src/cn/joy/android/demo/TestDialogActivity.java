package cn.joy.android.demo;

import cn.joy.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class TestDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);

		Dialog alertDialog = new AlertDialog.Builder(this).setTitle("退出").setMessage("退出登录。。。")
				.setIcon(R.drawable.ic_launcher).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(TestDialogActivity.this, "确定退出", Toast.LENGTH_LONG).show();
					}
				}).create();
		alertDialog.show();

	}

}
