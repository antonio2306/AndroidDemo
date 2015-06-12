package cn.joy.android.demo;

import java.io.File;

import cn.joy.android.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TestVideoActivity extends Activity implements SurfaceHolder.Callback {
	private TextView tvTime;
	private TextView tvSize;
	private Button btnStart;
	private Button btnStop;
	private Button btnCancel;
	
	private MediaRecorder mediaRecorder;// 录制视频的类
	private SurfaceView videoView;// 显示视频的控件
	private SurfaceHolder surfaceHolder;
	
	private File videoFile;
	private Handler handler;
	private boolean recording = false; // 记录是否正在录像,fasle为未录像, true 为正在录像
	private int minute = 0;
	private int second = 0;
	private String time="";
	private String size="";
	private String fileName;
	private String name="";
	private final int maxSecond = 6;	//6s
	private final int maxSize = 5;	//5M

	/**
	 * 录制过程中,时间变化,大小变化
	 */
	private Runnable timeRun = new Runnable() {

		@Override
		public void run() {
			long fileLength=videoFile.length();
			if(fileLength<1024 && fileLength>0){
				size=String.format("%dB/%dM", fileLength, maxSize);
			}else if(fileLength>=1024 && fileLength<(1024*1024)){
				fileLength=fileLength/1024;
				size=String.format("%dK/%dM", fileLength, maxSize);
			}else if(fileLength>(1024*1024*1024)){
				fileLength=(fileLength/1024)/1024;
				size=String.format("%dM/%dM", fileLength, maxSize);
			}
			second++;
			if (second == 60) {
				minute++;
				second = 0;
			}
			time = String.format("%02d:%02d", minute, second);
			tvSize.setText(size);
			tvTime.setText(time);
			if(second>=maxSecond)
				stopRecord();
			else
				handler.postDelayed(timeRun, 1000);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

		// 设置横屏显示
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// 选择支持半透明模式,在有surfaceview的activity中使用。
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		setContentView(R.layout.record_video);

		handler = new Handler();
		tvTime = (TextView) findViewById(R.id.tv_video_time);
		tvSize=(TextView)findViewById(R.id.tv_video_size);
		btnStop = (Button) findViewById(R.id.btn_video_stop);
		btnStart = (Button) findViewById(R.id.btn_video_start);
		btnCancel = (Button) findViewById(R.id.btn_video_cancel);
		
		TestVideoListener listener = new TestVideoListener();
		btnCancel.setOnClickListener(listener);
		btnStart.setOnClickListener(listener);
		btnStop.setOnClickListener(listener);

		videoView = (SurfaceView) this.findViewById(R.id.videoView);
		SurfaceHolder holder = videoView.getHolder();// 取得holder
		holder.addCallback(this); // holder加入回调接口
		// setType必须设置，要不出错.
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	class TestVideoListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.btn_video_start:
					startRecord();
					break;
				case R.id.btn_video_stop:
					stopRecord();
					break;
				case R.id.btn_video_cancel:
					finish();
					break;
				default:
					break;
			}
		}
	}
	
	private void startRecord(){
		if (recording) 
			return;
		
		if (mediaRecorder != null) {
			mediaRecorder.reset();
		} else
			mediaRecorder = new MediaRecorder();// 创建mediarecorder对象

		// 设置录制视频源为Camera(相机)
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		//mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		// 设置录制的视频编码h263 h264
		mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		//mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

		// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
		//mediaRecorder.setVideoSize(480, 360);
		// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
		//mediaRecorder.setVideoFrameRate(30);
		mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
		
		//设置视频输出的格式和编码
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        //mMediaRecorder.setProfile(mProfile);
        Log.e("DEMO", "exs:"+mProfile.videoFrameWidth+"-"+mProfile.videoFrameHeight);	
        mediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        mediaRecorder.setAudioEncodingBitRate(44100);
        Log.e("DEMO", "exs:"+mProfile.videoBitRate);
        if (mProfile.videoBitRate > 2 * 1024 * 1024)
        	mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        else
        	mediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);
        Log.e("DEMO", "exs:"+mProfile.videoFrameRate);
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);

		// 设置视频文件输出的路径
		//Log.e("DEMO", "exs:"+Environment.getExternalStorageDirectory().getPath());
		videoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".mp4");
		//Log.e("DEMO", "exs2:"+videoFile.getAbsolutePath());
		mediaRecorder.setOutputFile(videoFile.getAbsolutePath());
		
		mediaRecorder.setMaxDuration(maxSecond*1000);	
		mediaRecorder.setMaxFileSize(maxSize*1024*1024);	

		try {
			//mediaRecorder.setOnInfoListener(null);
			//mediaRecorder.setOnErrorListener(null);
			// 准备录制
			mediaRecorder.prepare();
			// 开始录制
			mediaRecorder.start();
			
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
			minute = 0;
			second = 0;
			recording = true;
			handler.post(timeRun); // 调用Runable
		} catch (Exception e) {
			Log.e("", "start error", e);
		}
	}
	
	private void stopRecord(){
		release();
		
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
	}
	
	private void release(){
		if (mediaRecorder != null) {
			try {
				// 停止录制
				mediaRecorder.stop();
			} catch (IllegalStateException e) {
			}
			mediaRecorder.release();
			mediaRecorder = null;
		}
		
		minute = 0;
		second = 0;
		handler.removeCallbacks(timeRun);
		recording = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
		surfaceHolder = holder;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
		surfaceHolder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// surfaceDestroyed的时候同时对象设置为null
		videoView = null;
		surfaceHolder = null;
		release();
	}
}
