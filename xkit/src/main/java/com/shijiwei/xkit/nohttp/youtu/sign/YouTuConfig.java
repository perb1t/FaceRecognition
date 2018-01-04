package com.shijiwei.xkit.nohttp.youtu.sign;

/**
 * Created by shijiwei on 2017/3/20.
 * 腾讯优图
 */
public class YouTuConfig {

    /**
     * 平台绑定信息
     */
    public static final String YT_APP_ID = "10078408";
    public static final String YT_SECRET_ID = "AKIDGWdbO2FD5lJ0ejDMSRWmT5a1MQG4OMgt";
    public static final String YT_SECRET_KEY = "GPNH67FGAwvDMOhqsjLpCUi8f1omeLqA";

    /**
     * 域名与路由
     */
    private static final String YT_URL = "http://api.youtu.qq.com/youtu/";
    private static final String DETECT = "api/detectface";
    private static final String IDENTIFY = "api/faceidentify";
    private static final String ADDPERSON = "api/newperson";
    private static final String DELPERSON = "api/delperson";
    private static final String SETINFO = "api/setinfo";

    public static String getDetectFaceURL() {
        return YT_URL + DETECT;
    }

    public static String getIdentifyFaceURL() {
        return YT_URL + IDENTIFY;
    }

    public static String getAddPersonURL() {
        return YT_URL + ADDPERSON;
    }

    public static String getDelPersonURL() {
        return YT_URL + DELPERSON;
    }

    public static String getSetInformationURL() {
        return YT_URL + SETINFO;
    }

}
