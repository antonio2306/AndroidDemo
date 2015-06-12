package cn.joy.android.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 主要解决
 * 
 * 1、系统自动把当作文本输出的数字和电话号码混淆
 * 
 * 2、对超文本链接可以自己控制是用自己的webview显示还是使用系统默认浏览器打开
 * 
 * 存在问题
 * 
 * 当TextView文本过多时，就不能将TextView设置为自动滚动的了，这是一个很大的问题。即下面代码有冲突
 * 
 * TextView.setMovementMethod(LinkMovementMethod.getInstance());
 * 
 * TextView.setMovementMethod(ScrollingMovementMethod.getInstance());
 * 如何解决两个事件冲突问题，目前还没研究，如果有高人已经解决，希望告诉我一下。
 * 
 * 
 * 
 * <a href="http://my.oschina.net/arthor" class="referer"
 * target="_blank">@author</a> lxlin
 * 
 * 
 */

public class TestTextViewSpanActivity extends Activity {

	/** Called when the activity is first created. */

	private TextView tv;

	private static Context ctx;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		tv = new TextView(this);
		tv.setTextIsSelectable(true);
		//tv.setEnabled(false);  //会变灰
		//tv.setKeyListener(null);  //不能复制

		ctx = this;

		String htmlLinkText = "我是普通数字" + "12365478910" + "我是" + "11233658510" + "你好10元"

		+ "我是超链接：<a href='http://my.oschina.net/zhibuji/blog'>超链接点击事件</a>"

		+ "我是电话号码： <a href='tel:4155551212'>打电话</a>"

		+ " 我是邮件 ：<a href='mailto:lxlinv@gmail.com'> 发邮件给我 </a>";

		// 文字的样式（style）被覆盖，不能改变……
		
		tv.setText(Html.fromHtml(htmlLinkText));
		
		//tv.setText("打电话123456789  网站http://www.baidu.com");
		Linkify.addLinks(tv, Linkify.ALL);
		//tv.setAutoLinkMask(Linkify.ALL);
		

		//tv.setMovementMethod(LinkMovementMethod.getInstance());

		/**
		 * 
		 * 上面两行代码约等于下面这一行代码。
		 * 
		 * Linkify.addLinks(tv, Linkify.ALL); //这句代码最好是在设置文本之前加入
		 * 
		 * 区别在于：
		 * 
		 * 使用Linkify不能对html文本进行处理，连接只能显示为连接比如http://my.oschina.net/zhibuji/
		 * blog才可以点击
		 * 
		 * 对号码的处理不够强大，当然你也可以自己写正则表达式加强,Linkify提供了很多类似下面的方法。
		 * 
		 * Linkify.addLinks(Spannble, Pattern, scheme,matchFilter,
		 * transformFilter)具体用法请查看文档
		 */

		// tv.setMovementMethod(ScrollingMovementMethod.getInstance());
/*
		CharSequence text = tv.getText();

		if (text instanceof Spannable) {

			int end = text.length();

			Spannable sp = (Spannable) tv.getText();

			URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);

			SpannableStringBuilder style = new SpannableStringBuilder(text);

			style.clearSpans();// should clear old spans

			// 循环把链接发过去

			for (URLSpan url : urls) {

				System.out.println("TestTextViewSpan Url" + url);// 这里输出的是类似
																	// android.text.style.UTLSpan@44f15a80

				System.out.println("TestTextViewSpan getURl" + url.getURL());// 这里输出的就是封装好的文本了：tel:4155551212

				MyURLSpan myURLSpan = new MyURLSpan(TestTextViewSpanActivity.this, url.getURL());

				style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			}

			tv.setText(style);

		}
*/
		setContentView(tv);

	}

	private static class MyURLSpan extends ClickableSpan {

		private String mUrl;

		private Context context;

		MyURLSpan(Context context, String string) {

			mUrl = string;

			this.context = context;

		}

		/**
		 * 
		 * 对各种链接事件进行自己的处理
		 */

		@Override
		public void onClick(View widget) {

			if (mUrl.startsWith("tel:")) {

				Toast.makeText(ctx, mUrl + "", Toast.LENGTH_LONG).show();

				// 这里自己处理，我做的是拨号动作

				Intent dialIntent = new Intent();

				dialIntent.setAction(Intent.ACTION_DIAL);

				dialIntent.setData(Uri.parse(mUrl));

				context.startActivity(dialIntent);

			}

			if (mUrl.startsWith("http://")) {

				Toast.makeText(ctx, mUrl, Toast.LENGTH_LONG).show();

				widget.setBackgroundColor(Color.parseColor("#00000000"));

			}

			if (mUrl.startsWith("mailto:")) {

				System.out.println("打开发邮件的地址");

			}

		}

	}

}
