package com.lmoh.googleplay.ui.activity;

import com.lmoh.google.http.protocol.HomeDetailProtocol;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.ui.holder.DetailAppInfoHolder;
import com.lmoh.googleplay.ui.holder.DetailDesHolder;
import com.lmoh.googleplay.ui.holder.DetailDownloadHolder;
import com.lmoh.googleplay.ui.holder.DetailPicHolder;
import com.lmoh.googleplay.ui.holder.DetailSafeHolder;
import com.lmoh.googleplay.ui.view.LoadingPage;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

public class HomeDetailActivity extends BaseActivity {

	private String packageName;
	private AppInfo data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载LoadingPage（由于HomeDetailActivity不同状态显示不同界面）
		LoadingPage loadingPage = new LoadingPage(this) {

			@Override
			public View onCreateSuccessView() {
				return HomeDetailActivity.this.onCreateSuccessView();
			}

			@Override
			public StateResult onLoad() {
				return HomeDetailActivity.this.onLoad();
			}

		};
		// 将loadingPage设置给activity
		setContentView(loadingPage);
		// 获取传递过来的包名
		packageName = getIntent().getStringExtra("packageName");
		// 开始加载网络数据,底层调用了onLoad方法,onLoad方法返回成功就调用onCreateSuccessView方法
		loadingPage.loadData();

		// 初始化actionbar，添加返回按钮，去掉项目图标
		initActionBar();
	}

	/**
	 * 初始化actionbar，添加返回按钮，去掉项目图标
	 */
	private void initActionBar() {

		ActionBar actionBar =  getSupportActionBar();

		actionBar.setTitle("   应用详情");// 设置标题
		//actionBar.setLogo(R.drawable.ic_launcher);// 设置logo

		//actionBar.setHomeButtonEnabled(true);// logo是否可以点击
		actionBar.setDisplayShowHomeEnabled(false);// 隐藏logo

		actionBar.setDisplayHomeAsUpEnabled(true);// 显示返回键

	}

	// actionbar点击事件处理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// 左上角home处点击之后的响应
			this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	// 自己的activity实现这两个方法
	public View onCreateSuccessView() {
		// 加载成功布局
		View view = UIUtils.inflate(R.layout.page_home_detail);
		// 每个lllayout加载应用信息
		FrameLayout fl_detail_appinfo = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
		DetailAppInfoHolder appHolder = new DetailAppInfoHolder();
		fl_detail_appinfo.addView(appHolder.getRootView());
		appHolder.setData(data);

		// 初始化安全描述模块
		FrameLayout fl_detail_safe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
		DetailSafeHolder safeHolder = new DetailSafeHolder();
		fl_detail_safe.addView(safeHolder.getRootView());
		safeHolder.setData(data);

		// 添加横向滑动布局HorizontalScrollView，截屏图片
		HorizontalScrollView hsv_detail_pics = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pics);
		DetailPicHolder pic_holder = new DetailPicHolder(HomeDetailActivity.this);
		hsv_detail_pics.addView(pic_holder.getRootView());
		pic_holder.setData(data);

		// 初始化应用描述
		FrameLayout flDetailDes = (FrameLayout) view.findViewById(R.id.fl_detail_des);
		DetailDesHolder desHolder = new DetailDesHolder();
		flDetailDes.addView(desHolder.getRootView());
		desHolder.setData(data);
		
		//初始化下载模块
		FrameLayout fl_detail_download = (FrameLayout) view.findViewById(R.id.fl_detail_download);
		DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
		fl_detail_download.addView(downloadHolder.getRootView());
		downloadHolder.setData(data);
		return view;
	}

	public StateResult onLoad() {
		HomeDetailProtocol protocol = new HomeDetailProtocol(packageName);
		data = protocol.getData(0);
		if (data != null) {
			return StateResult.LOAD_SUCCESS;
		} else {
			return StateResult.LOAD_ERROR;
		}
	}
}
