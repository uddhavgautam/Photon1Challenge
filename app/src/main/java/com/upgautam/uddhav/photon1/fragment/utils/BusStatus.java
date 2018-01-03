package com.upgautam.uddhav.photon1.fragment.utils;

/**
 * Created by Administrator on 12/23/2017.
 */

public class BusStatus { //to main the OttoBus status

    private int status;

    public BusStatus(int s) { //constructor working as a setter
        status = s;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
