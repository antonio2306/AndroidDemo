package cn.joy.android.demo;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import cn.joy.android.R;
import cn.joy.android.demo.tab.FirstTabActivity;
import cn.joy.android.demo.tab.SecondTabActivity;
import cn.joy.android.demo.tab.ThirdTabActivity;

public class TabviewActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabview);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, FirstTabActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tab1").setIndicator("Artists", res.getDrawable(R.drawable.h3c)).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, SecondTabActivity.class);
		spec = tabHost.newTabSpec("tab2").setIndicator("Albums", res.getDrawable(R.drawable.ic_tab)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ThirdTabActivity.class);
		spec = tabHost.newTabSpec("tab3").setIndicator("Songs", res.getDrawable(R.drawable.ic_tab)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(1);
	}



}
