package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;

import com.lmoh.google.http.protocol.AppProtocol;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.ui.adapter.MyBaseAdapter;
import com.lmoh.googleplay.ui.fragment.HomeFragment.HomeAdapter;
import com.lmoh.googleplay.ui.holder.AppHolder;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.view.MyListView;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.ListView;

/**应用Fragment
 * @author PC-LMOH
 *
 */
public class AppFragment extends BaseFragment {

	private ArrayList<AppInfo> data;

	@Override
	protected View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new AppAdapter(data));
		return view;
	}

	// onLoad()执行返回LOAD_SUCCESS，才会执行onCreateSuccessView方法
	@Override
	protected StateResult onLoad() {
		AppProtocol appProtocal = new AppProtocol();
		data = appProtocal.getData(0);
		return check(data);
	}
	
	class AppAdapter extends MyBaseAdapter<AppInfo>{

		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {
			AppProtocol appProtocal = new AppProtocol();
			ArrayList<AppInfo> moreData = appProtocal.getData(getListSizes());
			return moreData;
		}
		
	}
}
