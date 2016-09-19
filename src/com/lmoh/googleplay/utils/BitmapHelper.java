package com.lmoh.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * 单例模式的BitmapUtils,懒汉模式，只创建一个实例
 * 
 * @author PC-LMOH
 *
 */
public class BitmapHelper {
	private static BitmapUtils mBitmapUtils = null;

	// 单例, 懒汉模式
	public static BitmapUtils getBitmapUtils() {
		if (mBitmapUtils == null) {
			synchronized (BitmapHelper.class) {
				if (mBitmapUtils == null) {
					mBitmapUtils = new BitmapUtils(UIUtils.getContext());
				}
			}
		}

		return mBitmapUtils;
	}
}
