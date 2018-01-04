package com.shijiwei.xkit.nohttp.youtu.model.add;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by shijiwei on 2017/3/20.
 */
public class AddPersonResult {

    /**
     * person_id : gps01
     * suc_group : 1
     * suc_face : 1
     * session_id :
     * face_id : 1969427359030350006
     * group_ids : ["gpsrobottest"]
     * errorcode : 0
     * errormsg : OK
     */

    @JSONField(name = "person_id")
    private String personId;

    @JSONField(name = "suc_group")
    private int sucGroup;

    @JSONField(name = "suc_face")
    private int sucFace;

    @JSONField(name = "session_id")
    private String sessionId;

    @JSONField(name = "face_id")
    private String faceId;

    private int errorcode;
    private String errormsg;

    @JSONField(name = "group_ids")
    private List<String> groupIds;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getSucGroup() {
        return sucGroup;
    }

    public void setSucGroup(int suc_group) {
        this.sucGroup = suc_group;
    }

    public int getSucFace() {
        return sucFace;
    }

    public void setSucFace(int sucFace) {
        this.sucFace = sucFace;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }
}
