package com.lmoh.googleplay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**自定义listview，设置状态选择器为全透明，设置无分割线，背景会黑，解决
 * @author PC-LMOH
 *
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		initView();
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		this.setSelector(new ColorDrawable());				//设置状态选择器为全透明
		this.setDivider(null); 									//设置无分割线
		this.setCacheColorHint(Color.TRANSPARENT);	//背景会黑，解决
	}

}
