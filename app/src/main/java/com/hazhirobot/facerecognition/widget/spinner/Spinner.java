package com.hazhirobot.facerecognition.widget.spinner;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hazhirobot.facerecognition.R;
import com.hazhirobot.facerecognition.modle.Org;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/12/20.
 *
 * @VERSION 1.0
 */

public class Spinner extends Dialog implements View.OnClickListener {

    private RecyclerView lvSelector;
    private View btConfirm;

    private SpinnerAdapter adapter;
    private List<Org> dataSet = new ArrayList<>();


    public Spinner(@NonNull Context context) {
        this(context, R.style.simple_dialog);
    }

    public Spinner(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.layout_dialog_selector);
        lvSelector = findViewById(R.id.dialog_lv);
        lvSelector.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter == null) {
            adapter = new SpinnerAdapter(getContext(), dataSet);
            lvSelector.setAdapter(adapter);
        }
        btConfirm = findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                Org org = (Org) adapter.getChecked();
                if (scb != null) scb.onSpinnerConfirm(org);
                dismiss();
                break;
        }
    }

    public void updateData(List<Org> dataSet) {

        this.dataSet.clear();
        if (dataSet != null) {
            for (int i = 0; i < dataSet.size(); i++) {
                this.dataSet.add(dataSet.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }


    public interface SpinnerCallback {
        void onSpinnerConfirm(Org org);
    }

    private SpinnerCallback scb;

    public void setSpinnerCallback(SpinnerCallback scb) {
        this.scb = scb;
    }
}
