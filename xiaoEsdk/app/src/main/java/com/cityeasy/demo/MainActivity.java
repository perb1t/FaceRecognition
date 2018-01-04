package com.cityeasy.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.cityeasy.asr.SpeechArs;
import com.cityeasy.asr.SpeechAsrListener;
import com.cityeasy.tts.SpeechTts;
import com.cityeasy.tts.SpeechTtsListener;
import com.cityeasy.common.*;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SpeechTts";
    private RobotsUtils mRobotsUtils = null;
    private SpeechTts mSpeechTts;
    private SpeechArs mSpeechArs;
    private Button button;
    private EditText et;
    private EditText et_ars;
    private Toast mToast;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        mRobotsUtils = RobotsUtils.getInstance(this);
        mSpeechTts = SpeechTts.createSpeechTts(this);
        mSpeechArs = SpeechArs.createSpeechArs(this);
        button = (Button) findViewById(R.id.bt_speak);
        button.setOnClickListener(this);
        et = (EditText) findViewById(R.id.et_text);
        et_ars = (EditText) findViewById(R.id.et_ars);
        ((Button) findViewById(R.id.bt_talkaction)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_action)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_led)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_ars)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_headleft)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_headright)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_ledstop)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_wakeupopen)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_wakeupclose)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_offset)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_offset2)).setOnClickListener(this);
        ((Button) findViewById(R.id.bt_offset3)).setOnClickListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRobotsUtils.setMotorDegree(16, seekBar.getProgress() * 2);
            }
        });
        mRobotsUtils.registerTouchPanelListener(new TouchPanelListener() {
            @Override
            public void onTouched() {
                Log.i(TAG, "onTouched");
                Toast.makeText(MainActivity.this, "onTouched", Toast.LENGTH_SHORT).show();
            }
        });

        mRobotsUtils.registerInfraredListener(new InfraredListener() {
            @Override
            public void onInfraredChanged(boolean status) {
                Log.i(TAG, "onInfraredChanged " + status);
                Toast.makeText(MainActivity.this, "onInfraredChanged " + status, Toast.LENGTH_SHORT).show();
            }
        });

        mRobotsUtils.registerAsrResult(new SpeechAsrListener() {
            @Override
            public void onVolumeChanged(int var1) {
                Log.i(TAG, "onVolumeChanged " + var1);
            }

            @Override
            public void onBeginOfSpeech() {
                Log.i(TAG, "onBeginOfSpeech ");
            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public boolean onResult(String result) {

                Log.i(TAG, "onResult " + result);
                Message msg = handler.obtainMessage(1);
                msg.obj = result;
                msg.sendToTarget();

                return false;
            }

            @Override
            public void onError(int var1) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_speak:
                mSpeechTts.startSpeaking(et.getText().toString(), new SpeechTtsListener() {
                    @Override
                    public void onSpeakBegin() {
                        Log.i(TAG, "onSpeakBegin");
                    }

                    @Override
                    public void onBufferProgress(int progress, int beginPos, int endPos, String info) {
                        showTip("合成进度" + progress);
                    }

                    @Override
                    public void onSpeakPaused() {
                        Log.i(TAG, "onSpeakPaused");
                    }

                    @Override
                    public void onSpeakResumed() {
                        Log.i(TAG, "onSpeakResumed");
                    }

                    @Override
                    public void onSpeakProgress(int progress, int beginPos, int endPos) {
                        Log.i(TAG, "onSpeakProgress " + progress);
                        showTip("说话进度" + progress);
                    }

                    @Override
                    public void onCompleted(int var1) {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onEvent(int var1, int var2, int var3, Bundle var4) {
                        Log.i(TAG, "onEvent");
                    }
                });
                break;
            case R.id.bt_talkaction:
                mRobotsUtils.startSpeakAction();
                break;
            case R.id.bt_action:
                //mRobotsUtils.startAction("骑马舞");
                mRobotsUtils.startAction("骑马舞", false, false);
                break;
            case R.id.bt_headleft:
                mRobotsUtils.TurnleftHead(this);
                break;
            case R.id.bt_headright:
                mRobotsUtils.TurnRightHead(this);
                break;
            case R.id.bt_led:
                LedCotrol.startLedEye(this, 2, 2);
                break;
            case R.id.bt_ledstop:
//                LedCotrol.stopLedEye(this);
                Log.e("=======", "ssssssstop!");
                mRobotsUtils.stopListening();
                break;
            case R.id.bt_ars:
                mRobotsUtils.startListening(new SpeechAsrListener() {
                    @Override
                    public void onVolumeChanged(int var1) {
                        Log.i(TAG, "onVolumeChanged " + var1);
                        showTip("音量" + var1);
                    }

                    @Override
                    public void onBeginOfSpeech() {
                        Log.i(TAG, "onBeginOfSpeech ");
                    }

                    @Override
                    public void onEndOfSpeech() {
                        Log.i(TAG, "onEndOfSpeech ");
                    }

                    @Override
                    public boolean onResult(String result) {
                        Log.i(TAG, "onResult " + result);
                        Message msg = handler.obtainMessage(1);
                        msg.obj = result;
                        msg.sendToTarget();
                        return false;
                    }

                    @Override
                    public void onError(int var1) {
                        Log.i(TAG, "onError " + var1);
                    }
                });
                break;
            case R.id.bt_wakeupopen:
                mRobotsUtils.openSystemVoiceWakeUp();
                break;
            case R.id.bt_wakeupclose:
                mRobotsUtils.closeSystemVoiceWakeUp();
                break;
            case R.id.bt_offset:
                mRobotsUtils.offsetMotorDegree(16, -16);
                break;
            case R.id.bt_offset2:
                mRobotsUtils.offsetMotorDegree(16, 16);
                break;
            case R.id.bt_offset3:
                StringBuffer mLocalLexicon = new StringBuffer();
                mLocalLexicon.append("咪咔");
                mLocalLexicon.append('\n');
                mLocalLexicon.append("漫迪");
                mLocalLexicon.append('\n');
                Log.i(TAG, "词典更新 ");
                mRobotsUtils.updateUserWord(mLocalLexicon.toString(), new LexiconListener() {
                    @Override
                    public void onLexiconUpdated(String lexiconId, int errorcode) {
                        if (errorcode == 0) {
                            Log.i(TAG, "词典更新成功 " + lexiconId);
                        } else {
                            Log.e(TAG, "词典更新失败," + lexiconId + " 错误码：" + errorcode);
                        }
                    }
                });
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    if (result != null) {
                        et_ars.setText(result);
                    }
                    break;
            }
        }
    };


    private void showTip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }
}
