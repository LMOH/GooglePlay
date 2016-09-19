package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;

import com.lmoh.google.http.protocol.HomeProtocol;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.ui.activity.HomeDetailActivity;
import com.lmoh.googleplay.ui.adapter.MyBaseAdapter;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.holder.HomeHeadHolder;
import com.lmoh.googleplay.ui.holder.HomeHolder;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.ui.view.MyListView;
import com.lmoh.googleplay.utils.UIUtils;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 主页Fragment
 * 
 * @author PC-LMOH
 *
 */
public class HomeFragment extends BaseFragment {

	private ArrayList<AppInfo> data;
	private ArrayList<String> mPicList;

	@Override
	protected View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		HomeHeadHolder headHolder = new HomeHeadHolder();
		View headView = headHolder.getRootView();
		view.addHeaderView(headView);
		//添加轮播图数据
		if (mPicList != null) {
			headHolder.setData(mPicList);
		}
		view.setAdapter(new HomeAdapter(data));
		//设置listview条目点击监听
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//跳转到应用详情页activity,传递包名
				Intent intent = new Intent(UIUtils.getContext(),HomeDetailActivity.class);
				String packageName = data.get(position-1).packageName;		//-1减去头布局个数
				intent.putExtra("packageName", packageName);
				startActivity(intent);
			}
		});
		return view;
	}

	// onLoad()执行返回LOAD_SUCCESS，才会执行onCreateSuccessView方法,加载首页数据
	@Override
	protected StateResult onLoad() {
	//从HomeProtocal获取网络数据
		HomeProtocol protocal = new HomeProtocol();
		data = protocal.getData(0);	//从数据开头开始取
		mPicList = protocal.getPicList();
		return check(data);
	}

	class HomeAdapter extends MyBaseAdapter<AppInfo> {

		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
		}
		//给父类adapter返回BaseHolder对象,此对象则实现了BaseHolder未实现的加载布局，刷新布局方法
		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			
			return new HomeHolder();
		}
	//加载更多	
		@Override
		public ArrayList<AppInfo> onLoadMore() {
		/*	ArrayList<AppInfo> moreData = new ArrayList<AppInfo>();
			for (int i = 0; i < 20; i++) {
				moreData.add("onLoadMore的数据"+i);
			}
			SystemClock.sleep(2000);*/
			HomeProtocol protocal = new HomeProtocol();
			ArrayList<AppInfo> moreData = protocal.getData(getListSizes());
			return moreData;
		}
		
	}
}
