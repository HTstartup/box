package com.android021box.htstartup.async;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.android021box.htstartup.tool.GetPhoto;

public class AsyncPhotoLoader {
	private final int  imageQuality=30;
	private HashMap<String, SoftReference<Drawable>> imageCache;
	private static final int MAX_THREAD_NUM = 10;
	private ExecutorService threadPools = null;
	private static final String SD_PATH="/mnt/sdcard/htstartup/";
	public AsyncPhotoLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final PhotoCallback photoCallback) {
		final String myPath=new GetPhoto().addMyExtension(imageUrl);
		if (imageCache.containsKey( myPath)) {
			SoftReference<Drawable> softReference = imageCache.get( myPath);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		} else {
			String bitmapName =  myPath
					.substring( myPath.lastIndexOf("/") + 1);
			File cacheDir = new File(SD_PATH+"thumb/");
			File[] cacheFiles = cacheDir.listFiles();
			int i = 0;
			if (null != cacheFiles) {
				for (; i < cacheFiles.length; i++) {
					if (bitmapName.equals(cacheFiles[i].getName())) {
						break;
					}
				}

				if (i < cacheFiles.length) {
					Bitmap bitmap = BitmapFactory
							.decodeFile(SD_PATH+"thumb/" + bitmapName);
					return new BitmapDrawable(bitmap);
				}
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				photoCallback.imageLoaded((Drawable) message.obj, imageView);
			}
		};
		Thread thread = new Thread() {
			@Override
			public void run() {
				Drawable drawable = null;

				try {
					drawable = loadImageFromUrl(imageUrl);
				} catch (Exception e) {
					Log.e("AsyncPhoto", "error to download");
				}
				if (drawable != null) {
					sendMessage(drawable);
				}
			}

			private void sendMessage(Drawable drawable) {
				imageCache.put(myPath, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		};
		threadPools.execute(thread);
		return null;

	}

	protected Drawable loadImageFromUrl(String url) {
		Drawable d = null;
		try {
			d = Drawable.createFromStream(new URL(url).openStream(), "src");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File dir = new File(SD_PATH+"thumb/");
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

		if (!dir.exists()) {
			dir.mkdirs();
		}
		String myPath=new GetPhoto().addMyExtension(url);
		File bitmapFile = new File(SD_PATH+"thumb/"
				+ myPath.substring(myPath.lastIndexOf("/") + 1));
		if (!bitmapFile.exists()) {
			try {
				bitmapFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(bitmapFile);
			String prefix = myPath.substring(myPath.lastIndexOf(".") + 1);
			if (prefix.equals("mjpg") || prefix.equals("mjpeg")
					|| prefix.equals("mJPG") || prefix.equals("mJPEG")) {
				bitmap.compress(CompressFormat.JPEG, imageQuality, fos);
			} else if (prefix.equals("mpng")) {
				bitmap.compress(CompressFormat.PNG, imageQuality, fos);
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return d;
	}
	public interface PhotoCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView);
	}
}
