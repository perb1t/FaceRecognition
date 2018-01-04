package com.hazhirobot.facerecognition.modle;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by shijiwei on 2017/12/25.
 *
 * @VERSION 1.0
 */

public class Org {

    @JSONField(name = "org_name")
    private String name;
    @JSONField(name = "org_id")
    private String id;
    private String personHeader;
    private boolean isChecked;

    public Org() {
    }

    public Org(String name, String id, String personHeader, boolean isChecked) {
        this.name = name;
        this.id = id;
        this.personHeader = personHeader;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonHeader() {
        return personHeader;
    }

    public void setPersonHeader(String personHeader) {
        this.personHeader = personHeader;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
