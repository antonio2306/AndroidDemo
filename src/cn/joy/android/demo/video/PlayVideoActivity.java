package cn.joy.android.demo.video;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import cn.joy.android.R;

public class PlayVideoActivity extends Activity implements OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {
	private Display currDisplay;
	private SurfaceView videoView;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer mPlayer;
	private int vWidth, vHeight;

	private boolean isPlay = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// 选择支持半透明模式,在有surfaceview的activity中使用。
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.play_video);

		videoView = (SurfaceView) this.findViewById(R.id.videoView);
		surfaceHolder = videoView.getHolder();// 取得holder
		surfaceHolder.addCallback(this); // holder加入回调接口
		// setType必须设置，要不出错.
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		if (path != null && path.trim().length() > 0) {
			if (mPlayer == null)
				mPlayer = new MediaPlayer();

			// 如果是播放状态，就从新开始播放
			if (mPlayer.isPlaying()) {
				mPlayer.reset();
			}

			/*
			 * Uri uri = Uri.parse(path); mPlayer =
			 * MediaPlayer.create(PlayVideoActivity.this, uri);// 读取视频
			 * mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			 * mPlayer.setDisplay(surfaceHolder);// 设置屏幕 mPlayer.start();
			 */
			mPlayer.setOnCompletionListener(this);
			mPlayer.setOnErrorListener(this);
			mPlayer.setOnInfoListener(this);
			mPlayer.setOnPreparedListener(this);
			mPlayer.setOnSeekCompleteListener(this);
			mPlayer.setOnVideoSizeChangedListener(this);

			try {
				mPlayer.setDataSource(path);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 然后，我们取得当前Display对象
		currDisplay = this.getWindowManager().getDefaultDisplay();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
		surfaceHolder = holder;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 当SurfaceView中的Surface被创建的时候被调用
		// 在这里我们指定MediaPlayer在当前的Surface中进行播放
		mPlayer.setDisplay(holder);
		// 在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
		mPlayer.prepareAsync();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// surfaceDestroyed的时候同时对象设置为null
		videoView = null;
		surfaceHolder = null;
		if (mPlayer != null) {
			try {
				mPlayer.stop();
			} catch (IllegalStateException e) {
			}
			mPlayer.release();
			mPlayer = null;
		}

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mPlayer, int arg1, int arg2) {
		// 当video大小改变时触发
		// 这个方法在设置player的source后至少触发一次

	}

	@Override
	public void onSeekComplete(MediaPlayer mPlayer) {
		// seek操作完成时触发

	}

	@Override
	public void onPrepared(MediaPlayer mPlayer) {
		// 当prepare完成后，该方法触发，在这里我们播放视频
		// 首先取得video的宽和高
		vWidth = mPlayer.getVideoWidth();
		vHeight = mPlayer.getVideoHeight();

		if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
			// 如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
			float wRatio = (float) vWidth / (float) currDisplay.getWidth();
			float hRatio = (float) vHeight / (float) currDisplay.getHeight();

			// 选择大的一个进行缩放
			float ratio = Math.max(wRatio, hRatio);

			vWidth = (int) Math.ceil((float) vWidth / ratio);
			vHeight = (int) Math.ceil((float) vHeight / ratio);

			// 设置surfaceView的布局参数
			// videoView.setLayoutParams(new LinearLayout.LayoutParams(vWidth,
			// vHeight));
		}
		mPlayer.start();

	}

	@Override
	public boolean onInfo(MediaPlayer arg0, int whatInfo, int extra) {
		// 当一些特定信息出现或者警告时触发
		switch (whatInfo) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
			break;
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer mPlayer, int whatError, int extra) {
		switch (whatError) {
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// 当MediaPlayer播放完成后触发
		Log.v("Play Over:::", "onComletion called");
		this.finish();

	}
}
