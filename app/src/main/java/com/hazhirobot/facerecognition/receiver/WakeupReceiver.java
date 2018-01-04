package com.hazhirobot.facerecognition.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hazhirobot.facerecognition.service.CommandObserver;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class WakeupReceiver extends BroadcastReceiver {

    final String ACTION_TTS = "com.cityeasy.voice.enable.tts";


    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "语音唤醒", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, CommandObserver.class));
    }


}
