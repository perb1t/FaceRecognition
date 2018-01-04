package com.cityeasy.tts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import com.cityeasy.aidl.ISpeechTts;
import com.cityeasy.aidl.ISpeechTtsListener;
import com.cityeasy.asr.SpeechArs;

/**
 * Created by hsx on 2016/10/11.
 */
public class SpeechTts {

    private static final String TAG = "SpeechTts";

    private static final int MSG_SPEAKBEGIN = 1;
    private static final int MSG_SPEAKPAUSED = 2;
    private static final int MSG_SPEAKPROGRESS = 3;
    private static final int MSG_SPEAKRESUMED = 4;
    private static final int MSG_ONCOMPLETED = 5;
    private static final int MSG_ONBUFFERPROGRESS = 6;

    private static SpeechTts sInstance;
    private static Context mAppCtx;
    private SpeechTts.UIHandler mUiHandler;
    private static byte[] sLock = new byte[0];

    ISpeechTts mSpeechTts = null;
    private SpeechTtsListener mSpeechTtsListener;

    public SpeechTts(Context mAppCtx) {
        this.mAppCtx = mAppCtx.getApplicationContext();
        this.mUiHandler = new SpeechTts.UIHandler(Looper.getMainLooper());
        bindService();
    }

    public static SpeechTts createSpeechTts(Context appCtx) {
        if (sInstance == null) {
            byte[] var1 = sLock;
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new SpeechTts(appCtx);
                }
            }
        }
        return sInstance;
    }

    public void bindService() {
        boolean ret = mAppCtx.bindService(new Intent(ISpeechTts.class.getName()), mTTsService, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService " + ISpeechTts.class.getName() + " " + ret);
    }

    private ServiceConnection mTTsService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected " + name);
            mSpeechTts = ISpeechTts.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            mSpeechTts = null;
        }
    };


    public boolean isSpeaking() {
        try {
            if (mSpeechTts != null) {
                return mSpeechTts.isSpeaking();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return false;
    }

    public void pauseSpeaking() {
        try {
            if (mSpeechTts != null) {
                mSpeechTts.pauseSpeaking();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }

    }

    public void resumeSpeaking() {
        try {
            if (mSpeechTts != null) {
                mSpeechTts.resumeSpeaking();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }

    }

    public boolean setParameter(java.lang.String key, java.lang.String value) {

        try {
            if (mSpeechTts != null) {
                return mSpeechTts.setParameter(key, value);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return false;

    }

    public int startSpeaking(java.lang.String text, SpeechTtsListener listener) {

        try {
            mSpeechTtsListener = listener;

            if (mSpeechTts != null) {
                return mSpeechTts.startSpeaking(text, mISpeechTtsListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return 0;
    }


    private ISpeechTtsListener mISpeechTtsListener = new ISpeechTtsListener.Stub() {
        @Override
        public void onSpeakBegin() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_SPEAKBEGIN);
            msg.sendToTarget();
        }

        @Override
        public void onSpeakPaused() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_SPEAKPAUSED);
            msg.sendToTarget();
        }

        @Override
        public void onSpeakProgress(int progress, int beginPos, int endPos) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_SPEAKPROGRESS);
            msg.obj = new Object[]{progress, beginPos, endPos};
            msg.sendToTarget();
        }

        @Override
        public void onSpeakResumed() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_SPEAKRESUMED);
            msg.sendToTarget();
        }

        @Override
        public void onCompleted(int error) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_ONCOMPLETED);
            msg.arg1 = error;
            msg.sendToTarget();
        }

        @Override
        public void onBufferProgress(int progress, int beginPos, int endPos, java.lang.String info) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_ONBUFFERPROGRESS);
            msg.obj = new Object[]{progress, beginPos, endPos, info};
            msg.sendToTarget();
        }
    };

    public void stopSpeaking() {

        try {
            if (mSpeechTts != null) {
                mSpeechTts.stopSpeaking();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    private class UIHandler extends Handler {
        public UIHandler(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SPEAKBEGIN:
                    if (mSpeechTtsListener != null) {
                        mSpeechTtsListener.onSpeakBegin();
                    }
                    break;
                case MSG_SPEAKPAUSED:
                    if (mSpeechTtsListener != null) {
                        mSpeechTtsListener.onSpeakPaused();
                    }
                    break;
                case MSG_SPEAKPROGRESS:
                    if (mSpeechTtsListener != null) {
                        Object[] args = (Object[]) msg.obj;
                        mSpeechTtsListener.onSpeakProgress((int) args[0], (int) args[1], (int) args[2]);
                    }
                    break;
                case MSG_SPEAKRESUMED:
                    if (mSpeechTtsListener != null) {
                        mSpeechTtsListener.onSpeakResumed();
                    }
                    break;
                case MSG_ONCOMPLETED:
                    if (mSpeechTtsListener != null) {
                        mSpeechTtsListener.onCompleted(msg.arg1);
                    }
                    break;
                case MSG_ONBUFFERPROGRESS:
                    if (mSpeechTtsListener != null) {
                        Object[] args = (Object[]) msg.obj;
                        mSpeechTtsListener.onBufferProgress((int) args[0], (int) args[1], (int) args[2], (String) args[3]);
                    }
                    break;
            }
        }
    }

}
