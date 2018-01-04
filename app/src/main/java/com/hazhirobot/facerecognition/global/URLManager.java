package com.hazhirobot.facerecognition.global;

/**
 * Created by shijiwei on 2017/12/29.
 *
 * @VERSION 1.0
 */

public class URLManager {

    private static final String HZ_HOST = "http://47.97.98.96:16537/commuter/";

    /*组织列表*/
    private static final String ROUTE_ORG_LIST = "list";
    /*组织下的人员列表*/
    private static final String ROUTE_USER_LIST = "userlist";
    /*添加人脸*/
    private static final String ROUTE_ADD_FACE = "addFace";
    /*签到打卡*/
    private static final String ROUTE_SIGN_IN = "signin";
    /*打卡记录*/
    private static final String ROUTE_SIGN_IN_RECORD = "signinList";
    /*更新人脸信息*/
    private static final String ROUTE_UPDATE_USER = "updateUser";
    /*删除人脸信息*/
    private static final String ROUTE_DELETE_USER = "deleteUser";
    /*根绝 personIds 获取对应列表*/
    private static final String ROUTE_USER_LIST_BY_IDS = "userListByPersons";

    public static String getOrgListURL() {
        return HZ_HOST + ROUTE_ORG_LIST;
    }

    public static String getAddFaceURL() {
        return HZ_HOST + ROUTE_ADD_FACE;
    }

    public static String getSignInURL() {
        return HZ_HOST + ROUTE_SIGN_IN;
    }

    public static String getRouteSignInRecordURL() {
        return HZ_HOST + ROUTE_SIGN_IN_RECORD;
    }

    public static String getUserList() {
        return HZ_HOST + ROUTE_USER_LIST;
    }

    public static String getUpdateUserURL() {
        return HZ_HOST + ROUTE_UPDATE_USER;
    }

    public static String getDeleteUserURL() {
        return HZ_HOST + ROUTE_DELETE_USER;
    }

    public static String getUserListByIdsURL() {
        return HZ_HOST + ROUTE_USER_LIST_BY_IDS;
    }

}
