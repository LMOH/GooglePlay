package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;

import com.lmoh.googleplay.ui.view.LoadingPage;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private LoadingPage mLoadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLoadingPage = new LoadingPage(UIUtils.getContext()) {

			//重新加载成功方法显示页面，由fragment子类实现
			@Override
			public View onCreateSuccessView() {
				return BaseFragment.this.onCreateSuccessView();
			}
			//重写加载方法，由子类实现
			@Override
			public StateResult onLoad() {
				return BaseFragment.this.onLoad();
			}

		};
		return mLoadingPage;
	}

	// 具体实现由子类完成
	/**
	 * 子线程运行
	 * 
	 * @return
	 */
	protected abstract StateResult onLoad();

	/**
	 * 主线程运行，onLoad()返回结果成功才运行方法，切换到加载成功界面
	 * 
	 * @return
	 */
	protected abstract View onCreateSuccessView();

	// 开始加载数据
	public void loadData() {
		if (mLoadingPage != null) {
			mLoadingPage.loadData();
		}
	}
	

	// 对网络返回数据的合法性进行校验
	public StateResult check(Object obj) {
		if (obj != null) {
			if (obj instanceof ArrayList) {// 判断是否是集合
				ArrayList list = (ArrayList) obj;

				if (list.isEmpty()) {
					return StateResult.LOAD_EMPTY;
				} else {
					return StateResult.LOAD_SUCCESS;
				}
			}
		}

		return StateResult.LOAD_ERROR;
	}
}
