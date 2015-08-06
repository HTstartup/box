package com.android021box.htstartup.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapUtilities {

	public BitmapUtilities() {
		// TODO Auto-generated constructor stub
	}

	public static Bitmap getBitmapThumbnail(String path, int size) {
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = size;// ��͸߶���ԭ����1/4 
		bitmap =BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	public static Bitmap getBitmapThumbnail(Bitmap bmp, float scale) {
		Bitmap bitmap = null;
		if (bmp != null) {
			int bmpWidth = bmp.getWidth();
			int bmpHeight = bmp.getHeight();
			if (bmp != null) {
				if (scale != 0) {
					Matrix matrix = new Matrix();
					float scaleWidth = ((float) scale);
					float scaleHeight = ((float) scale);
					matrix.postScale(scaleWidth, scaleHeight);
					bitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,
							bmpHeight, matrix, true);
				} else {
					bitmap = bmp;
				}
			}
		}
		return bitmap;
	}
}
