package com.android021box.htstartup.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android021box.htstartup.R;
import com.android021box.htstartup.async.AsyncImageLoader;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.info.PhotoInfo;
import com.android021box.htstartup.photo.PhotoView;
import com.android021box.htstartup.tool.GetPhoto;
import com.android021box.htstartup.view.DialogStyle;
import com.android021box.htstartup.view.MyAlertDialog;
import com.android021box.htstartup.view.ShowLoadingDialog;

import java.util.ArrayList;

/**
 */
public class PhotoViewPagerAdapter extends PagerAdapter {
	private ArrayList<PhotoInfo> imglist;
	private Activity activity;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private ShowLoadingDialog mDialog;
	public PhotoViewPagerAdapter(ArrayList<PhotoInfo> imglist, Activity activity) {
		this.imglist = imglist;
		this.activity = activity;
		mDialog = new ShowLoadingDialog(activity, R.style.mydialog, false,false,
				R.drawable.spinner);
	}

	// ����arg1λ�õĽ���
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// ��õ�ǰ������
	@Override
	public int getCount() {
		if (imglist != null) {
			return imglist.size();
		}
		return 0;
	}

	// ��ʼ��arg1λ�õĽ���
	@Override
	public View instantiateItem(View view, final int position) {
		final String path = imglist.get(position).getImgPath();
		PhotoHolder img = new PhotoHolder();		
		FrameLayout mview = (FrameLayout) LayoutInflater.from(activity)
				.inflate(R.layout.viewpager_photo, null);
		img.photoView = (PhotoView) mview.findViewById(R.id.image);
		img.photoView.setVisibility(View.GONE);
		img.progressbar = (ProgressBar) mview.findViewById(R.id.progressbar);
		img.progressbar.setVisibility(View.VISIBLE);
		img.btn_down= (ImageButton) mview.findViewById(R.id.btn_down);
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable drawable = asyncImageLoader.loadDrawable(
				new Base().getBaseUrl() + path, img.photoView, img.progressbar,
				new AsyncImageLoader.ImageCallback() {
					@Override
					public void imageLoaded(Drawable imageDrawable,
							ImageView imageView, ProgressBar progressbar) {
						// TODO Auto-generated method stub
						progressbar.setVisibility(View.GONE);
						imageView.setVisibility(View.VISIBLE);
						imageView.setImageDrawable(imageDrawable);
					}
				});
		if (drawable != null) {
			img.progressbar.setVisibility(View.GONE);
			img.photoView.setVisibility(View.VISIBLE);
			img.photoView.setImageDrawable(drawable);
			img.btn_down.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.show();
					Thread thread = new Thread(new SaveThread(
							path));
					thread.start();
				}
			});
			img.photoView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					final Builder builder = new MyAlertDialog.Builder(activity);
					builder.setTitle("保存到手机");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									
								}

							});
					builder.setNegativeButton("取消", null);
					Dialog dialog = new DialogStyle(builder, Gravity.CENTER,
							R.style.dialogjumpstyle).setAnim();
					dialog.show();
					return false;
				}
			});
		}
		((ViewPager) view).addView(mview, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		return mview;
	}

	// �ж��Ƿ��ɶ������ɽ���
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	Handler PhotoHandler = new Handler() {
		public void handleMessage(Message msg) {
			mDialog.dismiss();
			Toast.makeText(activity, "b保存到/mnt/sdcard/htstartup/ht_photo/",Toast.LENGTH_SHORT)
					.show();
		}
	};

	class SaveThread implements Runnable {
		private String path;

		public SaveThread(String path) {
			this.path = path;
		}

		@Override
		public void run() {
			GetPhoto getphoto = new GetPhoto();
			getphoto.savePhotoToSd(path);
			Message msg = PhotoHandler.obtainMessage();
			PhotoHandler.sendMessage(msg);
		}
	}

}

class PhotoHolder {
	public PhotoView photoView;
	public ProgressBar progressbar;
	public ImageButton btn_down;
}
