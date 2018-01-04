package com.cityeasy.demo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.cityeasy.asr.SpeechAsrListener;
import com.cityeasy.common.RobotsUtils;

import java.util.List;

/**
 * Created by shijiwei on 2017/11/10.
 *
 * @VERSION 1.0
 */

public class WakeupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("=======", " 机器人被唤醒了 " + isAppOnForeground(context));
        RobotsUtils.getInstance(context).startListening(new SpeechAsrListener() {
            @Override
            public void onVolumeChanged(int var1) {
                Log.e("=======", " onVolumeChanged " + var1);
            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public boolean onResult(String result) {
                Log.e("=======", " onResult " + result);
                return true;
            }

            @Override
            public void onError(int var1) {

            }
        });
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public boolean isAppOnForeground(Context ctx) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) ctx.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName =  ctx.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
