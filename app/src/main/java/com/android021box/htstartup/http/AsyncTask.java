package com.android021box.htstartup.http;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android021box.htstartup.httputil.EventUtil;
import com.android021box.htstartup.httputil.IncuUtil;
import com.android021box.htstartup.info.EventInfo;
import com.android021box.htstartup.info.IncuInfo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncTask extends Thread implements Runnable {
    private List<NameValuePair> params;
    private String url;
    private Handler PostHandler;
    private int taskId;
    private List list;

    public AsyncTask(Handler PostHandler, List<NameValuePair> params,
                     String url, int taskId, List list) {
        this.PostHandler = PostHandler;
        this.params = params;
        this.url = url;
        this.taskId = taskId;
        this.list = list;
        this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            // �����߳�����һ����������ʱ�쳣��handler
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                // TODO
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        int res = submit_Data();
        Message msg = PostHandler.obtainMessage();
        msg.what = res;
        PostHandler.sendMessage(msg);
    }

    private boolean ifRefresh(int taskId) {
        boolean refresh = false;
        switch (taskId) {
            case Base.INCU_REFRESH:
            case Base.EVENT_REFRESH:
                refresh = true;
                break;
        }
        return refresh;
    }

    public int submit_Data() {
        int msg = 0;
        try {
            CustomHttpClient client = new CustomHttpClient();
            HttpData httpdata = new HttpData(client.getHttpClient());
            String result = httpdata.getdata(url, params);
            Log.e("AsyncTask", "result:" + result + "/");
            if (result.equals("null")) {
                if (ifRefresh(taskId)) {
                    msg = Base.CLEAR_LIST;
                } else {
                    msg = Base.NULL;
                }
            } else if (!result.equals("null")) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    switch (taskId) {
                        case Base.INCU_REFRESH:
                        case Base.INCU_LOADMORE:
                            list.clear();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jsonObject = jArray.getJSONObject(i);
                                IncuInfo inc = new IncuUtil()
                                        .getIncu(jsonObject);
                                list.add(inc);
                            }
                            msg = taskId;
                            break;
                        case Base.EVENT_REFRESH:
                        case Base.EVENT_LOADMORE:
                            list.clear();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jsonObject = jArray.getJSONObject(i);
                                EventInfo ev = new EventUtil()
                                        .getEvent(jsonObject);
                                list.add(ev);
                            }
                            msg = taskId;
                            break;
                    }
                } catch (JSONException e) {
                    msg = Base.FALSE;
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            msg = Base.FALSE;
            e.printStackTrace();
        }
        return msg;
    }
}
