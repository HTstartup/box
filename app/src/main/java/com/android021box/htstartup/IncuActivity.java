package com.android021box.htstartup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android021box.htstartup.adapter.IncuAdapter;
import com.android021box.htstartup.http.AsyncTask;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.http.NetWorkState;
import com.android021box.htstartup.info.IncuInfo;
import com.android021box.htstartup.tool.OnTabActivityResultListener;
import com.android021box.htstartup.view.ShowLoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class IncuActivity extends Activity implements
        OnTabActivityResultListener {
    private PullToRefreshListView mPullToRefreshListView;
    private List<IncuInfo> list = new ArrayList<IncuInfo>();
    private List<IncuInfo> newlist = new ArrayList<IncuInfo>();
    private ListView mListView;
    private IncuAdapter adapter;
    private long mExitTime;
    private static String BaseUrl = new Base().getBaseUrl();
    private ShowLoadingDialog mDialog;
    private boolean first = true;
    private Activity context = this;
    private static String url = BaseUrl + "get_incu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incu);
        init();
        setListener();
        FirstRefresh();
    }
    private void init() {
        mDialog = new ShowLoadingDialog(this, R.style.mydialog, false, false,
                R.drawable.spinner);
        adapter = new IncuAdapter(list, this);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        mListView = mPullToRefreshListView.getRefreshableView();
        mListView.setAdapter(adapter);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void setListener() {
        mPullToRefreshListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        if (refreshView.isHeaderShown()) {
                            Toast.makeText(IncuActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                            MyOnRefresh();
                        } else {
                            Toast.makeText(IncuActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                            MyOnLoadmore();
                        }
                    }
                });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                mPullToRefreshListView.onRefreshComplete();
                if (first) {
                    mDialog.dismiss();
                    first = false;
                }
                switch (msg.what) {
                    case Base.INCU_REFRESH:
                        list.clear();
                        list.addAll(newlist);
                        adapter = new IncuAdapter(list, context);
                        mListView.setAdapter(adapter);
                        break;
                    case Base.INCU_LOADMORE:
                        list.addAll(newlist);
                        adapter.notifyDataSetChanged();
                        break;
                    case Base.NULL:
                        Toast.makeText(context, "暂无更多孵化器信息", Toast.LENGTH_SHORT).show();
                        break;
                    case Base.FALSE:
                    case Base.ERROR:
                        Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                        break;
                    case Base.CLEAR_LIST:
                        list.clear();
                        adapter = new IncuAdapter(list, context);
                        mListView.setAdapter(adapter);
                        Toast.makeText(context, "暂无孵化器信息", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    private void MyOnRefresh() {
        if (new NetWorkState(this).networkStatusOK()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("lastid", "0"));
            Thread thread = new AsyncTask(handler, params, url,
                    Base.INCU_REFRESH, newlist);
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
                params.add(new BasicNameValuePair("lastid", ""+list.get(
                        list.size() - 1).getId()));
            } catch (Exception e) {
                Log.e("SaleBlog", "error to load more" + e.toString());
            }
            Thread thread = new AsyncTask(handler, params, url,
                    Base.INCU_LOADMORE, newlist);
            thread.start();
        } else {
            if (first) {
                mDialog.dismiss();
                first = false;
            }
            Toast.makeText(context, "您的网络罢工了",Toast.LENGTH_SHORT).show();
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
