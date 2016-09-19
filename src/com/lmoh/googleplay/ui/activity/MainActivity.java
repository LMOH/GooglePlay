package com.lmoh.googleplay.ui.activity;

import com.lmoh.googleplay.R;
import com.lmoh.googleplay.ui.fragment.BaseFragment;
import com.lmoh.googleplay.ui.fragment.FragmentFactory;
import com.lmoh.googleplay.ui.view.PagerTab;
import com.lmoh.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {

	private PagerTab mPagerTab;
	private ViewPager mViewPager;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPagerTab = (PagerTab) findViewById(R.id.pt_pagertab);
		mViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
		
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mPagerTab.setViewPager(mViewPager);
		//设置页面变化监听
		mPagerTab.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = FragmentFactory.createFragment(position);
				fragment.loadData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		//初始化actionbar
		initActionBar();
	}
	
	
	
	/**
	 * 初始化actionbar
	 */
	private void initActionBar() {
		ActionBar actionbar = getSupportActionBar();

		actionbar.setHomeButtonEnabled(true);// home处可以点击
		actionbar.setDisplayHomeAsUpEnabled(true);// 显示左上角返回键,当和侧边栏结合时展示三个杠图片

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

		// 初始化抽屉开关
		toggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer_am, R.string.drawer_open,
				R.string.drawer_close);

		toggle.syncState();// 同步状态, 将DrawerLayout和开关关联在一起
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// 切换抽屉
			toggle.onOptionsItemSelected(item);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}


	/**若viewpager里面的是fragment，此时就要用FragmentPagerAdapter，也是继承PagerAdapter的
	 * @author PC-LMOH
	 */
	class MyPagerAdapter extends FragmentPagerAdapter{

		private String[] mTabTitles;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			mTabTitles = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			//返回指针标题，标题数组在strings文件中写好
			return mTabTitles[position];
		}


		@Override
		public Fragment getItem(int position) {
			//根据条目位置获取每个条目对应的fragment
			BaseFragment fragment = FragmentFactory.createFragment(position);
			return fragment;
		}

		@Override
		public int getCount() {
			return mTabTitles.length;
		}
		
	}
}
