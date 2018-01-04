package com.shijiwei.xkit.widget.web;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijiwei.xkit.R;

import java.util.List;

/**
 * Created by shijiwei on 2016/11/30.
 */
public class BrowserAdapter extends BaseAdapter {

    private List<ResolveInfo> mDataSet;
    private Context mContext;

    public BrowserAdapter(List<ResolveInfo> mDataSet, Context mContext) {
        this.mDataSet = mDataSet;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet == null ? null : mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataSet == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_brower_list, null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_lable);

            holder.iv_icon.setImageDrawable(mDataSet.get(position).activityInfo.applicationInfo.loadIcon(mContext.getPackageManager()));
            holder.tv_name.setText(mDataSet.get(position).activityInfo.applicationInfo.loadLabel(mContext.getPackageManager()));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
    }
}
