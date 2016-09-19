package com.lmoh.googleplay.ui.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.lmoh.google.http.protocol.HotProtocol;
import com.lmoh.googleplay.ui.view.FlowLayout;
import com.lmoh.googleplay.ui.view.LoadingPage.StateResult;
import com.lmoh.googleplay.utils.DrawableUtils;
import com.lmoh.googleplay.utils.UIUtils;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**热门Fragment
 * @author PC-LMOH
 *
 */
public class HotFragment extends BaseFragment {

	private ArrayList<String> data;

	@Override
	protected View onCreateSuccessView() {
		// 支持上下滑动
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		FlowLayout flow = new FlowLayout(UIUtils.getContext());

		int padding = UIUtils.dip2px(10);
		flow.setPadding(padding, padding, padding, padding);// 设置内边距

		flow.setHorizontalSpacing(UIUtils.dip2px(6));// 水平间距
		flow.setVerticalSpacing(UIUtils.dip2px(8));// 竖直间距

		for (int i = 0; i < data.size(); i++) {
			final String keyword = data.get(i);
			TextView view = new TextView(UIUtils.getContext());
			view.setText(keyword);

			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);// 18sp
			view.setPadding(padding, padding, padding, padding);
			view.setGravity(Gravity.CENTER);

			// 生成随机颜色
			Random random = new Random();
			int r = 30 + random.nextInt(200);
			int g = 30 + random.nextInt(200);
			int b = 30 + random.nextInt(200);

			int color = 0xffcecece;// 按下后偏白的背景色

			// GradientDrawable bgNormal = DrawableUtils.getGradientDrawable(
			// Color.rgb(r, g, b), UIUtils.dip2px(6));
			// GradientDrawable bgPress = DrawableUtils.getGradientDrawable(
			// color, UIUtils.dip2px(6));
			// StateListDrawable selector = DrawableUtils.getSelector(bgNormal,
			// bgPress);

			StateListDrawable selector = DrawableUtils.getSelector(
					Color.rgb(r, g, b), color, UIUtils.dip2px(6));
			view.setBackgroundDrawable(selector);

			flow.addView(view);

			// 只有设置点击事件, 状态选择器才起作用
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), keyword,
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		scrollView.addView(flow);
		return scrollView;
	}

	@Override
	protected StateResult onLoad() {
		HotProtocol protocal = new HotProtocol();
		data = protocal.getData(0);
		return check(data);
	}
	
}
