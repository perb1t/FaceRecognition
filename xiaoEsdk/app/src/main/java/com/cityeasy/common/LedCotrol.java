package com.cityeasy.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by hsx on 2016/9/21.
 */
public class LedCotrol {

    private static final String TAG = "VoiceService";

    public static final String START_EYE_LED = "com.cityeasy.led.color.enable";
    public static final String STOP_EYE_LED = "com.cityeasy.led.color.disable";

    public static final int VOICE_STATUS_SPEECHSTART = 0;
    public static final int VOICE_STATUS_LISTENING = 1;
    public static final int VOICE_STATUS_RECOGNITION = 2;
    public static final int VOICE_STATUS_RECEND = 3;
    public static final int VOICE_STATUS_SPEAKING = 4;
    public static final int VOICE_STATUS_SPEECHERROR = 5;
    public static final int VOICE_STATUS_SPEECHEND = 6;

    public static void sendMessage(Context context, int msg) {

        switch (msg) {
            case VOICE_STATUS_SPEECHSTART:
                Log.w(TAG, "msg: VOICE_STATUS_SPEECHSTART ");
                startLedEye(context, 2, 3);//white
                break;
            case VOICE_STATUS_LISTENING:
                Log.w(TAG, "msg: VOICE_STATUS_LISTENING ");
                startLedEye(context, 3, 5);//green
                break;
            case VOICE_STATUS_RECOGNITION:
                Log.w(TAG, "msg: VOICE_STATUS_RECOGNITION ");
                startLedEye(context, 3, 2);//blue
                break;
            case VOICE_STATUS_SPEAKING:
                Log.w(TAG, "msg: VOICE_STATUS_SPEAKING ");
                startLedEye(context, 6, 5);//
                break;
            case VOICE_STATUS_SPEECHERROR:
                Log.w(TAG, "msg: VOICE_STATUS_SPEECHERROR ");
                startLedEye(context, 0, 1);//red
                break;
            case VOICE_STATUS_RECEND:
            case VOICE_STATUS_SPEECHEND:
                Log.w(TAG, "msg: VOICE_STATUS_SPEECHEND ");
                stopLedEye(context);
                break;
            default:
                break;
        }
    }

    public static void stopLedEye(Context context) {
        startLedEye(context, STOP_EYE_LED);
    }

    public static void startLedEye(Context context, String msg) {
        Intent i = new Intent();
        i.setAction(msg);
        context.sendBroadcast(i);
    }

    public static void startLedEye(Context context, int color, int gap) {
        Intent i = new Intent(LedCotrol.START_EYE_LED);
        i.putExtra("gap", gap);
        i.putExtra("color", color);
        context.sendBroadcast(i);
    }

}
