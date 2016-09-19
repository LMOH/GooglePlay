package com.lmoh.googleplay.ui.view;

import com.lmoh.googleplay.R;
import com.lmoh.googleplay.manager.ThreadManager;
import com.lmoh.googleplay.utils.UIUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 自定义FrameLayout， 1.可根据当前状态显示不同的布局 2.异步加载网络数据
 * 
 * @author PC-LMOH
 *
 */
public abstract class LoadingPage extends FrameLayout {
	// 五种页面加载状态
	private static int STATE_LOAD_UNDO = 1;
	private static int STATE_LOAD_LOADING = 2;
	private static int STATE_LOAD_ERROR = 3;
	private static int STATE_LOAD_EMPTY = 4;
	private static int STATE_LOAD_SUCCESS = 5;
	private int mCurrentState = STATE_LOAD_UNDO; // 默认状态
	private View mPageLoading;
	private View mPageError;
	private View mPageEmpty;
	private View mPageSuccess;

	public LoadingPage(Context context) {
		super(context);
		initView();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		// 初始化加载中布局
		if (mPageLoading == null) {
			mPageLoading = UIUtils.inflate(R.layout.page_loading);
			addView(mPageLoading);
		}

		if (mPageError == null) {
			mPageError = UIUtils.inflate(R.layout.page_load_error);
			Button btn_retry = (Button) mPageError.findViewById(R.id.btn_retry);
			btn_retry.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					loadData();
				}
			});
			addView(mPageError);
		}

		if (mPageEmpty == null) {
			mPageEmpty = UIUtils.inflate(R.layout.page_load_empty);
			addView(mPageEmpty);
		}

		//
		setCurrentView();
	}

	private void setCurrentView() {
		// 三元表达式，判断当前状态设置当前显示界面
		mPageLoading.setVisibility(
				mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING ? View.VISIBLE : View.GONE);
		mPageError.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
		mPageEmpty.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
		// 添加成功页面
		if (mPageSuccess == null && mCurrentState == STATE_LOAD_SUCCESS) {
			mPageSuccess = onCreateSuccessView();
			if (mPageSuccess != null) {
				addView(mPageSuccess);
			}
		}
		if (mPageSuccess != null) {
			mPageSuccess.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
		}

	}
/**加载数据，当mainactivity的viewpager界面改变是实现这个方法**/
	public void loadData() {
		// 判断当前状态，决定是否进入子线程加载数据
		if (mCurrentState != STATE_LOAD_LOADING) {
			mCurrentState = STATE_LOAD_LOADING;

/*			new Thread() {
				public void run() {
					// 根据加载方法返回状态结果(枚举)
					final StateResult stateResult = onLoad();
					
					if (stateResult != null) {
						// 运行在主线程，根据状态更新界面
						UIUtils.runOnUIThread(new Runnable() {
							
							@Override
							public void run() {
								//根据返回枚举，设置当前状态，并设置当前页面
								mCurrentState = stateResult.getState();
								setCurrentView();
							}
						});
					}
				};
			}.start();*/
			
			//使用线程管理者生产并执行线程
			ThreadManager.getThreadPool().execute(new Runnable() {
				
				@Override
				public void run() {
					// 根据加载方法返回状态结果(枚举)
					final StateResult stateResult = onLoad();
					
					if (stateResult != null) {
						// 运行在主线程，根据状态更新界面
						UIUtils.runOnUIThread(new Runnable() {
							
							@Override
							public void run() {
								//根据返回枚举，设置当前状态，并设置当前页面
								mCurrentState = stateResult.getState();
								setCurrentView();
							}
						});
					}
				}
			});
		}
	}
	// 加载成功返回的页面由调用者实现（抽象）
	public abstract View onCreateSuccessView();
	//加载网络数据，返回状态对象，返回结果由本类处理显示那个页面，由调用者实现
	public abstract StateResult onLoad();

	// 枚举：加载后的结果
	public enum StateResult {
		//枚举对象，传入状态
		LOAD_SUCCESS(STATE_LOAD_SUCCESS), LOAD_EMPTY(STATE_LOAD_EMPTY), LOAD_ERROR(STATE_LOAD_ERROR);

		private int state;

		private StateResult(int state) {
			this.state = state;
		}

		public int getState() {
			return state;
		}
	}

}
