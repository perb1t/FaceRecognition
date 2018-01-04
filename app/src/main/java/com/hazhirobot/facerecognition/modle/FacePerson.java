package com.hazhirobot.facerecognition.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by shijiwei on 2017/12/19.
 *
 * @VERSION 1.0
 */

public class FacePerson implements Parcelable {

    private String company;
    @JSONField(name = "person_name")
    private String name;
    private String sex;
    @JSONField(name = "occ")
    private String occupation;
    private String phone;
    private long signInTime;
    private String org;

    @JSONField(name = "face_id")
    private String faceId;
    @JSONField(name = "person_id")
    private String personId;
    @JSONField(name = "org_id")
    private String orgId;

    public FacePerson() {
    }

    public FacePerson(String compamy, String name, String sex, String occupation, String phone, long signInTime, String org) {
        this.company = compamy;
        this.name = name;
        this.sex = sex;
        this.occupation = occupation;
        this.phone = phone;
        this.signInTime = signInTime;
        this.org = org;
    }



    public FacePerson clear() {
        setCompany("");
        setName("");
        setSex("");
        setOccupation("");
        setPhone("");
        setSignInTime(0);
        setOrg("");
        setFaceId("");
        setOrgId("");
        setPersonId("");
        return this;
    }


    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String compamy) {
        this.company = compamy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(long signInTime) {
        this.signInTime = signInTime;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.company);
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.occupation);
        dest.writeString(this.phone);
        dest.writeLong(this.signInTime);
        dest.writeString(this.org);
        dest.writeString(this.faceId);
        dest.writeString(this.personId);
        dest.writeString(this.orgId);
    }

    protected FacePerson(Parcel in) {
        this.company = in.readString();
        this.name = in.readString();
        this.sex = in.readString();
        this.occupation = in.readString();
        this.phone = in.readString();
        this.signInTime = in.readLong();
        this.org = in.readString();
        this.faceId = in.readString();
        this.personId = in.readString();
        this.orgId = in.readString();
    }

    public static final Parcelable.Creator<FacePerson> CREATOR = new Parcelable.Creator<FacePerson>() {
        @Override
        public FacePerson createFromParcel(Parcel source) {
            return new FacePerson(source);
        }

        @Override
        public FacePerson[] newArray(int size) {
            return new FacePerson[size];
        }
    };
}
