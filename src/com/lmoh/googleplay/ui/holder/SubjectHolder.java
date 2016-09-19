package com.lmoh.googleplay.ui.holder;

import com.lidroid.xutils.BitmapUtils;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.domain.SubjectInfo;
import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.utils.BitmapHelper;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView ivPic;
	private TextView tvTitle;

	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_subject);
		ivPic = (ImageView) view.findViewById(R.id.iv_pic);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(SubjectInfo data) {
		tvTitle.setText(data.des);
		mBitmapUtils.display(ivPic, HttpHelper.URL + "image?name=" + data.url);
	}

}
