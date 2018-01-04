package com.shijiwei.xkit.nohttp.youtu.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.shijiwei.xkit.app.Constants;

/**
 * Created by shijiwei on 2017/3/20.
 * 腾讯优图人脸基类
 */
public class Face {

    @JSONField(name = "app_id")
    private String appId = Constants.YT_APP_ID;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
