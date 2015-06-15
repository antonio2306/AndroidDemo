package cn.joy.android.demo;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.joy.android.R;
import cn.joy.android.demo.utils.ImageUtil;
import cn.joy.android.demo.video.PlayVideoActivity;
import cn.joy.android.demo.video.RecordVideoActivity;

public class TestVideoActivity extends Activity {
	private static final int RECORD = 101;
	private static final int RECORD_BY_SYSTEM = 102;
	private static final int PLAY = 201;
	private static final int PLAY_BY_SYSTEM = 202;
	
	private String filePath = "";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_video);
	}

	public void record(View v) {
		Intent intent = new Intent(getApplicationContext(), RecordVideoActivity.class);
		startActivityForResult(intent, RECORD);
	}
	
	public void recordBySystem(View v) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		//限制时长
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
		//限制大小
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 3*1024*1024);
		filePath = Environment.getExternalStorageDirectory().getPath()
				+"/"+System.currentTimeMillis() + ".mp4";
		File newfile = new File(filePath);
        /*try {
            newfile.createNewFile();
        } catch (IOException e) {}   */    

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));
		startActivityForResult(intent, RECORD_BY_SYSTEM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		Log.e("VIDEO", "requestCode=" + requestCode + ", resultCode=" + resultCode);
		
		String path = "";
		if (requestCode == RECORD) {
			if (intent != null) {
				path = intent.getStringExtra("path");
			}
		}else if(requestCode == RECORD_BY_SYSTEM){
			path = filePath;
		}
		
		Log.e("VIDEO", "path=" + path);
		if (path.trim().length() > 0) {
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MICRO_KIND);
			String thumbnailPath = path.replace(".mp4", ".jpg");
			ImageUtil.saveBitmapToDiskAsJPG(bitmap, thumbnailPath);

			View videoContainer = findViewById(R.id.videoLayout);
			videoContainer.setVisibility(View.VISIBLE);
			ImageView thumbnail = (ImageView) videoContainer.findViewById(R.id.thumbnail);
			bitmap = ImageUtil.createThumbnail(thumbnailPath, 150);
			thumbnail.setImageBitmap(bitmap);
			final String p = path;
			thumbnail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), PlayVideoActivity.class);
					intent.putExtra("path", p);
					startActivityForResult(intent, PLAY);

					/*
					 * Intent intent = new Intent(Intent.ACTION_VIEW);
					 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 * intent.setDataAndType(Uri.parse(path),
					 * "video/mp4"); startActivity(intent);
					 */
				}
			});
		}
	}
}
