package com.android021box.htstartup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
public class SplashActivity extends Activity {
	File sdCardDir;
	private static final int GO_MAIN = 1002;
	private static final long SPLASH_DELAY_MILLIS = 2000;
	private File path1, path2, path3, path4;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_MAIN:
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				SplashActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.activity_zoom_in,
						R.anim.activity_zoom_out);
				SplashActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sdCardDir = Environment.getExternalStorageDirectory();
		setFiles();
		init();
	}

	private void setFiles() {
		File sdcardDir = Environment.getExternalStorageDirectory();
		String mainpath = sdcardDir.getPath() + "/htstartup/";
		path1 = new File(mainpath + "photo/");
		if (!path1.exists()) {
			path1.mkdirs();
		}
		path2 = new File(mainpath + "thumb/");
		if (!path2.exists()) {
			path2.mkdirs();
		}
		path3 = new File(mainpath + "img/");
		if (!path3.exists()) {
			path3.mkdirs();
		}
		path4 = new File(mainpath + "db/");
		if (!path4.exists()) {
			path4.mkdirs();
		}
	}
	private void init() {
		mHandler.sendEmptyMessageDelayed(GO_MAIN, 2000);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
