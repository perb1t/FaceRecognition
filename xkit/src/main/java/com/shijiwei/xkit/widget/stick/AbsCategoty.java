package com.shijiwei.xkit.widget.stick;

import android.widget.AbsListView;

/**
 * Created by shijiwei on 2017/10/17.
 *
 * @VERSION 1.0
 */

public interface AbsCategoty {

    /**
     * compute the vertical sliding distance of the listview
     */
    int getScrollY(AbsListView lv);
}
