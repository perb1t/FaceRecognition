package com.shijiwei.xkit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by shijiwei on 2017/12/19.
 *
 * @VERSION 1.0
 */

public class ExceptionImageView extends android.support.v7.widget.AppCompatImageView {

    public ExceptionImageView(Context context) {
        super(context);
    }

    public ExceptionImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExceptionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
        }
    }
}
