package com.example.monsterfestival.classes_dir;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.monsterfestival.R;

public class WrapLayoutManager extends LinearLayoutManager {

    private int itemMarginX, itemMarginY;

    public WrapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init(context);
    }

    private void init(Context context) {
        itemMarginX = context.getResources().getDimensionPixelSize(R.dimen.item_margin_width);
        itemMarginY = context.getResources().getDimensionPixelSize(R.dimen.item_margin_height);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int width = getWidth();

        int x = 0;
        int y = 0;
        int rowHeight = 0;

        for (int i = 0; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChild(view, itemMarginX, itemMarginY);
            int childWidth = getDecoratedMeasuredWidth(view);
            int childHeight = getDecoratedMeasuredHeight(view);
            if (x + childWidth > width) {
                x = 0;
                y += rowHeight;
                rowHeight = 0;
            }

            layoutDecorated(view, x, y, x + childWidth, y + childHeight);

            x += childWidth;
            rowHeight = Math.max(rowHeight, childHeight);
        }
    }
}

