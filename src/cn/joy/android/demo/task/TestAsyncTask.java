package cn.joy.android.demo.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import cn.joy.android.demo.TestTaskActivity;
import cn.joy.android.demo.utils.HttpUtil;

public class TestAsyncTask extends AsyncTask<String, Integer, String> {

	private static final String TAG="TestAsyncTask";
	
	private ProgressDialog dialog;
	
	private TestTaskActivity mActivity;
	
	private String info = "";
	
	public TestAsyncTask(TestTaskActivity activity, String info){
		this.mActivity=activity;
		this.info = info;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog=new ProgressDialog(mActivity);
		dialog.setMessage("...");
		dialog.setCancelable(false);
		dialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		//Toast.makeText(mActivity, info, Toast.LENGTH_LONG).show();
		System.out.println("info="+info);
		
		HttpUtil.testRequest();
    	
		return info;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.dismiss();
		if(result==null){
			Toast.makeText(mActivity, "xxxx", Toast.LENGTH_LONG).show();

			//Intent syncIntent=new Intent(mActivity,SyncNoteService.class);
			//mActivity.startService(syncIntent);
			
			/*Intent intent=new Intent(mActivity,MainActivity.class);
			intent.putExtra("fromlogin", true);
			mActivity.startActivity(intent);
			mActivity.finish();*/
		}else{
			Toast.makeText(mActivity, result, Toast.LENGTH_LONG).show();
		}
	}
}
