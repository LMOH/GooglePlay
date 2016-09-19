package com.lmoh.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**自定义Application，进行全局初始化
 * @author PC-LMOH
 *
 */
public class BaseApplication extends Application {

	/**
	 * 全局context
	 */
	private static Context context;
	/**
	 * 全局handler
	 */
	private static Handler handler;
	/**
	 * 主线程ID
	 */
	private static int mainThreadTid;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		handler = new Handler();
		mainThreadTid = android.os.Process.myTid();
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThreadId() {
		return mainThreadTid;
	}
}
