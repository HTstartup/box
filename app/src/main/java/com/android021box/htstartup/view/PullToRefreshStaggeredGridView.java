package com.android021box.htstartup.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.android021box.htstartup.R;
import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by arctu on 2015/8/7.
 */
public class PullToRefreshStaggeredGridView extends PullToRefreshBase<StaggeredGridView> {

    /**
     * Constructor
     */
    public PullToRefreshStaggeredGridView(Context context) {
        super(context);
    }

    /**
     * Constructor
     */
    public PullToRefreshStaggeredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     */
    public PullToRefreshStaggeredGridView(Context context, Mode mode) {
        super(context, mode);
    }

    /**
     * Constructor
     */
    public PullToRefreshStaggeredGridView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected StaggeredGridView createRefreshableView(Context context, AttributeSet attrs) {
        StaggeredGridView gridView = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            gridView = new InternalStaggeredGridViewSDK9(context, attrs);
        } else {
            gridView = new StaggeredGridView(context, attrs);
        }
        gridView.setId(R.id.gridview);
        return gridView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        boolean result = false;
        int last = getRefreshableView().getChildCount() - 1;
        View view = getRefreshableView().getChildAt(last);

        int firstVisiblePosition = getRefreshableView().getFirstVisiblePosition();
        int visibleItemCount = getRefreshableView().getChildCount();
        int itemCount = getRefreshableView().getAdapter().getCount();
        if (firstVisiblePosition + visibleItemCount >= itemCount) {
            if (view != null) {
                result = view.getBottom() <= getRefreshableView().getHeight();
            }
        }
        return result;
    }

    @Override
    protected boolean isReadyForPullStart() {
        boolean result = false;
        View view = getRefreshableView().getChildAt(0);
        if (getRefreshableView().getFirstVisiblePosition() == 0) {
            if (view != null) {
                // getTop() and getBottom() are relative to the ListView,
                // so if getTop() is negative, it is not fully visible
                result = view.getTop() >= 0;
            }
        }
        return result;
    }

    @TargetApi(9)
    final class InternalStaggeredGridViewSDK9 extends StaggeredGridView {
        static final int OVERSCROLL_FUZZY_THRESHOLD = 2;
        static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

        public InternalStaggeredGridViewSDK9(Context context) {
            super(context);
        }

        public InternalStaggeredGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InternalStaggeredGridViewSDK9(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            boolean result = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            OverscrollHelper.overScrollBy(PullToRefreshStaggeredGridView.this, deltaX, scrollX, deltaY, scrollY, getScrollRange(), isTouchEvent);
            return result;
        }

        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
    }
}
