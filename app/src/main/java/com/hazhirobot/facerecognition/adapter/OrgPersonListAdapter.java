package com.hazhirobot.facerecognition.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.modle.FacePerson;

import java.util.List;

/**
 * Created by shijiwei on 2017/12/22.
 *
 * @VERSION 1.0
 */

public class OrgPersonListAdapter extends RecyclerView.Adapter<OrgPersonListAdapter.ViewHolder> {

    private List<FacePerson> dataSet;
    private Context ctx;


    public OrgPersonListAdapter(Context ctx, List<FacePerson> dataSet) {
        this.ctx = ctx;
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.layout_unsign_record, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        FacePerson entry = dataSet.get(position);
        holder.tvCompany.setText(entry.getCompany());
        holder.tvName.setText(entry.getName());
        holder.tvSex.setText("  " + entry.getSex() + "  ");
        holder.tvOcc.setText(entry.getOccupation());
        holder.tvPhone.setText(entry.getPhone());
        holder.tvPhone.setTextSize(10);
        holder.tvPhone.setText(entry.getPhone());


        if (position == dataSet.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (l != null) l.onLongClick(position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCompany;
        TextView tvName;
        TextView tvSex;
        TextView tvOcc;
        TextView tvPhone;
        TextView tvSignInTime;
        TextView divider;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvOcc = itemView.findViewById(R.id.tv_occ);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvSignInTime = itemView.findViewById(R.id.tv_sign_in_time);
            divider = itemView.findViewById(R.id.divider);
            tvSignInTime.setVisibility(View.GONE);
        }
    }

    public interface ItemLongClickListener {
        void onLongClick(int position);
    }

    private ItemLongClickListener l;

    public void setItemLongClickListener(ItemLongClickListener l) {
        this.l = l;
    }
}
