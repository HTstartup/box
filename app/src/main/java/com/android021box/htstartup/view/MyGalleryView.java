package com.android021box.htstartup.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.android021box.htstartup.R;
import com.android021box.htstartup.async.GetAsyncPicture;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.info.PhotoInfo;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * ViewPagerʵ�ֵ��ֲ�ͼ����Զ�����ͼ���義����ҳ�Ĺ���ֲ�ͼЧ���� ��֧���Զ��ֲ�ҳ��Ҳ֧�����ƻ����л�ҳ��
 *
 * @author caizhiming
 *
 */

public class MyGalleryView extends FrameLayout {

	// �Զ��ֲ����ÿ���
	private final static boolean isAutoPlay = true;
	// ���ֲ�ͼƬ��ImageView ��list
	private List<PhotoInfo> imagePathList;
	// ��Բ���View��list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// ��ǰ�ֲ�ҳ
	private int currentItem = 0;
	// ��ʱ����
	private ScheduledExecutorService scheduledExecutorService;
	private String BaseUrl = new Base().getBaseUrl();
	private FixedSpeedScroller mScroller;
	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public MyGalleryView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyGalleryView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public MyGalleryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��ʼ�ֲ�ͼ�л�
	 */
	public void setView(List<PhotoInfo> alist) {
		imagePathList = alist;
		dotViewsList = new ArrayList<View>();
		initUI(getContext());
		if (isAutoPlay) {
			startPlay();
		}
	}

	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * ֹͣ�ֲ�ͼ�л�
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * ��ʼ��Views��UI
	 */
	private void initUI(Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_photo_gallery, this, true);
		dotViewsList.add(findViewById(R.id.v_dot1));
		dotViewsList.add(findViewById(R.id.v_dot2));
		dotViewsList.add(findViewById(R.id.v_dot3));
		dotViewsList.add(findViewById(R.id.v_dot4));
		dotViewsList.add(findViewById(R.id.v_dot5));
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());try {
			Field mField = ViewPager.class.getDeclaredField("mScroller");
			mField.setAccessible(true);
			mScroller = new FixedSpeedScroller(viewPager.getContext(),new AccelerateInterpolator());
			mField.set(viewPager, mScroller);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���ViewPager��ҳ��������
	 *
	 * @author caizhiming
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//container.removeView((View) object);
		}

		@Override
		public View instantiateItem(View view, final int position) {
			final PhotoInfo p=imagePathList.get(position);
			final String path = BaseUrl+p.getImgPath();
			PhotoHolder img = new PhotoHolder();
			LinearLayout mview = (LinearLayout) LayoutInflater.from(
					getContext()).inflate(R.layout.view_gallery_adapter, null);
			img.imgView = (ImageView) mview.findViewById(R.id.image);
			img.imgView.setTag(path);// ��ֹͼƬ����ʱ���ִ�λ
			GetAsyncPicture getPic = new GetAsyncPicture(getContext());
			getPic.getRectImage(path, img.imgView);
			((ViewPager) view).addView(mview, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			img.imgView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			return mview;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imagePathList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager�ļ����� ��ViewPager��ҳ���״̬�����ı�ʱ����
	 *
	 * @author caizhiming
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
				case 1:// ���ƻ�����������
					isAutoPlay = false;
					break;
				case 2:// �����л���
					isAutoPlay = true;
					break;
				case 0:// �������������л���ϻ��߼������
					// ��ǰΪ���һ�ţ���ʱ�������󻬣����л�����һ��
					if (viewPager.getCurrentItem() == viewPager.getAdapter()
							.getCount() - 1 && !isAutoPlay) {
						viewPager.setCurrentItem(0);
					}
					// ��ǰΪ��һ�ţ���ʱ�������һ������л������һ��
					else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
						viewPager
								.setCurrentItem(viewPager.getAdapter().getCount() - 1);
					}
					mScroller.setmDuration(500);
					break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub
			currentItem = pos;
			PhotoInfo p=imagePathList.get(pos);
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.dot_black);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_white);
				}
			}
		}

	}

	/**
	 * ִ���ֲ�ͼ�л�����
	 *
	 * @author caizhiming
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imagePathList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	class PhotoHolder {
		public ImageView imgView;
	}

	class FixedSpeedScroller extends Scroller {
		private int mDuration = 1000;

		public FixedSpeedScroller(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
								int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}

	}

}
