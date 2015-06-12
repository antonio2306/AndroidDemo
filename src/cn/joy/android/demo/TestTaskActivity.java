package cn.joy.android.demo;

import android.app.Activity;
import android.os.Bundle;

import cn.joy.android.demo.task.TestAsyncTask;

import cn.joy.android.R;

public class TestTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);

		TestAsyncTask asyncTask=new TestAsyncTask(this,"test task");
		asyncTask.execute();

	}

	
}
