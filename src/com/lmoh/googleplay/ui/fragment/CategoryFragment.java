package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;

import com.lmoh.google.http.protocol.CategoryProtocol;
import com.lmoh.googleplay.domain.CategoryInfo;
import com.lmoh.googleplay.ui.adapter.MyBaseAdapter;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.holder.CategoryHolder;
import com.lmoh.googleplay.ui.holder.TitleHolder;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.ui.view.MyListView;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;

/**
 * 分类Fragment
 * 
 * @author PC-LMOH
 *
 */
public class CategoryFragment extends BaseFragment {

	private ArrayList<CategoryInfo> data;

	@Override
	protected View onCreateSuccessView() {
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new CategoryAdapter(data));
		return view;
	}

	@Override
	protected StateResult onLoad() {

		CategoryProtocol protocal = new CategoryProtocol();
		data = protocal.getData(0);
		return check(data);
	}

	/**** ------------------------ ***********/
	class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

		public CategoryAdapter(ArrayList<CategoryInfo> data) {
			super(data);
		}

		// 由于这里布局类型比基类多一种，所以要重写几个方法
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1; // 类型数在原本基础加一
		}


		/**
		 * 获取条目view类型，可被子类调用
		 * 
		 * @return 默认返回normal
		 */
		public int getInnerType(int position) {
			// 判断是标题类型还是普通分类类型
			CategoryInfo info = data.get(position);

			if (info.isTitle) {
				// 返回标题类型
				return super.getInnerType(position) + 1;// 原来类型基础上加1; 注意:
														// 将TYPE_NORMAL修改为1;
			} else {
				// 返回普通类型
				return super.getInnerType(position);
			}
		}

		@Override
		public BaseHolder<CategoryInfo> getHolder(int position) {
			// 判断是标题类型还是普通分类类型, 来返回不同的holder
			CategoryInfo info = data.get(position);

			if (info.isTitle) {
				return new TitleHolder();
			} else {
				return new CategoryHolder();
			}
		}

		@Override
		public ArrayList<CategoryInfo> onLoadMore() {
			return null;
		}

		// 没有加载更多
		@Override
		public boolean hasMore() {
			return false;
		}
	}
}
