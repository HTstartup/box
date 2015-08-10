package com.android021box.htstartup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android021box.htstartup.adapter.IncuAdapter;
import com.android021box.htstartup.http.AsyncTask;
import com.android021box.htstartup.http.Base;
import com.android021box.htstartup.info.IncuInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class IncuDetailActivity extends Activity {
    private String url=new Base().getBaseUrl()+"Incubators/outputDetail";
    private IncuInfo incubator=new IncuInfo();
    private List<IncuInfo> list = new ArrayList<IncuInfo>();
    private Activity context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incu_detail);
    }
    private void initView(){//实例化layout中控件。
    }
    private void initIncubator(){//从服务器中获取incubator详细信息。
        Intent intent = getIntent();
        Bundle data=intent.getExtras();
        int id=data.getInt("id");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("lastid", "0"));
        Thread thread = new AsyncTask(handler, params, url,
                Base.INCU_REFRESH, list);
        thread.start();
    }
    private void setView(){//根据孵化器信息填充控件。

    }
    Handler handler = new Handler() {//处理从服务器端获得的信息。
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Base.GET_INCU_DETAIL:
                    incubator = list.get(0);
                    setView();
                    break;
                case Base.NULL:
                    Toast.makeText(context, "暂无孵化器信息", Toast.LENGTH_SHORT).show();
                    break;
                case Base.FALSE:
                case Base.ERROR:
                    Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
