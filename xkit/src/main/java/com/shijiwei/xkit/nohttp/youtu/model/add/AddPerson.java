package com.shijiwei.xkit.nohttp.youtu.model.add;

import com.alibaba.fastjson.annotation.JSONField;
import com.shijiwei.xkit.nohttp.youtu.model.Face;

/**
 * Created by shijiwei on 2017/3/20.
 */
public class AddPerson extends Face {

    @JSONField(name = "group_ids")
    private String[] groupIds;

    @JSONField(name = "person_id")
    private String personId;

    private String image;

    @JSONField(name = "person_name")
    private String personName;

    private String tag;

    public AddPerson() {
    }

    public AddPerson(String[] groupIds, String personId, String image, String personName, String tag) {
        this.groupIds = groupIds;
        this.personId = personId;
        this.image = image;
        this.personName = personName;
        this.tag = tag;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
