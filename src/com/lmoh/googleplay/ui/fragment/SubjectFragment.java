package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;

import com.lmoh.google.http.protocol.SubjectProtocol;
import com.lmoh.googleplay.domain.SubjectInfo;
import com.lmoh.googleplay.ui.adapter.MyBaseAdapter;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.holder.SubjectHolder;
import com.lmoh.googleplay.ui.view.MyListView;
import com.lmoh.googleplay.utils.UIUtils;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Fragment
 * 
 * @author PC-LMOH
 *
 */
public class SubjectFragment extends BaseFragment {

	private ArrayList<SubjectInfo> data;

	@Override
	protected View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new SubjectAdapter(data));
		return view;
	}

	@Override
	protected StateResult onLoad() {
		SubjectProtocol protocal = new SubjectProtocol();
		data = protocal.getData(0);
		return check(data);
	}

	class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder(int position) {
			return new SubjectHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			SubjectProtocol protocal = new SubjectProtocol();
			ArrayList<SubjectInfo> moreData = protocal.getData(getListSizes());
			return moreData;
		}
		
	}
}
