package com.android021box.htstartup.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class ShowLoadingDialog {
	private LoadingDialog mDialog;
	private Activity mContext;
	private boolean back;
	private int theme;
	private int drawable;
	private boolean cancle;

	public ShowLoadingDialog(Activity mContext, int theme, boolean back,
			boolean cancle, int drawable) {
		this.mContext = mContext;
		this.theme = theme;
		this.back = back;
		this.drawable = drawable;
		this.cancle = cancle;
	}

	public void show() {
		mDialog = new LoadingDialog(mContext, theme, drawable);
		mDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (back) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						dialog.dismiss();
						mContext.finish();
						return true;
					} else {
						return false;
					}
				}
				return false;
			}
		});
		mDialog.setCancelable(cancle);
		mDialog.show();
	}

	public void dismiss() {
		mDialog.dismiss();
	}
}
