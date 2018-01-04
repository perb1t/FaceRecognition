package com.shijiwei.xkit.widget.component.title;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiwei.xkit.R;
import com.shijiwei.xkit.utility.display.DisplayUtility;

/**
 * Created by shijiwei on 2017/9/27.
 *
 * @VERSION 1.0
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private FrameLayout containerLayout;
    private TextView tvBack;
    private TextView tvTitle;

    private TitleBarCallBack callBack;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_title, this);
        containerLayout = findViewById(R.id.title_container_layout);
        tvBack = findViewById(R.id.button_back);
        tvTitle = findViewById(R.id.tv_title);
        layout();

        tvBack.setOnClickListener(this);
    }

    /**
     * 如果SDK版本大于等于19(android 4.4),则设置沉浸式模式，需要调整高度
     */
    private void layout() {

        if (Build.VERSION.SDK_INT >= 19) {
            containerLayout.setPadding(0, DisplayUtility.getStatusbarHeight((Activity) getContext()), 0, 0);
            requestLayout();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            if (callBack != null)
                callBack.onLeftButonClick(view);
        }
    }

    /**
     * The component of the title bar' click response callback event
     */
    public interface TitleBarCallBack {

        void onLeftButonClick(View v);
    }

    public void setTitleBarCallBack(TitleBarCallBack callBack) {
        this.callBack = callBack;
    }

    public void setTitel(String title) {
        setTitel(title, Color.argb(255,255,255,255));
    }

    public void setTitel(String title, int txColor) {
        tvTitle.setText(title);
        tvTitle.setTextColor(txColor);
    }
}
