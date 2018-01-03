package com.upgautam.uddhav.photon1.model;

//model class

public class IssData {

    private String mDuration;
    private String mTime;

    public IssData(String mDuration, String mTime) {
        this.mDuration = mDuration;
        this.mTime = mTime;
    }

    public IssData() {
    }

    public String getmDuration() {
        return mDuration;
    }

    public void setmDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
