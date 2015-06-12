package cn.joy.android.demo.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import cn.joy.android.R;

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_view);
        

        Intent it = getIntent();
        if (it != null &&  it.getAction() != null && it.getAction().equals(Intent.ACTION_SEND)) {
	        Bundle extras = it.getExtras();
	        if (extras.containsKey("android.intent.extra.STREAM")) {
	        	Uri uri = (Uri) extras.get("android.intent.extra.STREAM");
	        	ImageView imageView = (ImageView)this.findViewById(R.id.shareImage);
	        	imageView.setImageURI(uri);
	        }else if(extras.containsKey("share_screenshot")){
	        	Bitmap bm = (Bitmap) extras.get("share_screenshot");
	        	ImageView imageView = (ImageView)this.findViewById(R.id.shareImage);
	        	imageView.setImageBitmap(bm);
	        	
	        	String url = (String) extras.get("android.intent.extra.TEXT");
	        	EditText editText = (EditText)this.findViewById(R.id.shareText);
	        	editText.setText(url);
	        }
        }

    }
}
