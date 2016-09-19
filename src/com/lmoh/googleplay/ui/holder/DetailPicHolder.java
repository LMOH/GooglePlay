package com.lmoh.googleplay.ui.holder;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.ui.activity.BaseActivity;
import com.lmoh.googleplay.ui.activity.ScreenBigPicActivity;
import com.lmoh.googleplay.utils.BitmapHelper;
import com.lmoh.googleplay.utils.UIUtils;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DetailPicHolder extends BaseHolder<AppInfo> {

	private ImageView[] ivPics;
	private BitmapUtils mBitmapUtils;
	private BaseActivity mActivity;

	public DetailPicHolder(BaseActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_picinfo);

		ivPics = new ImageView[5];
		ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
		ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
		ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
		ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
		ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		final ArrayList<String> screen = data.screen;

		for (int i = 0; i < 5; i++) {
			final int j = i;
			if (i < screen.size()) {
				mBitmapUtils.display(ivPics[i], HttpHelper.URL + "image?name=" + screen.get(i));

				ivPics[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转activity, activity展示viewpager
						// 将集合通过intent传递过去, 当前点击的位置i也可以传过去
						Intent intent = new Intent(UIUtils.getContext(),ScreenBigPicActivity.class);
						intent.putExtra("list", screen);
						intent.putExtra("position", j);
						mActivity.startActivity(intent);
					}
				});
			} else {
				ivPics[i].setVisibility(View.GONE);
			}
		}

	}

}
