package com.lmoh.googleplay.ui.holder;

import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.CategoryInfo;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.TextView;

/**
 * 分类模块标题holder
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {

	public TextView tvTitle;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_title);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView(CategoryInfo data) {
		tvTitle.setText(data.title);
	}

}
