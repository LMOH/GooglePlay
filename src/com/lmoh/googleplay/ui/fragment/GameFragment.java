package com.lmoh.googleplay.ui.fragment;

import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.ui.adapter.MyBaseAdapter;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.holder.GameHolder;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.utils.UIUtils;

import java.util.ArrayList;

import com.lmoh.google.http.protocol.GameProtocol;
import com.lmoh.googleplay.ui.view.MyListView;

import android.view.View;

/**游戏Fragment
 * @author PC-LMOH
 *
 */
public class GameFragment extends BaseFragment {

	private ArrayList<AppInfo> data;

	@Override
	protected View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new GameAdapter(data));
		return view;
	}

	@Override
	protected StateResult onLoad() {
		GameProtocol protocal = new GameProtocol();
		data = protocal.getData(0);
		return check(data);
	}
	
	class GameAdapter extends MyBaseAdapter<AppInfo>{

		public GameAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			return new GameHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {
			GameProtocol protocal = new GameProtocol();
			ArrayList<AppInfo> moreData = protocal.getData(getListSizes());
			return moreData;
		}
		
	}
}
