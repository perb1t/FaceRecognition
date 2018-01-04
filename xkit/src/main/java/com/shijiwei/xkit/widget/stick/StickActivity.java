package com.shijiwei.xkit.widget.stick;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shijiwei.xkit.R;
import com.shijiwei.xkit.app.base.BaseActivity;
import com.shijiwei.xkit.utility.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/10/16.
 *
 * @VERSION 1.0
 */

public class StickActivity extends BaseActivity {
    private ListView mListView;
    private ExpandableListView explv;
    private TextView mTopCategoryLable;

    private CategoryAdapter adapter;
    private List<String> data = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.a_stick;
    }

    @Override
    public void initialData(Bundle savedInstanceState) {
        for (int i = 0; i < 20; i++) {
            data.add(i + "");
        }
    }

    @Override
    public void initialView() {
        mListView = (ListView) findViewById(R.id.list_view);
        mTopCategoryLable = (TextView) findViewById(R.id.tv_lable);
        adapter = new CategoryAdapter(data, this);
        mListView.setAdapter(adapter);

        CategoryComparator comparator = new CategoryComparator();
        comparator.compare(null,null);
    }

    FrameLayout.LayoutParams layoutParams;
    private int margin = 0;

    @Override
    public void initialEvn() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                layoutParams = (FrameLayout.LayoutParams) mTopCategoryLable.getLayoutParams();
                int offset = getScrollY(view);
                margin = (firstVisibleItem + 1) * 210 - 70 - offset;
                LogUtil.e("======", firstVisibleItem + ", " + margin);

                if (margin > -70 && margin <= 0) {
                    layoutParams.setMargins(0, margin, 0, 0);
                    mTopCategoryLable.requestLayout();
                } else {
                    mTopCategoryLable.setText(firstVisibleItem  + "");
                    layoutParams.setMargins(0, 0, 0, 0);
                    mTopCategoryLable.requestLayout();
                }

            }

        });

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void onNetworkStateChanged(int type, boolean isAvailable) {

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
