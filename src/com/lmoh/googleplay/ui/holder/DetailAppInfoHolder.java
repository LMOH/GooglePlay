package com.lmoh.googleplay.ui.holder;

import com.lidroid.xutils.BitmapUtils;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.domain.AppInfo;
import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.utils.BitmapHelper;
import com.lmoh.googleplay.utils.UIUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 详情页-应用信息
 * 
 * @author PC-LMOH
 *
 */
public class DetailAppInfoHolder extends BaseHolder<AppInfo> {

	private ImageView ivIcon;
	private TextView tvName;
	private TextView tvDownloadNum;
	private TextView tvVersion;
	private TextView tvDate;
	private TextView tvSize;
	private RatingBar rbStar;
	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_appinfo);

		ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
		tvName = (TextView) view.findViewById(R.id.tv_name);
		tvDownloadNum = (TextView) view.findViewById(R.id.tv_download_num);
		tvVersion = (TextView) view.findViewById(R.id.tv_version);
		tvDate = (TextView) view.findViewById(R.id.tv_date);
		tvSize = (TextView) view.findViewById(R.id.tv_size);
		rbStar = (RatingBar) view.findViewById(R.id.rb_star);

		mBitmapUtils = BitmapHelper.getBitmapUtils();
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + data.iconUrl);
		tvName.setText(data.name);
		tvDownloadNum.setText("下载量:" + data.downloadNum);
		tvVersion.setText("版本号:" + data.version);
		tvDate.setText(data.date);
		tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
		rbStar.setRating(data.stars);
	}

}
