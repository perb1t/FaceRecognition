package com.shijiwei.xkit.widget.stick;

import java.util.List;

/**
 * Created by shijiwei on 2017/10/16.
 *
 * @VERSION 1.0
 */

public class CategoryModel<T> {

    private String category;
    private List<T> data;


    public CategoryModel(String category, List<T> data) {
        this.category = category;
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return data == null ? 0 : data.size();
    }
}
