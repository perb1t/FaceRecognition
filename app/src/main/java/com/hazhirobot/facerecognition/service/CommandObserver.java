package com.hazhirobot.facerecognition.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cityeasy.asr.SpeechAsrListener;
import com.cityeasy.common.RobotsUtils;
import com.hazhirobot.facerecognition.ui.RecognitionActivity;
import com.shijiwei.xkit.utility.log.LogUtil;

/**
 * Created by shijiwei on 2018/1/4.
 *
 * @VERSION 1.0
 */

public class CommandObserver extends Service implements SpeechAsrListener {

    private static final String TAG = "CommandObserver";
    private RobotsUtils mRobotsUtils;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG, "onCreate");
        createRobotUtil();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e(TAG, "onStartCommand");
        createRobotUtil();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onVolumeChanged(int var1) {

    }

    @Override
    public void onBeginOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public boolean onResult(String result) {
        LogUtil.e(TAG, "onResult : " + result);

        if (result.contains("我是谁") || result.contains("你认识我吗")){
            startRecognition(this);
            return true;
        }

        return true;
    }

    @Override
    public void onError(int var1) {

    }


    private Handler mObserverHandler = new Handler();

    private void createRobotUtil() {
        if (mRobotsUtils == null) {
            mRobotsUtils = RobotsUtils.getInstance(this);
            mObserverHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRobotsUtils.startListening(CommandObserver.this);
                }
            }, 500);
        }
    }

    private void startRecognition(Context ctx) {
        if (ctx == null) return;
        Intent act = new Intent(ctx, RecognitionActivity.class);
        act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        act.putExtra("oneshot", true);
        ctx.startActivity(act);
    }
}
