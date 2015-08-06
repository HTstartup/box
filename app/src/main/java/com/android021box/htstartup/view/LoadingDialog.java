package com.android021box.htstartup.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android021box.htstartup.R;

public class LoadingDialog extends AlertDialog {
	private ImageView mImageView;
	private Animation mAnimation;
	private Activity mContext;
	public LoadingDialog(Activity context, int theme,int drawable) {
		super(context, theme);
		mContext = context;
		mImageView = new ImageView(mContext);
		mImageView.setImageResource(drawable);
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(mImageView);
		mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
		mImageView.setAnimation(mAnimation);
		mAnimation.startNow();
	}

}

