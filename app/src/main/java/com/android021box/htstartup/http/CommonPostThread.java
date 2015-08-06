package com.android021box.htstartup.http;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CommonPostThread extends Thread implements Runnable {
	private List<NameValuePair> params;
	private HttpData httpdata;
	private String url;
	private Handler PostHandler;
	private int kind;
	private int http;

	public CommonPostThread(Handler PostHandler, List<NameValuePair> params,
			HttpData httpdata, String url, int kind, int http) {
		this.PostHandler = PostHandler;
		this.params = params;
		this.httpdata = httpdata;
		this.url = url;
		this.kind = kind;
		this.http = http;
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
		if (kind == 1) {
			Message msg = PostHandler.obtainMessage();
			msg.what = res;
			PostHandler.sendMessage(msg);
		}
	}

	public int submit_Data() {
		int msg = 0;
		String result = null;
		if (http == 0) {
			result = httpdata.senddata(url, params);
		} else if (http == 1) {
			result = httpdata.sendcomplexdata(url, params);
		}
		Log.e("Result",result);
		if (result.equals("succeed")) {
			msg = Base.SUCCEED;
		}else if (result.equals("error")) {
			msg =Base.ERROR;
		}else if (result.equals("false")) {
			msg =Base.FALSE;
		}
		return msg;
	}
}