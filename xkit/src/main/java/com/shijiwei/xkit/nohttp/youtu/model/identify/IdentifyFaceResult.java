package com.shijiwei.xkit.nohttp.youtu.model.identify;

/**
 * Created by shijiwei on 2017/11/6.
 *
 * @VERSION 1.0
 */


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Auto-generated: 2017-11-06 18:26:37
 */
public class IdentifyFaceResult {

    @JSONField(name = "candidates")
    private List<Person> persons;
    private int errorcode;
    @JSONField(name = "session_id")
    private String sessionId;
    private String errormsg;
    @JSONField(name = "group_size")
    private int groupSize;
    @JSONField(name = "time_ms")
    private int timeMs;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setTimeMs(int timeMs) {
        this.timeMs = timeMs;
    }

    public int getTimeMs() {
        return timeMs;
    }

    public static class Person {

        @JSONField(name = "person_id")
        private String personId;
        private String tag;
        @JSONField(name = "face_id")
        private String faceId;
        private int confidence;

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getPersonId() {
            return personId;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }

        public int getConfidence() {
            return confidence;
        }

    }
}
