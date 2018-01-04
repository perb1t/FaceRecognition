package com.hazhirobot.facerecognition.global;

import com.shijiwei.xkit.app.XKitApplication;
import com.shijiwei.xkit.utility.data.sp.SPUtility;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class App extends XKitApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Constants.WELCOME_WORD = new SPUtility(this, SPUtility.HZ_ROTOT_FACE_CONFIG).getString(SPUtility.WELCOME_WORD, "你好，XXX");
    }
}
