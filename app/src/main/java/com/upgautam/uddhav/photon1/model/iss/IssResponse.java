package com.upgautam.uddhav.photon1.model.iss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssResponse {

    @SerializedName("duration")
    @Expose
    private Long duration;
    @SerializedName("risetime")
    @Expose
    private Long risetime;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getRisetime() {
        return risetime;
    }

    public void setRisetime(Long risetime) {
        this.risetime = risetime;
    }

}
