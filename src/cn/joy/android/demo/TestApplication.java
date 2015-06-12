package cn.joy.android.demo;

import android.app.Application;

public class TestApplication extends Application {
	
	private static TestApplication mInstance = null;
    public boolean m_bKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	
	public static TestApplication getInstance() {
		return mInstance;
	}
}
