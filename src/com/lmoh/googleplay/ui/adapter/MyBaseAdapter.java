package com.lmoh.googleplay.ui.adapter;

import java.util.ArrayList;

import com.lmoh.googleplay.manager.ThreadManager;
import com.lmoh.googleplay.ui.holder.BaseHolder;
import com.lmoh.googleplay.ui.holder.MoreHolder;
import com.lmoh.googleplay.utils.UIUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

/**
 * @author PC-LMOH
 *  对Adapter的封装
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	private ArrayList<T> data;
	private static final int TYPE_NORMAL = 1;
	private static final int TYPE_MORE = 0;
	
	public MyBaseAdapter(ArrayList<T> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size()+1; 		//加上下拉加载布局
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() -1) {
			return TYPE_MORE;
		}else{
			return getInnerType(position);
		}
	}
	
	/**获取条目view类型，可被子类调用
	 * @return  默认返回normal
	 */
	public int getInnerType(int position) {		
		return TYPE_NORMAL;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		BaseHolder holder;	//(BaseHolder做了加载布局和初始化控件（由子类holder实现），还有setTag)
		if (convertView == null) {
			if (getItemViewType(position) == TYPE_MORE) {
				//若当前条目类型为more，返回MoreHolder对象
				holder = new MoreHolder(hasMore());
			}else{
				holder = getHolder(position);	//子类返回具体对象 
			}
		}else{
			holder = (BaseHolder) convertView.getTag();
		}
		if (getItemViewType(position) != TYPE_MORE) {		//条目类型不为more，则正常setdata
			//通过子类实现或者convertView存储拿到holder
			holder.setData(getItem(position));
		}else{ 
			// 加载更多布局
			//布局类型是TYPE_MORE，执行loadMore逻辑
			MoreHolder moreHolder = (MoreHolder)holder;
			//当状态为more是加载数据
			if (moreHolder.getData() ==MoreHolder.STATE_MORE_MORE) {
				loadMore(moreHolder);
			}
		}
		
		return holder.getRootView();
	}
	
	private boolean isLoad = false;		//标记是否加载更多
	/***加载更多数据，在getview调用***/
	public void loadMore(final MoreHolder holder){
		if (!isLoad) {
			isLoad = true;
		/*	new Thread(){
				public void run() {
					final ArrayList<T> moreData = onLoadMore();
					UIUtils.runOnUIThread(new Runnable() {
						
						@Override
						public void run() {
							if (moreData != null) {
								//加载有数据，判断数据长度
								if (moreData.size() < 20) {
									//默认一页20，小于二十，无数据
									holder.setData(MoreHolder.STATE_MORE_EMPTY);
									Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
								}else{
									//加载数据
									holder.setData(MoreHolder.STATE_MORE_MORE);
									//添加进集合
									data.addAll(moreData);
									//刷新适配器
									MyBaseAdapter.this.notifyDataSetChanged();
								}
							}else{
								//加载为空，失败
								holder.setData(MoreHolder.STATE_MORE_ERROR);
							}
							isLoad = false;
						}
					});
				};
			}.start();*/
			
			/***使用线程管理者加载更多数据***/
			ThreadManager.getThreadPool().execute(new Runnable() {
				
				@Override
				public void run() {
					final ArrayList<T> moreData = onLoadMore();
					UIUtils.runOnUIThread(new Runnable() {
						
						@Override
						public void run() {
							if (moreData != null) {
								//加载有数据，判断数据长度
								if (moreData.size() < 20) {
									//默认一页20，小于二十，无数据
									holder.setData(MoreHolder.STATE_MORE_EMPTY);
									Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
								}else{
									//加载数据
									holder.setData(MoreHolder.STATE_MORE_MORE);
									//添加进集合
									data.addAll(moreData);
									//刷新适配器
									MyBaseAdapter.this.notifyDataSetChanged();
								}
							}else{
								//加载为空，失败
								holder.setData(MoreHolder.STATE_MORE_ERROR);
							}
							isLoad = false;
						}
					});
				}
			});
		}
		
	}
	
	//让子类可重新修改返回值,以便修改加载的不同状态
	public boolean hasMore(){
		return true;
	}
	//获取当前集合的长度
	public int getListSizes(){
		return data.size();
	}
	//让子类实现holder
	public abstract BaseHolder<T> getHolder(int position);
	//子类实现加载更多并返回数据
	public abstract ArrayList<T> onLoadMore();
}
