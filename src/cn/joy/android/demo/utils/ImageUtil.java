package cn.joy.android.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageUtil {
	public static Bitmap createThumbnail(String imgPath, int height) {
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = new FileInputStream(imgPath);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int be = (int) (options.outHeight / (float) height);
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			is.close();
			is = new FileInputStream(imgPath);
			bitmap = BitmapFactory.decodeStream(is, null, options);
			is.close();
		} catch (FileNotFoundException e) {
			Log.e("ImageUtil", "文件：" + imgPath + "  找不到");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("ImageUtil", e.getMessage());
			e.printStackTrace();
		}
		return bitmap;
	}

	public static String saveBitmapToDiskAsJPG(Bitmap bitmap, String path) {
		FileOutputStream out = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			out = new FileOutputStream(path);
			int quality = 100;
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			while (baos.toByteArray().length / 1024 > 1024) {
				baos.reset();
				quality -= 10;
				bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			}
			out.write(baos.toByteArray());
			bitmap.recycle();
			System.gc();
			return path;
		} catch (Exception e) {
			Log.e("ImageUtil", e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				Log.e("ImageUtil", e.getMessage());
			}
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				Log.e("ImageUtil", e.getMessage());
			}
		}
		return null;
	}
}
