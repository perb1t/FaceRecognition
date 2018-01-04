package com.shijiwei.xkit.nohttp.youtu.model.delperson;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by shijiwei on 2018/1/2.
 *
 * @VERSION 1.0
 */

public class DelPersonResult {


    /**
     * deleted : 1
     * person_id : person0
     * errorcode : 0
     * session_id : session_id
     * errormsg : ok
     */

    private int deleted;
    @JSONField(name = "person_id")
    private String personId;
    private int errorcode;
    @JSONField(name = "session_id")
    private String sessionId;
    private String errormsg;

    public DelPersonResult() {
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
