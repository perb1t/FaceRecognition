package com.shijiwei.xkit.widget.stick;

import java.util.Comparator;

/**
 * Created by shijiwei on 2017/10/16.
 *
 * @VERSION 1.0
 */

public class CategoryComparator implements Comparator<CategoryModel> {

    /**
     * 按照升序排列
     */
    private boolean asc;

    public CategoryComparator() {
        this(true);
    }

    public CategoryComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(CategoryModel o1, CategoryModel o2) {

        /**
         *  a < b  return - 1
         *  a = b  return 0
         *  a > b  return 1
         */
        String a;
        String b;
        if (asc) {
            a = o1.getCategory();
            b = o2.getCategory();
        } else {
            b = o1.getCategory();
            a = o2.getCategory();
        }

        return a.compareToIgnoreCase(b);
    }

}
