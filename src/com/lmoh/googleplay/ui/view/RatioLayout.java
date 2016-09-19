package com.lmoh.googleplay.ui.view;

import com.lmoh.googleplay.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 自定义framelayout（自定义属性） 功能：根据传入自定义属性值，设置宽高比例
 * 
 * @author PC-LMOH
 *
 */
public class RatioLayout extends FrameLayout {

	private float ratio;

	public RatioLayout(Context context) {
		super(context);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取属性值
		// attrs.getAttributeFloatValue(index, defaultValue)
		// 获取到属性集合
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
		ratio = typeArray.getFloat(R.styleable.RatioLayout_ratio, -1);
		typeArray.recycle();
		System.out.println("ratio = " + ratio);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 1.获取宽度，2.根据图片比例值计算高度，3.重新测量控件
		// MeasureSpec.AT_MOST; 至多模式, 控件有多大显示多大, wrap_content
		// MeasureSpec.EXACTLY; 确定模式, 类似宽高写死成dip, match_parent
		// MeasureSpec.UNSPECIFIED; 未指定模式.

		int width = MeasureSpec.getSize(widthMeasureSpec);// 获取宽度值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 获取宽度模式
		int height = MeasureSpec.getSize(heightMeasureSpec);// 获取高度值
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 获取高度模式

		// 宽度确定, 高度不确定, ratio合法, 才计算高度值
		if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio > 0) {
			// 图片宽度 = 控件宽度 - 左侧内边距 - 右侧内边距
			int imageWidth = width - getPaddingLeft() - getPaddingRight();

			// 图片高度 = 图片宽度/宽高比例
			int imageHeight = (int) (imageWidth / ratio + 0.5f);

			// 控件高度 = 图片高度 + 上侧内边距 + 下侧内边距
			height = imageHeight + getPaddingTop() + getPaddingBottom();

			// 根据最新的高度来重新生成heightMeasureSpec(高度模式是确定模式)
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}

		// 按照最新的高度测量控件
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
