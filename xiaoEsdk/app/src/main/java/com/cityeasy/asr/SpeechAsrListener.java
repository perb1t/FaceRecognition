package com.cityeasy.asr;

/**
 * Created by hsx on 2016/10/13.
 */
public interface SpeechAsrListener {

        void onVolumeChanged(int var1);

        void onBeginOfSpeech();

        void onEndOfSpeech();

        boolean onResult(String result);

        void onError(int var1);
}
