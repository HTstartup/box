package com.android021box.htstartup.async;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android021box.htstartup.tool.GetPhoto;

public class AsyncImageLoader {
	private final int  imageQuality=30;
	private HashMap<String, SoftReference<Drawable>> imageCache;
	private static final int MAX_THREAD_NUM = 10;
	private ExecutorService threadPools = null;
	private static final String SD_PATH="/mnt/sdcard/htstartup/";
	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
	}
	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ProgressBar progressbar,
			final ImageCallback imageCallback) {
		final String myPath=new GetPhoto().addMyExtension(imageUrl);
		if (imageCache.containsKey(myPath)) {
			// Èí»º´æÖÐÓÐ¾ÍÖ±½Ó·µ»Ø
			SoftReference<Drawable> softReference = imageCache.get(myPath);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		} else {
			String bitmapName = myPath
					.substring(myPath.lastIndexOf("/") + 1);
			File cacheDir = new File(SD_PATH);
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
							.decodeFile(SD_PATH+"photo/"
									+ bitmapName);
					return new BitmapDrawable(bitmap);
				}
			}

		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageView,
						progressbar);
			}
		};
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Drawable drawable = null;
					try {
						drawable = loadImageFromUrl(imageUrl);
					} catch (Exception e) {
						Log.e("AsyncImage", "error to download");
					}
					if (drawable != null) {
						sendMessage(drawable);
					}
				} catch (Exception e) {
					e.printStackTrace();
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
		File dir = new File(SD_PATH+"photo/");
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

		if (!dir.exists()) {
			dir.mkdirs();
		}
		String myPath=new GetPhoto().addMyExtension(url);
		File bitmapFile = new File(SD_PATH+"photo/"
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
			if (prefix.equals("mjpg") || prefix.equals("mjpeg")|| prefix.equals("mJPG")|| prefix.equals("mJPEG")) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, fos);//ï¿½ï¿½ï¿½ï¿½Îª100ï¿½ï¿½Ê¾ï¿½ï¿½Ñ¹ï¿½ï¿½ï¿½ï¿½60ï¿½ï¿½ï¿½ï¿½Ñ¹ï¿½ï¿½Ò²ï¿½Ü±ï¿½Ö¤Æ·ï¿½ï¿½
			} else if (prefix.equals("mpng")) {
				bitmap.compress(Bitmap.CompressFormat.PNG, imageQuality, fos);
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

	// ï¿½Øµï¿½ï¿½Ó¿ï¿½
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
								ProgressBar progressbar);
	}
}
