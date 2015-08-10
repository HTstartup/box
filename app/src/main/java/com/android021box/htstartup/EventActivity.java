package com.android021box.htstartup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android021box.htstartup.adapter.EventAdapter;
import com.android021box.htstartup.http.AsyncTask;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.http.NetWorkState;
import com.android021box.htstartup.info.EventInfo;
import com.android021box.htstartup.tool.OnTabActivityResultListener;
import com.android021box.htstartup.view.PullToRefreshStaggeredGridView;
import com.android021box.htstartup.view.ShowLoadingDialog;
import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class EventActivity extends Activity implements
        OnTabActivityResultListener {
    private List<EventInfo> list = new ArrayList<EventInfo>();
    private List<EventInfo> newlist = new ArrayList<EventInfo>();
    private StaggeredGridView mGridView;
    private EventAdapter adapter;
    private long mExitTime;
    private static String BaseUrl = new Base().getBaseUrl();
    private ShowLoadingDialog mDialog;
    private boolean first = true;
    private Activity context = this;
    private static String url = BaseUrl + "Events/outputList";
    private PullToRefreshStaggeredGridView ptrStaggeredGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initViews();
        setListener();
        FirstRefresh();
    }

    private void initViews() {
        mDialog = new ShowLoadingDialog(this, R.style.mydialog, false, false,
                R.drawable.spinner);
        adapter = new EventAdapter(this,list);
        ptrStaggeredGridView = (PullToRefreshStaggeredGridView) findViewById(R.id.event_staggered_events);
        mGridView=ptrStaggeredGridView.getRefreshableView();
        mGridView.setAdapter(adapter);
        ptrStaggeredGridView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void setListener() {
        ptrStaggeredGridView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<StaggeredGridView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<StaggeredGridView> refreshView) {
                        if (refreshView.isHeaderShown()) {
                            Toast.makeText(EventActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                            MyOnRefresh();
                        } else {
                            Toast.makeText(EventActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                            MyOnLoadmore();
                        }
                    }
                });
        ptrStaggeredGridView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        adapter.scroll = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        adapter.scroll = false;
                        adapter.notifyDataSetChanged();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        adapter.scroll = false;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                adapter.start_index = firstVisibleItem;
                adapter.end_index = firstVisibleItem + visibleItemCount;
                if (adapter.end_index >= totalItemCount) {
                    adapter.end_index = totalItemCount - 1;
                }
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (MainActivity.state) {
                ptrStaggeredGridView.onRefreshComplete();
                if (first) {
                    mDialog.dismiss();
                    first = false;
                }
                switch (msg.what) {
                    case Base.EVENT_REFRESH:
                        list.clear();
                        list.addAll(newlist);
                        adapter = new EventAdapter(context, list);
                        mGridView.setAdapter(adapter);
                        break;
                    case Base.EVENT_LOADMORE:
                        list.addAll(newlist);
                        adapter.notifyDataSetChanged();
                        break;
                    case Base.NULL:
                        Toast.makeText(context, "暂无更多活动信息", Toast.LENGTH_SHORT).show();
                        break;
                    case Base.FALSE:
                    case Base.ERROR:
                        Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                        break;
                    case Base.CLEAR_LIST:
                        list.clear();
                        adapter = new EventAdapter(context, list);
                        mGridView.setAdapter(adapter);
                        Toast.makeText(context, "暂无活动信息", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    private void MyOnRefresh() {//
        if (new NetWorkState(this).networkStatusOK()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lastid", "0"));
            Thread thread = new AsyncTask(handler, params, url,
                    Base.EVENT_REFRESH, newlist);
            thread.start();
        } else {
            if (first) {
                mDialog.dismiss();
                first = false;
            }
            Toast.makeText(context, "您的网络罢工了", Toast.LENGTH_SHORT).show();
        }
    }

    private void MyOnLoadmore() {
        if (new NetWorkState(this).networkStatusOK()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            try {
                params.add(new BasicNameValuePair("lastid", "" + list.get(
                        list.size() - 1).getId()));
            } catch (Exception e) {
                Log.e("Event", "error to load more" + e.toString());
            }
            Thread thread = new AsyncTask(handler, params, url,
                    Base.EVENT_LOADMORE, newlist);
            thread.start();
        } else {
            if (first) {
                mDialog.dismiss();
                first = false;
            }
            Toast.makeText(context, "您的网络罢工了", Toast.LENGTH_SHORT).show();
        }
    }

    private void FirstRefresh() {
        mDialog.show();
        first = true;
        MyOnRefresh();
    }

    @Override
    public void onTabActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

