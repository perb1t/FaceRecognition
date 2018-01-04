package com.cityeasy.asr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import com.cityeasy.aidl.ISpeechAsr;
import com.cityeasy.aidl.ISpeechAsrListener;

/**
 * Created by hsx on 2016/10/13.
 */
public class SpeechArs {

    private static final String TAG = "SpeechArs";

    private static final int MSG_REMOTE_VOLUMECHANGE = 1;
    private static final int MSG_REMOTE_BEGINOFSPEECH = 2;
    private static final int MSG_REMOTE_ONENDOFSPEECH = 3;
    private static final int MSG_REMOTE_ONRESULT = 4;
    private static final int MSG_REMOTE_ONERROR = 5;

    private static SpeechArs sInstance;
    private static Context mAppCtx;
    private static byte[] sLock = new byte[0];
    private SpeechArs.UIHandler mUiHandler;
    ISpeechAsr mSpeechAsr = null;
    private SpeechAsrListener mSpeechAsrListener;

    public SpeechArs(Context mAppCtx) {
        this.mAppCtx = mAppCtx.getApplicationContext();
        this.mUiHandler = new SpeechArs.UIHandler(Looper.getMainLooper());
        bindService();
    }

    public static SpeechArs createSpeechArs(Context appCtx) {
        if (sInstance == null) {
            byte[] var1 = sLock;
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new SpeechArs(appCtx);
                }
            }
        }
        return sInstance;
    }

    public void bindService() {
        boolean ret = mAppCtx.bindService(new Intent(ISpeechAsr.class.getName()), mAsrService, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService " + ISpeechAsr.class.getName() + " " + ret);
    }

    private ServiceConnection mAsrService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected " + name);
            mSpeechAsr = ISpeechAsr.Stub.asInterface(service);
            if (mSpeechAsr == null) {
                Log.i(TAG, "onServiceConnected  fail" + name);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            mSpeechAsr = null;
        }
    };

    public int startListening(SpeechAsrListener listener) {
        mSpeechAsrListener = listener;
        try {
            if (mSpeechAsr != null) {
                return mSpeechAsr.startListening(mISpeechAsrListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return 0;
    }


    private ISpeechAsrListener mISpeechAsrListener = new ISpeechAsrListener.Stub() {
        @Override
        public void onVolumeChanged(int var1) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_REMOTE_VOLUMECHANGE);
            msg.arg1 = var1;
            msg.sendToTarget();
        }

        @Override
        public void onBeginOfSpeech() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_REMOTE_BEGINOFSPEECH);
            msg.sendToTarget();
        }

        @Override
        public void onEndOfSpeech() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_REMOTE_ONENDOFSPEECH);
            msg.sendToTarget();
        }

        @Override
        public boolean onResult(String result) throws RemoteException {
           /* Message msg = mUiHandler.obtainMessage(MSG_REMOTE_ONRESULT);
            msg.obj = result;
            msg.sendToTarget();*/
            if (mSpeechAsrListener != null) {
                boolean ret = mSpeechAsrListener.onResult(result);
            }
            return false;
        }

        @Override
        public void onError(int var1) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_REMOTE_ONERROR);
            msg.arg1 = var1;
            msg.sendToTarget();
        }
    };

    public String getParameter(String var1) {
        try {
            if (mSpeechAsr != null) {
                return mSpeechAsr.getParameter(var1);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return "";
    }


    public boolean setParameter(String key, String value) {
        try {
            if (mSpeechAsr != null) {
                return mSpeechAsr.setParameter(key, value);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        Log.i(TAG, "Remote error");
        return false;
    }

    public boolean isListening() {

        try {
            if (mSpeechAsr != null) {
                return mSpeechAsr.isListening();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return false;
    }

    public void cancel() {
        try {
            if (mSpeechAsr != null) {
                mSpeechAsr.cancel();
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
                case MSG_REMOTE_VOLUMECHANGE:
                    if (mSpeechAsrListener != null) {
                        mSpeechAsrListener.onVolumeChanged(msg.arg1);
                    }
                    break;
                case MSG_REMOTE_BEGINOFSPEECH:
                    if (mSpeechAsrListener != null) {
                        mSpeechAsrListener.onBeginOfSpeech();
                    }
                    break;
                case MSG_REMOTE_ONENDOFSPEECH:
                    if (mSpeechAsrListener != null) {
                        mSpeechAsrListener.onEndOfSpeech();
                    }
                    break;
                case MSG_REMOTE_ONRESULT:
                    if (mSpeechAsrListener != null) {
                        mSpeechAsrListener.onResult((String) msg.obj);
                    }
                    break;
                case MSG_REMOTE_ONERROR:
                    if (mSpeechAsrListener != null) {
                        mSpeechAsrListener.onError(msg.arg1);
                    }
                    break;
            }
        }
    }

}
