package com.shijiwei.xkit.nohttp.youtu.model.delperson;

import com.alibaba.fastjson.annotation.JSONField;
import com.shijiwei.xkit.nohttp.youtu.model.Face;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class DelPerson extends Face {

    @JSONField(name = "person_id")
    private String personId;
    @JSONField(name = "org_id")
    private String orgId;

    public DelPerson() {
    }


    public DelPerson(String personId, String orgId) {
        this.personId = personId;
        this.orgId = orgId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
