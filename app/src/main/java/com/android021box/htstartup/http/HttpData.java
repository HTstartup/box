package com.android021box.htstartup.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpData {
	private static HttpClient httpclient;

	public HttpData(HttpClient client) {
		this.httpclient = client;
	}

	public String getdata(String url, List params) {
		String result = "";
		try {
			HttpPost httppost = new HttpPost(url);
			if (params != null) {
				httppost.setEntity(new UrlEncodedFormEntity(params, "utf8"));
			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String aresult = EntityUtils.toString(entity);
			if (aresult.equals("error")) {
				result = "error";
			} else if (aresult.equals("null")) {
				result = "null";
			} else if (aresult.equals("false")) {
				result = "false";
			} else {
				InputStream is = new ByteArrayInputStream(aresult.getBytes());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "utf8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			}
		} catch (Exception e) {
			result = "error";
			Log.e("log_tag", "Error get data " + e.toString());
		}
		return result;
	}

	public static String senddata(String url, List params) {
		String result = "";
		try {
			HttpPost httppost = new HttpPost(url);
			if (params != null) {
				httppost.setEntity(new UrlEncodedFormEntity(params, "utf8"));
			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			result = "error";
			Log.e("log_tag", "Error get data " + e.toString());
		}
		return result;
	}

	public String sendcomplexdata(String url, List<NameValuePair> params) {
		String result = "";
		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			HttpPost httpRequest = new HttpPost(url);
			HttpContext localContext = new BasicHttpContext();
			for (int index = 0; index < params.size(); index++) {
				if (params.get(index).getName().equalsIgnoreCase("image")) {
					// If the key equals to "image", we use FileBody to transfer
					// the data
					entity.addPart(params.get(index).getName(), new FileBody(
							new File(params.get(index).getValue())));
				} else {
					entity.addPart(params.get(index).getName(),
							new StringBody(params.get(index).getValue(),
									Charset.forName("UTF-8")));
				}
			}
			httpRequest.setEntity(entity);
			HttpParams parms = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(parms, 15000);
			HttpConnectionParams.setSoTimeout(parms, 45000);
			HttpResponse httpResponse = httpclient.execute(httpRequest,
					localContext);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
			Log.e("log_tag", result);
		}
		return result;
	}
}
