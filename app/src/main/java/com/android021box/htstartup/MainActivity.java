package com.android021box.htstartup;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private SlidingMenu slidingMenu;
    private Button btn_sliding;
    private LocalActivityManager manager = null;
    private ViewPager pager = null;
    private TextView t1, t2, t3;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    private ImageView cursor;
    private Activity subActivityA, subActivityB, subActivityC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        init();
        setListener();
        initTextView();
        InitImageView();
        initPagerViewer();
        initSlidingMenu();
    }

    private void init() {
        btn_sliding = (Button) findViewById(R.id.btn_sliding);
    }

    private void setListener() {
        btn_sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle(true);
            }
        });

    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.activity_sliding_menu);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setShadowWidth(getWindowManager().getDefaultDisplay()
                .getWidth() / 40);
        slidingMenu.setShadowDrawable(R.drawable.sliding_shadow);
        //initSlidingView();
        //setSlidingListener();
    }

    private void initSlidingView() {

        View view = slidingMenu.getMenu();
    }

    private void initTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t1.setText("孵化器");
        t2.setText("活 动");
        t3.setText("我 的");
        t1.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }

    private void initPagerViewer() {
        pager = (ViewPager) findViewById(R.id.viewpage);
        ArrayList<View> list = new ArrayList<View>();
        Intent intent1 = new Intent(this, IncuActivity.class);
        Intent intent2 = new Intent(this, EventActivity.class);
        Intent intent3 = new Intent(this, SelfActivity.class);
        list.add(getView("A", intent1));
        list.add(getView("B", intent2));
        list.add(getView("C", intent3));
        pager.setAdapter(new MyPagerAdapter(list));
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
        subActivityA = manager.getActivity("A");
        subActivityB = manager.getActivity("B");
        subActivityC = manager.getActivity("C");
    }

    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.icon_roller)
                .getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);
    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    public class MyPagerAdapter extends PagerAdapter {
        List<View> list = new ArrayList<View>();

        public MyPagerAdapter(ArrayList<View> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager pViewPager = ((ViewPager) container);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ViewPager pViewPager = ((ViewPager) arg0);
            pViewPager.addView(list.get(arg1));
            return list.get(arg1);
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
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;
        int two = one * 2;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                        t2.setTextColor(Color.BLACK);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                        t3.setTextColor(Color.BLACK);
                    }
                    t1.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                        t1.setTextColor(Color.BLACK);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                        t3.setTextColor(Color.BLACK);
                    }
                    t2.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                        t1.setTextColor(Color.BLACK);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                        t2.setTextColor(Color.BLACK);
                    }
                    t3.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            pager.setCurrentItem(index);
        }
    }

    ;

}
