package cn.joy.android.demo.component;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import cn.joy.android.demo.adapter.SpinnerOptionsAdapter;
import cn.joy.android.demo.listener.OnOptionsSelectedLinstener;

import cn.joy.android.R;

public class FormSpinner extends LinearLayout implements OnOptionsSelectedLinstener, OnClickListener {

	private List<String> list;

	private boolean flag = false;

	private int pwidth = 0;

	private ListView optionListView = null;

	private SpinnerOptionsAdapter optionsAdapter = null;

	private PopupWindow selectPopupWindow = null;

	private Context context;

	private ImageView arrowImageView;

	private String optionsTitle = "";

	public FormSpinner(Context context) {
		super(context);
		this.context = context;
	}

	public FormSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.spinner, this, true);
		arrowImageView = (ImageView) findViewById(R.id.arrow);
		arrowImageView.setImageResource(R.drawable.input_arrow_down);
	}

	public FormSpinner(Context context, List<String> list) {
		super(context);
		this.list = list;
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.spinner, this, true);
		setClickable(true);
		setOnClickListener(this);
		arrowImageView = (ImageView) findViewById(R.id.arrow);
		arrowImageView.setImageResource(R.drawable.input_arrow_down);
	}

	@Override
	public void onSelected(String option) {
		selectPopupWindow.dismiss();
		((TextView) findViewById(R.id.value)).setText(option);
	}

	@Override
	public void onClick(View v) {
		if (selectPopupWindow == null) {
			while (!flag) {
				initWedget();
				flag = true;
			}
		}
		if (selectPopupWindow.isShowing()) {
			selectPopupWindow.dismiss();
		} else {
			//selectPopupWindow.showAsDropDown(this, 0, -3);
			selectPopupWindow.showAtLocation(optionListView, Gravity.CENTER, 0, 0);
			arrowImageView.setImageResource(R.drawable.input_arrow_up);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			initWedget();
			flag = true;
		}
	}

	private void initWedget() {
		// 获取下拉框依附的组件宽度
		int width = getWidth();
		pwidth = width;
		// 初始化PopupWindow
		initPopuWindow();
	}

	private void initPopuWindow() {
		View optionwindow = (View) LayoutInflater.from(context).inflate(R.layout.spinner_options, null);
		optionListView = (ListView) optionwindow.findViewById(R.id.list);
		TextView optionsTitleBar = (TextView) optionwindow.findViewById(R.id.optionsTitleBar);
		if (optionsTitle == null || optionsTitle.trim().length() == 0)
			optionsTitle = getResources().getString(R.string.optionsDefaultTitle);
		optionsTitleBar.setText(optionsTitle);
		optionsAdapter = new SpinnerOptionsAdapter(context, list, this);
		optionListView.setAdapter(optionsAdapter);
		
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		int h = wm.getDefaultDisplay().getHeight();  
		optionListView.getLayoutParams().height = h/2;
		//optionListView.setVerticalScrollBarEnabled(false);
		
		LinearLayout optionsLayoutWrap = (LinearLayout) optionwindow.findViewById(R.id.optionsLayoutWrap);
		optionsLayoutWrap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectPopupWindow.dismiss();
			}
		});
		//LinearLayout optionsLayoutWrap = (LinearLayout) optionwindow.findViewById(R.id.optionsLayoutWrap);
		//optionsLayoutWrap.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, h));
		/*optionListView.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				if (list.size() > 0) {
					View optionView = optionsAdapter.getView(0, null, optionListView);
					optionView.measure(0, 0);
					int h = optionView.getMeasuredHeight();
					optionListView.getLayoutParams().height = h * list.size();
				}
				return true;
			}
		});*/

		selectPopupWindow = new PopupWindow(optionwindow, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
		selectPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				arrowImageView.setImageResource(R.drawable.input_arrow_down);
				arrowImageView.setTag("2");
			}
		});
		selectPopupWindow.setOutsideTouchable(true);
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public String getOptionsTitle() {
		return optionsTitle;
	}

	public void setOptionsTitle(String optionsTitle) {
		this.optionsTitle = optionsTitle;
	}
}
