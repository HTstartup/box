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
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android021box.htstartup.tool.GetPhoto;

public class AsyncImageLoader {
	private final int  imageQuality=30;
	private static LruCache<String, Bitmap> mMemoryCache=null;
	private static final int MAX_THREAD_NUM = 10;
	private ExecutorService threadPools = null;
	private static final String SD_PATH="/mnt/sdcard/htstartup/";
	public AsyncImageLoader() {
		if(mMemoryCache==null){
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			int mCacheSize = maxMemory / 8;
			//给LruCache分配1/8 4M
			mMemoryCache = new LruCache<String, Bitmap>(mCacheSize){
				//必须重写此方法，来测量Bitmap的大小
				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes() * value.getHeight();
				}

			};
		}
		threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
	}
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			mMemoryCache.put(key, bitmap);
		}
	}
	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ProgressBar progressbar,
			final ImageCallback imageCallback) {
		final String myPath=new GetPhoto().addMyExtension(imageUrl);
		if (getBitmapFromMemCache(myPath) != null) {
			// 缓存中有就直接返回
			return new BitmapDrawable(getBitmapFromMemCache(myPath));
		} else {
			String bitmapName = myPath
					.substring(myPath.lastIndexOf("/") + 1);
			File cacheDir = new File(SD_PATH+"photo/");
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
					addBitmapToMemoryCache(myPath, bitmap);
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
				addBitmapToMemoryCache(myPath, ((BitmapDrawable) drawable).getBitmap());
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
				bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, fos);
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

	// 锟截碉拷锟接匡拷
	public interface ImageCallback {
		void imageLoaded(Drawable imageDrawable, ImageView imageView,
						 ProgressBar progressbar);
	}
}
