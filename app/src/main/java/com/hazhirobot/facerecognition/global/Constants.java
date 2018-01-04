package com.hazhirobot.facerecognition.global;

import com.hazhirobot.facerecognition.modle.Org;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijiwei on 2017/11/6.
 *
 * @VERSION 1.0
 */

public class Constants {

    public static final int ACTION_DETECT_FACE = 100;
    public static final int ACTION_ADD_PERSON = 101;
    public static final int ACTION_IDENTIFY_FACE = 102;
    public static final int ACTION_DEL_PERSON = 103;
    public static final int ACTION_SET_INFO = 104;


    public static final int N_RESULT_CODE_SUCCESS = 0;

    public static final int N_ACTION_HZ_GET_ORG_LIST = 200;
    public static final int N_ACTION_HZ_ADD_FACE = 201;
    public static final int N_ACTION_HZ_IDENTIFY_FACE = 202;
    public static final int N_ACTION_HZ_SIGB_IN = 203;
    public static final int N_ACTION_HZ_SIGB_IN_RECORD = 204;
    public static final int N_ACTION_HZ_USER_LIST = 205;
    public static final int N_ACTION_HZ_UPDATE_USER = 206;
    public static final int N_ACTION_HZ_DELETE_USER = 207;
    public static final int N_ACTION_HZ_USER_LIST_BY_IDS = 207;

    //deprecated
    public static final String FACE_GROUP_ID = "hazhi_robot_group_001";
    public static final String PERSON_FACE_HEADER = "hz_person_";
    //deprecated

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public static List<Org> ORG_SET = new ArrayList<>();
    public static Org ORG = new Org("杭州哈智机器人", "hz_group_1234567890", "hz_", true);

    public static String WELCOME_WORD = "你好，XXX";
    public static boolean inInitialVoiceService = true;

    public static boolean isRequstedPermission = false;
    public static boolean isInitialSuccess = false;

//    static {
//        ORG_SET.add(new Org("杭州哈智机器人有限公司", "hazhi_robot_group_002", "hz_person_", true));
//        ORG_SET.add(new Org("22222", "hazhi_robot_group_003", "hz_person_", false));
//        ORG_SET.add(new Org("33333", "hazhi_robot_group_004", "hz_person_", false));
//        ORG = ORG_SET.get(0);
//    }


}
