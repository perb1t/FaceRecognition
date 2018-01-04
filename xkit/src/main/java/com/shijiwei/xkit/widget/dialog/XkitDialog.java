package com.shijiwei.xkit.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shijiwei on 2017/10/23.
 *
 * @VERSION 1.0
 */

public class XkitDialog extends View {

    private Paint mPaint;

    public XkitDialog(Context context) {
        this(context, null);
    }

    public XkitDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XkitDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval = new RectF(getLeft(), getTop(), getRight(), getBottom());
        canvas.drawArc(oval, 270, 360, false, mPaint);
    }
}
