package com.android021box.htstartup.photo;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.android021box.htstartup.adapter.PhotoViewPagerAdapter;
import com.android021box.htstartup.info.PhotoInfo;

public class ViewPagerPhotoActivity extends Activity {

	private ViewPager mViewPager;
	private int position;
	private ArrayList<PhotoInfo> imglist;
	public static ViewPagerPhotoActivity minstance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		minstance = this;
		mViewPager = new HackyViewPager(this);
		setContentView(mViewPager);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		position = data.getInt("position");
		imglist = (ArrayList<PhotoInfo>)intent.getSerializableExtra("imglist");
		mViewPager.setAdapter(new PhotoViewPagerAdapter(imglist, this));
		mViewPager.setCurrentItem(position);
	}
}
