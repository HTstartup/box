package com.android021box.htstartup.http;

public class Base {
	public static final int NULL = 0x000;
	public static final int SUCCEED = 0x001;
	public static final int FALSE = 0x002;
	public static final int CLEAR_LIST = 0x003;
	public static final int INCU_REFRESH = 0x004;
	public static final int INCU_LOADMORE = 0x005;
	public static final int ERROR = 0x006;
	public static final int EVENT_REFRESH = 0x007;
	public static final int EVENT_LOADMORE = 0x008;
	public static final int GET_INCU_DETAIL = 0x009;
	private static String BaseUrl = "http://192.168.0.125/htstartup/";

	public String getBaseUrl() {
		return BaseUrl;
	}
}
