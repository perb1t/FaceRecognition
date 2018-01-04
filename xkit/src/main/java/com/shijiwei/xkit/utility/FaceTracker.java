package com.shijiwei.xkit.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by shijiwei on 2017/12/19.
 *
 * @VERSION 1.0
 */

public class FaceTracker extends SurfaceView {

    private FaceDetector.Face[] faceSet;

    private Paint p;

    private int HEIGHT;
    private int WIDTH;

    private int imgHeight;
    private int imgWidth;

    private boolean isBackFacingCamera = true;


    public FaceTracker(Context context) {
        this(context, null);
    }

    public FaceTracker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceTracker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    private void initialize() {
        setZOrderOnTop(true);
        p = new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(2);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        WIDTH = getMeasuredWidth();
        HEIGHT = getMeasuredHeight();

    }

    public void trackFaceSet(FaceDetector.Face[] faceSet, int width, int height) {
        this.faceSet = faceSet;
        imgHeight = height;
        imgWidth = width;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (faceSet == null) return;
        for (int i = 0; i < faceSet.length; i++) {
            FaceDetector.Face face = faceSet[i];
            if (face == null) return;
            PointF centerPoint = new PointF();
            face.getMidPoint(centerPoint);
            float eyesDistance = face.eyesDistance();

            float wScale = (float) WIDTH / imgWidth;
            float hScale = (float) HEIGHT / imgHeight;

            p.setColor(Color.GREEN);

            canvas.drawRect(
                    new Rect(
                            (int) (wScale * (isBackFacingCamera ? centerPoint.x : imgWidth - centerPoint.x) - eyesDistance * 2),
                            (int) (hScale * centerPoint.y - eyesDistance * 2),
                            (int) (wScale * (isBackFacingCamera ? centerPoint.x : imgWidth - centerPoint.x) + eyesDistance * 2),
                            (int) (hScale * centerPoint.y + eyesDistance * 2)),
                    p);


        }

    }

}
