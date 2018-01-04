package com.cityeasy.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.cityeasy.aidl.IInfraredListener;
import com.cityeasy.aidl.ILexiconListener;
import com.cityeasy.aidl.ISpeechAsrListener;
import com.cityeasy.aidl.ITouchPanelListener;
import com.cityeasy.aidl.IVoiceAssistService;
import com.cityeasy.asr.SpeechAsrListener;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by hsx on 2016/9/22.
 */
public class RobotsUtils {

    private static final String TAG = "VoiceService";

    private static final int MSG_REMOTE_VOLUMECHANGE = 1;
    private static final int MSG_REMOTE_BEGINOFSPEECH = 2;
    private static final int MSG_REMOTE_ONENDOFSPEECH = 3;
    private static final int MSG_REMOTE_ONRESULT = 4;
    private static final int MSG_REMOTE_ONERROR = 5;
    private static final int MSG_TOUCHPANEL = 6;
    private static final int MSG_INFRARED = 7;

    private static RobotsUtils sInstance;
    private RobotsUtils.UIHandler mUiHandler;
    private Context mAppCtx;
    IVoiceAssistService mVoiceAssistService = null;

    private static byte[] sLock = new byte[0];

    private TouchPanelListener mTouchPanelListener;
    private InfraredListener mInfraredListener;
    private LexiconListener mLexiconListener;

    public RobotsUtils(Context context) {
        this.mAppCtx = context.getApplicationContext();
        this.mUiHandler = new RobotsUtils.UIHandler(Looper.getMainLooper());
        init();
    }

    public static RobotsUtils getInstance(Context appCtx) {
        if (sInstance == null) {
            byte[] var1 = sLock;
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new RobotsUtils(appCtx);
                }
            }
        }
        return sInstance;
    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }


    public void init() {
        Intent intent = new Intent(IVoiceAssistService.class.getName());
        boolean ret = mAppCtx.bindService(createExplicitFromImplicitIntent(mAppCtx,intent), mService, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "bindService " + IVoiceAssistService.class.getName() + " " + ret);
    }

    private ServiceConnection mService = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i(TAG, "onServiceConnected " + className);
            mVoiceAssistService = IVoiceAssistService.Stub.asInterface(service);
            try {
                if (mVoiceAssistService != null) {
                    mVoiceAssistService.registerTouchPanelListener(mAppCtx.getPackageName(), mITouchPanelListener);
                    mVoiceAssistService.registerInfraredListener(mAppCtx.getPackageName(), mIInfraredListener);
                }
            } catch (RemoteException e) {
                Log.i(TAG, "RemoteException " + e.getCause());
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "onServiceDisconnected");
            try {
                if (mVoiceAssistService != null) {
                    mVoiceAssistService.unregisterTouchPanelListener(mAppCtx.getPackageName(), mITouchPanelListener);
                }
            } catch (RemoteException e) {
                Log.i(TAG, "RemoteException " + e.getCause());
                e.printStackTrace();
            }
            mVoiceAssistService = null;
        }
    };

    public void registerTouchPanelListener(TouchPanelListener listener) {
        mTouchPanelListener = listener;
    }


    public void unregisterTouchPanelListener(TouchPanelListener listener) {
        mTouchPanelListener = null;
        try {
            if (mVoiceAssistService != null) {
                mVoiceAssistService.unregisterTouchPanelListener(mAppCtx.getPackageName(), mITouchPanelListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    public int startListening(SpeechAsrListener speechAsrListener) {
        mSpeechAsrListener = speechAsrListener;
        int ret = -1;
        try {
            if (mVoiceAssistService != null) {
                ret = mVoiceAssistService.startListening(mISpeechAsrListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return ret;
    }


    public void registerAsrResult(SpeechAsrListener speechAsrListener) {
        mSpeechAsrListener = speechAsrListener;
        int ret = -1;
        try {
            if (mVoiceAssistService != null) {
                mVoiceAssistService.registerAsrResult(mISpeechAsrListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return ;
    }


    public void unregisterAsrResult(SpeechAsrListener speechAsrListener) {
        mSpeechAsrListener = speechAsrListener;
        int ret = -1;
        try {
            if (mVoiceAssistService != null) {
                mVoiceAssistService.unregisterAsrResult(mISpeechAsrListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
        return ;
    }

    public void stopListening() {
        try {
            if (mVoiceAssistService != null) {
                Log.e("WakeupReceiver","stopListening");
                mVoiceAssistService.stopListening();
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    public void updateUserWord(String userwork ,LexiconListener listener) {
        mLexiconListener = listener;
        try {
            if (mVoiceAssistService != null) {
                mVoiceAssistService.updateUserWord(userwork, mILexiconListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    private ILexiconListener mILexiconListener = new ILexiconListener.Stub() {
        @Override
        public void onLexiconUpdated(String lexiconId, int errorcode) throws RemoteException {
            if (mLexiconListener != null) {
                mLexiconListener.onLexiconUpdated(lexiconId, errorcode);
            }
        }
    };

    public void registerInfraredListener(InfraredListener listener) {
        mInfraredListener = listener;
    }

    public void unregisterInfraredListener(TouchPanelListener listener) {
        mTouchPanelListener = null;
        try {
            if (mVoiceAssistService != null) {
                mVoiceAssistService.unregisterTouchPanelListener(mAppCtx.getPackageName(), mITouchPanelListener);
            }
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    private ITouchPanelListener mITouchPanelListener = new ITouchPanelListener.Stub() {
        @Override
        public void onTouched() throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_TOUCHPANEL);
            msg.sendToTarget();
        }
    };

    private IInfraredListener mIInfraredListener = new IInfraredListener.Stub() {
        @Override
        public void onInfraredChanged(boolean status) throws RemoteException {
            Message msg = mUiHandler.obtainMessage(MSG_INFRARED);
            msg.obj = status;
            msg.sendToTarget();
        }
    };

    private SpeechAsrListener mSpeechAsrListener;

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
                return ret;
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

    private class UIHandler extends Handler {
        public UIHandler(Looper mainLooper) {
            super(mainLooper);
        }

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_TOUCHPANEL:
                    if (mTouchPanelListener != null) {
                        mTouchPanelListener.onTouched();
                    }
                    break;
                case MSG_INFRARED:
                    if (mInfraredListener != null) {
                        mInfraredListener.onInfraredChanged((boolean) msg.obj);
                    }
                    break;
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

    public void startSpeakAction() {
        try {
            mVoiceAssistService.startSpeakAction();
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    public void startAction(String actionname) {
        try {
            mVoiceAssistService.startAction(actionname);
        } catch (RemoteException e) {
            Log.i(TAG, "RemoteException " + e.getCause());
            e.printStackTrace();
        }
    }

    public void startAction(String actionname, boolean animation) {
        if (TextUtils.isEmpty(actionname)) {
            return;
        }
        try {
            Intent i = new Intent("serial.com.control.action");
            i.putExtra("serial_int_value", 2);
            byte[] b_utf8 = actionname.getBytes("UTF-8");
            i.putExtra("UTFByte", b_utf8);
            i.putExtra("animation", animation);
            mAppCtx.sendBroadcast(i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void startAction(String actionname, boolean animation, boolean playsound) {
        if (TextUtils.isEmpty(actionname)) {
            return;
        }
        try {
            Intent i = new Intent("serial.com.control.action");
            i.putExtra("serial_int_value", 2);
            byte[] b_utf8 = actionname.getBytes("UTF-8");
            i.putExtra("UTFByte", b_utf8);
            i.putExtra("animation", animation);
            i.putExtra("sound", playsound);
            mAppCtx.sendBroadcast(i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void TurnleftHead(Context context) {
        Intent i = new Intent("com.cityeasy.adjust.degree");
        i.putExtra("MotorId", 17);
        i.putExtra("OffsetDegree", -20);
        context.sendBroadcast(i);
    }

    public void TurnRightHead(Context context) {
        Intent i = new Intent("com.cityeasy.adjust.degree");
        i.putExtra("MotorId", 17);
        i.putExtra("OffsetDegree", 20);
        context.sendBroadcast(i);
    }

    public void openSystemVoiceWakeUp() {
        Intent i = new Intent("com.voice.wakeup.switch");
        i.putExtra("switch", true);
        mAppCtx.sendBroadcast(i);
    }

    public void closeSystemVoiceWakeUp() {
        Intent i = new Intent("com.voice.wakeup.switch");
        i.putExtra("switch", false);
        mAppCtx.sendBroadcast(i);
    }

    public void setMotorDegree(int id, int degree) {
        Intent i = new Intent("com.cityeasy.head.action");
        i.putExtra("MotorId", id);
        i.putExtra("ToDegree", degree);
        mAppCtx.sendBroadcast(i);
    }

    public void offsetMotorDegree(int id, int offset) {
        Intent i = new Intent("com.cityeasy.adjust.degree");
        i.putExtra("MotorId", id);
        i.putExtra("OffsetDegree", offset);
        mAppCtx.sendBroadcast(i);
    }


}
