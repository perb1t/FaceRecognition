package com.hazhirobot.facerecognition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hazhirobot.facerecognition.R;

/**
 * Created by shijiwei on 2017/11/6.
 *
 * @VERSION 1.0
 */

public class ActionSheetAdapter extends BaseAdapter {

    private String[] menuSet;
    private Context context;

    private int icons[] = {R.mipmap.face_recognition,R.mipmap.input_face,R.mipmap.setting};

    public ActionSheetAdapter(String[] menuSet, Context context) {
        this.menuSet = menuSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuSet == null ? 0 : menuSet.length;
    }

    @Override
    public Object getItem(int position) {
        return menuSet == null ? 0 : menuSet[position];
    }

    @Override
    public long getItemId(int position) {
        return menuSet == null ? null : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_actionsheet, null);
            holder = new ViewHolder();
            holder.item = convertView.findViewById(R.id.tv_item);
            holder.icon = convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item.setText(menuSet[position]);
        holder.icon.setImageResource(icons[position]);
        return convertView;
    }

    class ViewHolder {
        TextView item;
        ImageView icon;
    }
}
