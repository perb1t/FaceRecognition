package com.shijiwei.xkit.nohttp.youtu.model.identify;

import com.alibaba.fastjson.annotation.JSONField;
import com.shijiwei.xkit.nohttp.youtu.model.Face;

/**
 * Created by shijiwei on 2017/3/20.
 */
public class IdentifyFace extends Face {

    private String image;

    @JSONField(name = "group_id")
    private String groupId;

    public IdentifyFace() {
    }

    public IdentifyFace(String image, String groupId) {
        this.image = image;
        this.groupId = groupId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
