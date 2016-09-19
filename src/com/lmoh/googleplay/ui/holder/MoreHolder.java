package com.lmoh.googleplay.ui.holder;

import com.lmoh.googleplay.R;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreHolder extends BaseHolder<Integer> {

	/**
	 * 加载中分三种状态：加载中，加载失败，无数据
	 */
	public static final int STATE_MORE_MORE = 1;
	public static final int STATE_MORE_ERROR = 2;
	public static final int STATE_MORE_EMPTY = 3;
	private LinearLayout llMoreMore;
	private TextView tvMoreError;

	public MoreHolder(boolean hasMore) {
		/*if (hasMore) {
			// setData:为父类设置条目数据方法，里面同时刷新显示（refreshView，由子类具体实现）
			setData(STATE_MORE_MORE);
		} else {
			setData(STATE_MORE_EMPTY);
		}*/
		//三元表达式
		setData(hasMore?STATE_MORE_MORE:STATE_MORE_EMPTY);
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_more);
		llMoreMore = (LinearLayout) view.findViewById(R.id.ll_more_more);
		tvMoreError = (TextView) view.findViewById(R.id.tv_more_error);
		return view;
	}

	@Override
	public void refreshView(Integer data) {
		//根据不同状态，显示或隐藏控件
		switch (data) {
		case STATE_MORE_MORE:
			llMoreMore.setVisibility(View.VISIBLE);
			tvMoreError.setVisibility(View.GONE);
			break;
		case STATE_MORE_ERROR:
			llMoreMore.setVisibility(View.GONE);
			tvMoreError.setVisibility(View.VISIBLE);
			break;
		case STATE_MORE_EMPTY:
			llMoreMore.setVisibility(View.GONE);
			tvMoreError.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

}
