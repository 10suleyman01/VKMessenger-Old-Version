package com.suleyman.vkclient.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

public class AppTextView extends AppCompatTextView {
	
	public AppTextView(Context context, AttributeSet attrs) {
		super(context, attrs);	
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
			int width = getMaxWidth(getLayout());
			if (width > 0 && width < getMeasuredWidth()) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), heightMeasureSpec);
			}
		}
	}
	
	public int getMaxWidth(Layout layout) {
		
		int lineCount = layout.getLineCount();
		if (lineCount < 3) return 0;
		
        float maxWidth = 0;
        for (int i = 0; i < lineCount; i++) {
            maxWidth = Math.max(maxWidth, layout.getLineWidth(i));
        }
		
		return (int) Math.ceil(maxWidth);
	}
	
}
