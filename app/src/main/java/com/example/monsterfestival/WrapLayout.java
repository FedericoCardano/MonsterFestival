package com.example.monsterfestival;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class WrapLayout extends ViewGroup {

    private int itemMarginX, itemMarginY;

    public WrapLayout(Context context) {
        super(context);
        init();
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        itemMarginX = getResources().getDimensionPixelSize(R.dimen.item_margin_width);
        itemMarginY = getResources().getDimensionPixelSize(R.dimen.item_margin_height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int rowHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                if (x + childWidth > maxWidth) {
                    x = 0;
                    y += rowHeight + itemMarginY;
                    rowHeight = 0;
                }

                child.layout(x, y, x + childWidth, y + childHeight);
                x += childWidth + itemMarginX;
                rowHeight = Math.max(rowHeight, childHeight);
            }
        }

        int measuredWidth = resolveSize(maxWidth, widthMeasureSpec);
        int measuredHeight = resolveSize(y + rowHeight, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }
}
