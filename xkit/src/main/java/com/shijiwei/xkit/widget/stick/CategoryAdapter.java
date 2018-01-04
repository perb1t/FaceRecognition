package com.shijiwei.xkit.widget.stick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shijiwei.xkit.R;

import java.util.List;

/**
 * Created by shijiwei on 2017/10/16.
 *
 * @VERSION 1.0
 */

public class CategoryAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public CategoryAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, null);
            holder = new ViewHolder();
            holder.tvContent = convertView.findViewById(R.id.tv_content);
            holder.tvLable = convertView.findViewById(R.id.tv_lable);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvLable.setText(data.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView tvLable;
        TextView tvContent;
    }

    public int getLableHeight() {
        return 0;
    }

    public int getContentHeight() {
        return 0;
    }

}
