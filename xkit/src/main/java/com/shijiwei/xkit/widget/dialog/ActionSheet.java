package com.shijiwei.xkit.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.shijiwei.xkit.R;

/**
 * Created by shijiwei on 2017/11/3.
 *
 * @VERSION 1.0
 */

public class ActionSheet extends Dialog implements Animation.AnimationListener, View.OnClickListener {


    private View mDialogContainerView;

    private AnimationSet mEnterAnimation;
    private AnimationSet mEixtAnimation;

    private boolean isDialogDismissing = false;

    private TextView mButtonCancel;
    private TextView mButtonSelectPhoto;
    private TextView mButtonTakePhoto;

    private
    @LayoutRes
    int layoutResId;

    public ActionSheet(@NonNull Context context) {
        this(context, 0);
    }

    public ActionSheet(@NonNull Context context, @LayoutRes int layoutResId) {
        this(context, layoutResId, 0);
    }

    public ActionSheet(@NonNull Context context, @LayoutRes int layoutResId, @StyleRes int themeResId) {
        super(context, themeResId == 0 ? R.style.simple_dialog : themeResId);

        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(false);
        this.layoutResId = layoutResId;

        mEnterAnimation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.menu_bottombar_in);
        mEixtAnimation = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.menu_bottombar_out);
        mEixtAnimation.setAnimationListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId == 0 ? R.layout.layout_action_sheet : layoutResId);
        mDialogContainerView = getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogContainerView.getLayoutParams().width = getContext().getResources().getDisplayMetrics().widthPixels;

        mButtonSelectPhoto = findViewById(R.id.btn_take_photo_from_album);
        mButtonTakePhoto = findViewById(R.id.btn_take_photo_from_camera);
        mButtonCancel = findViewById(R.id.btn_cancel);

        mButtonSelectPhoto.setOnClickListener(this);
        mButtonTakePhoto.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDialogContainerView.startAnimation(mEnterAnimation);
    }

    @Override
    public void dismiss() {
        if (!isDialogDismissing) {
            isDialogDismissing = true;
            mDialogContainerView.startAnimation(mEixtAnimation);
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isDialogDismissing = false;
        super.dismiss();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_cancel) {
            dismiss();
        } else if (v.getId() == R.id.btn_take_photo_from_album) {
            if (actionSheetCallback != null)
                actionSheetCallback.onSelectPhoto(v);
        } else if (v.getId() == R.id.btn_take_photo_from_camera) {
            if (actionSheetCallback != null)
                actionSheetCallback.onTakePhoto(v);
        }
    }


    public interface ActionSheetCallback {

        void onTakePhoto(View view);

        void onSelectPhoto(View view);
    }

    public ActionSheetCallback actionSheetCallback;

    public void setActionSheetCallback(ActionSheetCallback actionSheetCallback) {
        this.actionSheetCallback = actionSheetCallback;
    }
}
