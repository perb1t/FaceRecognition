package com.hazhirobot.facerecognition.widget.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by shijiwei on 2017/12/20.
 *
 * @VERSION 1.0
 */

public class CameraPreView extends SurfaceView {

    private Camera mHWCamera;

    public CameraPreView(Context context) {
        super(context);
    }

    public CameraPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void SupportedPreviewSizes() {
        List<Camera.Size> supportedPreviewSizes = mHWCamera.getParameters().getSupportedPreviewSizes();
    }
}
