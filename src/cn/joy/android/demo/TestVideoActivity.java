package cn.joy.android.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.joy.android.R;
import cn.joy.android.demo.utils.ImageUtil;
import cn.joy.android.demo.utils.ToastUtil;
import cn.joy.android.demo.video.PlayVideoActivity;
import cn.joy.android.demo.video.RecordVideoActivity;

public class TestVideoActivity extends Activity {
	private static final int RECORD = 101;
	private static final int PALY = 102;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_video);
	}

	public void record(View v) {
		Intent intent = new Intent(getApplicationContext(), RecordVideoActivity.class);
		startActivityForResult(intent, RECORD);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		Log.e("VIDEO", "requestCode=" + requestCode + ", resultCode=" + resultCode);
		if (requestCode == RECORD) {
			if (intent != null) {
				final String path = intent.getStringExtra("path");
				Log.e("VIDEO", "path=" + path);
				if (path != null && path.trim().length() > 0) {
					Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, Thumbnails.MICRO_KIND);
					String thumbnailPath = path.replace(".mp4", ".jpg");
					ImageUtil.saveBitmapToDiskAsJPG(bitmap, thumbnailPath);

					View videoContainer = findViewById(R.id.videoLayout);
					videoContainer.setVisibility(View.VISIBLE);
					ImageView thumbnail = (ImageView) videoContainer.findViewById(R.id.thumbnail);
					bitmap = ImageUtil.createThumbnail(thumbnailPath, 150);
					thumbnail.setImageBitmap(bitmap);
					thumbnail.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							ToastUtil.show(getApplicationContext(), path);

							Intent intent = new Intent(getApplicationContext(), PlayVideoActivity.class);
							intent.putExtra("path", path);
							startActivityForResult(intent, PALY);

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
	}
}
