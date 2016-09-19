package com.lmoh.googleplay.ui.holder;

import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.domain.DownloadInfo;
import com.lmoh.googleplay.manager.DownloadManager;
import com.lmoh.googleplay.manager.DownloadManager.DownloadObserver;
import com.lmoh.googleplay.ui.view.fly.ProgressHorizontal;
import com.lmoh.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownloadObserver, OnClickListener {

	private DownloadManager mDm;
	private FrameLayout flProgress;
	private ProgressHorizontal pbProgress;
	private int mCurrentState;
	private float mProgress;
	private Button btnDownload;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_download);
		btnDownload = (Button) view.findViewById(R.id.btn_download);
		btnDownload.setOnClickListener(this);
		
		// 初始化自定义进度条
		flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);
		flProgress.setOnClickListener(this);
		
		pbProgress = new ProgressHorizontal(UIUtils.getContext());
		pbProgress.setProgressBackgroundResource(R.drawable.progress_bg);// 进度条背景图片
		pbProgress.setProgressResource(R.drawable.progress_normal);// 进度条图片
		pbProgress.setProgressTextColor(Color.WHITE);// 进度文字颜色
		pbProgress.setProgressTextSize(UIUtils.dip2px(18));// 进度文字大小

		// 宽高填充父窗体
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		// 添加进度条
		flProgress.addView(pbProgress, params);
		mDm = DownloadManager.getInstance();
		mDm.registerObserver(this);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		DownloadInfo downloadInfo = mDm.getDownloadInfo(data);
		if (downloadInfo != null) {
			mCurrentState = downloadInfo.currentState;
			mProgress = downloadInfo.getProgress();
		} else {
			mCurrentState = DownloadManager.STATE_UNDO;
			mProgress = 0;
		}
		System.out.println("刷新UI");
		refreshUI(mCurrentState, mProgress);
	}

	/**
	 * 根据当前状态和进度更新ui
	 * 
	 * @param mCurrentState
	 * @param mProgress
	 */
	private void refreshUI(int currentState, float progress) {
		mCurrentState = currentState;
		mProgress = progress;

		switch (currentState) {
		case DownloadManager.STATE_UNDO:// 未下载
			System.out.println("STATE_UNDO");
			flProgress.setVisibility(View.GONE);
			btnDownload.setVisibility(View.VISIBLE);
			btnDownload.setText("下载");
			break;

		case DownloadManager.STATE_WAITING:// 等待下载
			flProgress.setVisibility(View.GONE);
			btnDownload.setVisibility(View.VISIBLE);
			btnDownload.setText("等待中..");
			break;

		case DownloadManager.STATE_DOWNLOADING:// 正在下载
			flProgress.setVisibility(View.VISIBLE);
			btnDownload.setVisibility(View.GONE);
			pbProgress.setCenterText("");
			pbProgress.setProgress(mProgress);// 设置下载进度
			break;

		case DownloadManager.STATE_PAUSE:// 下载暂停
			flProgress.setVisibility(View.VISIBLE);
			btnDownload.setVisibility(View.GONE);
			pbProgress.setCenterText("暂停");
			pbProgress.setProgress(mProgress);

			System.out.println("暂停界面更新:" + mCurrentState);
			break;

		case DownloadManager.STATE_ERROR:// 下载失败
			flProgress.setVisibility(View.GONE);
			btnDownload.setVisibility(View.VISIBLE);
			btnDownload.setText("下载失败");
			break;

		case DownloadManager.STATE_SUCCESS:// 下载成功
			flProgress.setVisibility(View.GONE);
			btnDownload.setVisibility(View.VISIBLE);
			btnDownload.setText("安装");
			break;

		default:
			break;
		}
	}

	/**
	 * 主线程更新ui 3-4
	 * 
	 * @param info
	 */
	private void refreshUIOnMainThread(final DownloadInfo info) {
		// 判断下载对象是否是当前应用
		AppInfo appInfo = getData();
		if (appInfo.id.equals(info.id)) {
			UIUtils.runOnUIThread(new Runnable() {

				@Override
				public void run() {
					refreshUI(info.currentState, info.getProgress());
				}
			});
		}
	}

	/*** 实现DownloadObserver被观察接口，实现方法 ***/
	@Override
	public void onDownloadStateChanged(DownloadInfo info) {
		refreshUIOnMainThread(info);
	}

	@Override
	public void onDownloadProgressChanged(DownloadInfo info) {
		refreshUIOnMainThread(info);
	}

	@Override
	public void onClick(View v) {
		// System.out.println("点击事件响应了:" + mCurrentState);

		switch (v.getId()) {
		case R.id.btn_download:
		case R.id.fl_progress:
			// 根据当前状态来决定下一步操作
			if (mCurrentState == DownloadManager.STATE_UNDO || mCurrentState == DownloadManager.STATE_ERROR
					|| mCurrentState == DownloadManager.STATE_PAUSE) {
				mDm.download(getData());// 开始下载
			} else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
					|| mCurrentState == DownloadManager.STATE_WAITING) {
				mDm.pause(getData());// 暂停下载
			} else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
				mDm.install(getData());// 开始安装
			}

			break;

		default:
			break;
		}
	}

}
