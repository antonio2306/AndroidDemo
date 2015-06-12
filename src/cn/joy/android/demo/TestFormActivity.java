package cn.joy.android.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

import cn.joy.android.demo.adapter.PopselectOptionsAdapter;
import cn.joy.android.demo.component.FormSpinner;
import cn.joy.android.demo.listener.OnOptionsSelectedLinstener;

import cn.joy.android.R;

public class TestFormActivity extends Activity implements OnOptionsSelectedLinstener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		List<String> list = new ArrayList();
		list.add("语文");
		list.add("数学");
		list.add("英语");
		list.add("物理");
		list.add("化学");
		list.add("社会主义初级阶段政治经济洗脑学");
		list.add("历史");
		list.add("地理");
		list.add("生物");
		list.add("体育");
		list.add("计算机");

		FormSpinner spinner = new FormSpinner(this, list);
		spinner.setOptionsTitle("");
		spinner.setLayoutParams(new LayoutParams(450, 55));
		((ViewGroup) this.findViewById(R.id.formLayout)).addView(spinner);

	}

	private PopupWindow selectPopupWindow;

	public void selectFormType(View v) {
		if (selectPopupWindow != null && selectPopupWindow.isShowing()) {
			selectPopupWindow.dismiss();
			return;
		}
		View optionwindow = (View) this.getLayoutInflater().inflate(R.layout.popselect_options, null);
		ListView optionListView = (ListView) optionwindow.findViewById(R.id.list);

		List<String> types = new ArrayList();
		types.add("1,通用表单");
		types.add("2,出工单");
		types.add("3,请假单");
		types.add("4,名字可以起的很长很长的表单");
		types.add("11,通用表单1");
		types.add("12,出工单1");
		types.add("13,请假单1");
		types.add("21,通用表单2");
		types.add("22,出工单2");
		types.add("23,请假单2");
		types.add("31,通用表单3");
		types.add("32,出工单3");
		types.add("33,请假单3");
		types.add("41,通用表单4");
		types.add("42,出工单4");
		types.add("43,请假单4");
		// 设置自定义Adapter
		PopselectOptionsAdapter optionsAdapter = new PopselectOptionsAdapter(this, types, this, R.layout.popselect_option_item);
		//optionsAdapter.setFlag(this.fid);
		optionsAdapter.setFlag("3");
		optionListView.setAdapter(optionsAdapter);
		
		WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		int h = wm.getDefaultDisplay().getHeight();  
		optionListView.getLayoutParams().height = h/2;

		selectPopupWindow = new PopupWindow(optionwindow, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

		selectPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});

		selectPopupWindow.setOutsideTouchable(true);
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		//selectPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		
		//selectPopupWindow.showAtLocation(findViewById(R.id.parent), Gravity.CENTER| Gravity.CENTER, 0, 0);
		selectPopupWindow.showAsDropDown(v, 0, 0);
		selectPopupWindow.update();
		/*
		ViewFlipper mViewFlipper = new ViewFlipper(TestFormActivity.this);
		
		TranslateAnimation animation1 = new TranslateAnimation(0, 0, -300, 0); 
		animation1.setDuration(1000);//设置动画持续时间 
		
		TranslateAnimation animation2 = new TranslateAnimation(0, 0, 0, -300); 
		animation2.setDuration(1000);//设置动画持续时间 
		mViewFlipper.setInAnimation(animation1);
		mViewFlipper.setOutAnimation(animation2);
		mViewFlipper.addView(optionwindow);
		mViewFlipper.setFlipInterval(3000);
		                     
		selectPopupWindow = new PopupWindow(mViewFlipper, 1080, 720);
		
		selectPopupWindow = new PopupWindow(optionwindow, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

		selectPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
			}
		});

		selectPopupWindow.setOutsideTouchable(true);
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		//selectPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		
		//selectPopupWindow.showAtLocation(findViewById(R.id.parent), Gravity.CENTER| Gravity.CENTER, 0, 0);
		selectPopupWindow.showAsDropDown(v, 0, 5);
		selectPopupWindow.update();
		
		mViewFlipper.showNext();*/
	}

	public void onSelected(String option) {
		selectPopupWindow.dismiss();
		//((TextView)this.findViewById(R.id.selectformtypevalue)).setText(option);
		
		Toast.makeText(this, "选择的是：" + option, Toast.LENGTH_SHORT).show();
	}
}
