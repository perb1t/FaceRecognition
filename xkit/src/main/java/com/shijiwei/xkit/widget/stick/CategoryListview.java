package com.shijiwei.xkit.widget.stick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by shijiwei on 2017/9/27.
 *
 * @VERSION 1.0
 */

public class CategoryListview extends FrameLayout {

    private TextView mTopTitel;
    private ListView mListView;

    public CategoryListview(Context context) {
        this(context, null);
    }

    public CategoryListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialView();
    }

    private void initialView() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int offsetY = getScrollY(view);


            }
        });


    }


    /**
     * Calculate the vertical sliding distance of the listview
     *
     * @param listView
     * @return
     */
    public int getScrollY(AbsListView listView) {
        View c = listView.getChildAt(0);
        if (c == null) return 0;
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }


}
