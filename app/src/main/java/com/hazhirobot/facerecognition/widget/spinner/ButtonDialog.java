package com.hazhirobot.facerecognition.widget.spinner;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.hazhirobot.facerecognition.R;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class ButtonDialog extends Dialog implements View.OnClickListener {

    private View btLeft;
    private View btRight;

    private TextView tvTitle;

    public ButtonDialog(@NonNull Context context) {
        this(context, R.style.simple_dialog);
    }

    public ButtonDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.layout_dialog_);

        btLeft = findViewById(R.id.bt_left);
        btRight = findViewById(R.id.bt_right);
        tvTitle = findViewById(R.id.tv_title);

        btLeft.setOnClickListener(this);
        btRight.setOnClickListener(this);
    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {

        dismiss();

        if (v.getId() == R.id.bt_left) {
            if (l != null) l.onLeftButtonClick();
        } else {
            if (l != null) l.onRightButtonClick();
        }
    }

    public interface OnClickListener {

        void onLeftButtonClick();

        void onRightButtonClick();
    }

    private OnClickListener l;

    public void setOnClickCallback(OnClickListener l) {
        this.l = l;
    }
}
