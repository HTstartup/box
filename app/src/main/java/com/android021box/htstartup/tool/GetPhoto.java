package com.android021box.htstartup.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class GetPhoto {
	private final int imageQuality = 30;

	private static final String SD_PATH="/mnt/sdcard/htstartup/";
	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public Drawable getRoundedCornerDrawable(Drawable drawable, float roundPx) {
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		return new BitmapDrawable(getRoundedCornerBitmap(bitmap, roundPx));
	}

	public void getSmallPhoto(String dirpath, String photopath, String photoname) {
		Bitmap bm = null;
		Bitmap bmp = getBitmapByPath(photopath);
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 1;
		int Max = 0;
		if (bmpWidth >= bmpHeight) {
			Max = bmpWidth;
		} else {
			Max = bmpHeight;
		}
		if (Max > 800) {
			scale = Max / 800;
		}
		Options op = new Options();
		op.inSampleSize = (int) scale;
		op.inJustDecodeBounds = false;
		op.inPreferredConfig = Config.ARGB_8888;
		bm = BitmapFactory.decodeFile(photopath, op);
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File bitmapFile = new File(dirpath + photoname);
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
			String prefix = photoname.substring(photoname.lastIndexOf(".") + 1);
			if (prefix.equals("jpg") || prefix.equals("jpeg")
					|| prefix.equals("JPG") || prefix.equals("JPEG")) {
				bm.compress(Bitmap.CompressFormat.JPEG, imageQuality, fos);
			} else if (prefix.equals("png")) {
				bm.compress(Bitmap.CompressFormat.PNG, imageQuality, fos);
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

	}

	public String savePhoto(Bitmap bitmap, String name) {
		File dir = new File(SD_PATH+"etaopai_photo/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File bitmapFile = new File(SD_PATH+"etaopai_photo/" + name);
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
			String prefix = name.substring(name.lastIndexOf(".") + 1);
			if (prefix.equals("jpg") || prefix.equals("jpeg")) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, fos);
			} else if (prefix.equals("png")) {
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
		return SD_PATH+"etaopai_photo/" + name;
	}

	public Bitmap getBitmapByPath(String path) {
		if (path == null)
			return null;
		Bitmap temp = null;
		if (temp == null) {
			try {
				temp = BitmapFactory.decodeFile(path);
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Error e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	public String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date);
	}

	public String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(0, i);
			}
		}
		return filename;
	}

	public String addMyExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			String name = trimExtension(filename);
			String extension = filename
					.substring(filename.lastIndexOf(".") + 1);
			filename = name + ".m" + extension;
		}
		return filename;
	}
	private String getOldName(String filename){
		if ((filename != null) && (filename.length() > 0)) {
			String name = trimExtension(filename);			
			String oldextension=filename
					.substring(filename.lastIndexOf(".") + 2);
			filename = name + "." + oldextension;
		}
		return filename;
	}
	public void savePhotoToSd(String path) {
		String myPath = addMyExtension(path);
		String name = myPath.substring(myPath.lastIndexOf("/") + 1);
		String oldname=path.substring(path.lastIndexOf("/") + 1);
		Bitmap bitmap=null;
		try {
			bitmap = getBitmapByPath(SD_PATH+"photo/" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(bitmap!=null){
			savePhoto(bitmap,oldname);
		}

	}
}
