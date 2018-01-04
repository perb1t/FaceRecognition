package com.cityeasy.tts;

import android.os.Bundle;

/**
 * Created by hsx on 2016/10/11.
 */
public interface SpeechTtsListener {

    void onSpeakBegin();

    void onBufferProgress(int progress, int beginPos, int endPos, java.lang.String info);

    void onSpeakPaused();

    void onSpeakResumed();

    void onSpeakProgress(int var1, int var2, int var3);

    void onCompleted(int var1);

    void onEvent(int var1, int var2, int var3, Bundle var4);
}
