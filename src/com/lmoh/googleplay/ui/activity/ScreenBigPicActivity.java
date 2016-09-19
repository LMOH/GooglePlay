package com.lmoh.googleplay.ui.activity;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.lmoh.googleplay.R;
import com.lmoh.googleplay.http.HttpHelper;
import com.lmoh.googleplay.utils.BitmapHelper;
import com.lmoh.googleplay.utils.UIUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class ScreenBigPicActivity extends BaseActivity {
	private int position;
	private ArrayList<String> list;
	private ViewPager vp_pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_big_pic);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		list = intent.getStringArrayListExtra("list");
		if (list != null) {
			vp_pager = (ViewPager) findViewById(R.id.vp_pager);
			vp_pager.setAdapter(new PicAdapter());
			vp_pager.setCurrentItem(position);
		}
	}
	
	class PicAdapter extends PagerAdapter{

		private BitmapUtils mBitmapUtils;
		
		public PicAdapter(){
			mBitmapUtils = BitmapHelper.getBitmapUtils();
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// 初始化item布局
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view  = new ImageView(UIUtils.getContext());
			mBitmapUtils.display(view, HttpHelper.URL + "image?name=" +list.get(position));
			container.addView(view);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ScreenBigPicActivity.this.finish();
				}
			});
			return view;
		}

		// 销毁item
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}
}
