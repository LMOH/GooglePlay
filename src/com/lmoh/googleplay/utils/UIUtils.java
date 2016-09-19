package com.lmoh.googleplay.utils;

import com.lmoh.googleplay.global.BaseApplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

/**
 * UI工具类
 * 
 * @author PC-LMOH
 *
 */
public class UIUtils {
	// 1.获取全局对象
	public static Context getContext() {
		return BaseApplication.getContext();
	}

	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}

	public static int getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	// 2.获取资源对象
	public static String getString(int id) {
		return getContext().getResources().getString(id);
	}

	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	public static Drawable getDrawable(int id) {
		return getContext().getResources().getDrawable(id);
	}

	public static int getColor(int id) {
		return getContext().getResources().getColor(id);
	}

	// 根据id获取颜色的状态选择器
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	// 获取尺寸
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
	}

	/********************* dip和px转换 ***************************/

	public static int dip2px(float dip) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(int px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	// 加载布局文件
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	// 判断是否运行在主线程
	public static boolean isRunOnUIThread() {
		// 获取当前线程ID与主线程ID是否相同
		int myTid = android.os.Process.myTid();
		if (myTid == getMainThreadId()) {
			return true;
		}
		return false;
	}

	// 运行在主线程
	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			getHandler().post(r);
		}
	}
}
