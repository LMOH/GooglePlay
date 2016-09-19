package com.lmoh.googleplay.ui.holder;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.utils.BitmapHelper;
import com.lmoh.googleplay.utils.UIUtils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeHeadHolder extends BaseHolder<ArrayList<String>> {

	private ArrayList<String> mData;
	private ViewPager mViewPager;
	private LinearLayout llContainer;
	private int mPreviousPos;

	@Override
	public View initView() {
		// 创建根布局RelativeLayout
		RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
		// 添加布局参数，因为RelativeLayout在ListView里面，所以添加其布局参数
		AbsListView.LayoutParams rootParams = new LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
				UIUtils.dip2px(150));
		rlRoot.setLayoutParams(rootParams);
		// 创建ViewPager，添加布局参数，并添加到RelativeLayout
		mViewPager = new ViewPager(UIUtils.getContext());
		RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		rlRoot.addView(mViewPager, vpParams);

		llContainer = new LinearLayout(UIUtils.getContext());
		llContainer.setOrientation(LinearLayout.HORIZONTAL);// 水平方向

		RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		// 设置内边距
		int padding = UIUtils.dip2px(10);
		llContainer.setPadding(padding, padding, padding, padding);

		// 添加规则, 设定展示位置
		llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部对齐
		llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);// 右对齐

		// 添加布局
		rlRoot.addView(llContainer, llParams);

		return rlRoot;
	}

	@Override
	public void refreshView(ArrayList<String> data) {
		this.mData = data;
		// 添加适配器
		mViewPager.setAdapter(new HomeHeadAdapter());
		// 指定从第几个条目开始为首个页面
		mViewPager.setCurrentItem(mData.size() * 10000);

		// 启动轮播条自动播放
		HomeHeaderTask task = new HomeHeaderTask();
		task.start();
		// 初始化指示器
		for (int i = 0; i < data.size(); i++) {
			ImageView point = new ImageView(UIUtils.getContext());

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			if (i == 0) {// 第一个默认选中
				point.setImageResource(R.drawable.indicator_selected);
			} else {
				point.setImageResource(R.drawable.indicator_normal);

				params.leftMargin = UIUtils.dip2px(4);// 左边距
			}

			point.setLayoutParams(params);

			llContainer.addView(point);
		}
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				position = position % mData.size();

				// 当前点被选中
				ImageView point = (ImageView) llContainer.getChildAt(position);
				point.setImageResource(R.drawable.indicator_selected);

				// 上个点变为不选中
				ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
				prePoint.setImageResource(R.drawable.indicator_normal);

				mPreviousPos = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	/** 自动循环 */

	class HomeHeaderTask implements Runnable {

		public void start() {
			// 移除之前发送的所有消息, 避免消息重复
			UIUtils.getHandler().removeCallbacksAndMessages(null);
			UIUtils.getHandler().postDelayed(this, 3000);
		}

		@Override
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			currentItem++;
			mViewPager.setCurrentItem(currentItem);

			// 继续发延时3秒消息, 实现内循环
			UIUtils.getHandler().postDelayed(this, 3000);
		}
	}

	/****/
	class HomeHeadAdapter extends PagerAdapter {

		private BitmapUtils mBitmapUtils;

		public HomeHeadAdapter() {
			mBitmapUtils = BitmapHelper.getBitmapUtils();
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 防止角标越界，取余
			position = position % mData.size();
			String url = mData.get(position);
			ImageView view = new ImageView(UIUtils.getContext());
			view.setScaleType(ScaleType.FIT_XY);
			mBitmapUtils.display(view, HttpHelper.URL + "image?name=" + url);

			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}
