// IVoiceAssistService.aidl
package com.cityeasy.aidl;

// Declare any non-default types here with import statements
import com.cityeasy.aidl.ITouchPanelListener;
import com.cityeasy.aidl.IInfraredListener;
import com.cityeasy.aidl.ISpeechAsrListener;
import com.cityeasy.aidl.ILexiconListener;

interface IVoiceAssistService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void startSpeakAction();
    void startAction(String actionname);
    void registerTouchPanelListener(String packageName,ITouchPanelListener listener);
    void unregisterTouchPanelListener(String packageName,ITouchPanelListener listener);
    void registerInfraredListener(String packageName,IInfraredListener listener);
    void unregisterInraredListener(String packageName,IInfraredListener listener);
    boolean isVoiceActivityRunning();
    int startListening(ISpeechAsrListener listener);
    void registerAsrResult(ISpeechAsrListener listener);
    void unregisterAsrResult(ISpeechAsrListener listener);
    void stopListening();
    void updateUserWord(String userwork,ILexiconListener listener);
}
