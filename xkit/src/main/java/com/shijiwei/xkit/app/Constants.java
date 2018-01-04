package com.shijiwei.xkit.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/2/20.
 * 常量管理
 */
public class Constants {

    /**
     * 常用字段
     */
    public static final String API_KEY = "api_key";
    public static final String API_SECRET = "api_secret";
    public static final String IMAGE_FILE = "image_file";
    public static final String DISPLAY_NAME = "display_name";
    public static final String OUTER_ID = "outer_id";
    public static final String RETURN_RESULT_COUNT = "return_result_count";
    public static final String TAGS = "tags";
    public static final String FACE_TOKENS = "face_tokens";
    public static final String USER_DATA = "user_data";
    public static final String FORCE_MERGE = "force_merge";

    /**
     * ACTION
     */

    public static final int ACTION_FACE_DETECT = 101;
    public static final int ACTION_CREATE_FACESET = 102;
    public static final int ACTION_ADD_FACE_TO_FACESET = 103;
    public static final int ACTION_SEARCH_FACE_FROM_FACESET = 104;
    public static final int ACTION_BIND_ID_TO_FACETOKEN = 105;

    /**
     * 全局变量
     */
    /* face++ 密钥 */
    public static final String FACE_API_KEY = "Qa6zyq4qxbr6IFmPhnBxA8g1og7b7quO";
    public static final String FACE_API_SECRET = "rXur221qNKEE94G986Uct2MoVkUoCrZG";

    /**
     * 腾讯优图
     */

    public static final String YT_APP_ID = "10078408";
    public static final String YT_SECRET_ID = "AKIDGWdbO2FD5lJ0ejDMSRWmT5a1MQG4OMgt";
    public static final String YT_SECRET_KEY = "GPNH67FGAwvDMOhqsjLpCUi8f1omeLqA";


    /**
     * 科大讯飞语音库AppId
     */
    public static final String IFLYTEK_APP_ID = "58af8edb";

    /**
     * 是否退出了人机语义交流
     */
    public static boolean IS_EXIT_VOICE = true;

    public static boolean USE_HTTPS_VERIFY = false;
    public static final int SERVER_CONNECT_TIME_OUT = 30 * 1000;
    /* 单位 KB */
    public static final int IMAGE_COMPRESS_SIZE = 100;

    /**
     * 系统检测到usb摄像头集合
     */
    public static List<String> USB_CAMERA_SET = new ArrayList<>();

}
