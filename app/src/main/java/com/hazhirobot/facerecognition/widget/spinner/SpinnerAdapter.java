package com.hazhirobot.facerecognition.widget.spinner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.modle.Org;

import java.util.List;

/**
 * Created by shijiwei on 2017/12/25.
 *
 * @VERSION 1.0
 */

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ViewHolder> {

    private Context ctx;
    private List<Org> dataSet;

    public SpinnerAdapter(Context ctx, List<Org> dataSet) {
        this.ctx = ctx;
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_spinner_lable, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.cb.setChecked(dataSet.get(position).isChecked());
        holder.cb.setText(dataSet.get(position).getName());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < dataSet.size(); i++) {
                    dataSet.get(i).setChecked(false);
                }
                dataSet.get(position).setChecked(true);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public Object getChecked() {
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).isChecked()) {
                return dataSet.get(i);
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb_selector);
        }
    }
}
