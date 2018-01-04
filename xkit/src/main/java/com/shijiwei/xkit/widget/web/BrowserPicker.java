package com.shijiwei.xkit.widget.web;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;

import com.shijiwei.xkit.R;
import com.shijiwei.xkit.widget.fullseize.FullSizeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2016/11/30.
 */
public class BrowserPicker extends Dialog implements Animation.AnimationListener,
        View.OnClickListener,
        AdapterView.OnItemClickListener {

    private View mDialogView;

    private FullSizeGridView mBrowserGridView;
    private TextView mTakeCancelButton;

    private AnimationSet mDialogOutAnim;
    private AnimationSet mDialogInAnim;
    private boolean isDismiss = false;

    private BrowserAdapter mBrowserAdapter;
    private List<ResolveInfo> mDataSet;

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public BrowserPicker(Context context) {
        this(context, 0);
    }

    public BrowserPicker(Context context, int themeResId) {
        super(context, R.style.simple_dialog);
        //setCancelable(false);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.layout_web_sheet);
        initialView(context);

        mDialogInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.menu_bottombar_in);
        mDialogOutAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.menu_bottombar_out);
        mDialogOutAnim.setAnimationListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mDialogView.startAnimation(mDialogInAnim);
    }


    @Override
    public void dismiss() {
        if (!isDismiss) {
            isDismiss = true;
            mDialogView.startAnimation(mDialogOutAnim);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void initialView(Context context) {
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.getLayoutParams().width = getContext().getResources().getDisplayMetrics().widthPixels;
//        mDialogView.setPadding(0, 0, 0, DisplayUtility.getVirtualKeyHeight((Activity) context));

        mTakeCancelButton = (TextView) findViewById(R.id.btn_cancel);
        mBrowserGridView = (FullSizeGridView) findViewById(R.id.browser_grid_view);
        mDataSet = new ArrayList<>();
        mBrowserAdapter = new BrowserAdapter(mDataSet, getContext());
        mBrowserGridView.setAdapter(mBrowserAdapter);
        mTakeCancelButton.setOnClickListener(this);
        mBrowserGridView.setOnItemClickListener(this);
    }


    public void notifyDataChanged(List<ResolveInfo> list) {
        this.mDataSet.clear();
        this.mDataSet.addAll(list);
        mBrowserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isDismiss = false;
        super.dismiss();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (callBack != null)
            callBack.callBack(position);
    }

    public interface CallBack {
        void callBack(int position);
    }

}
