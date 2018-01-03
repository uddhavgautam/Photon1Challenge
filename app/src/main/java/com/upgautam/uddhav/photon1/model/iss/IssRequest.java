package com.upgautam.uddhav.photon1.model.iss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssRequest {

    @SerializedName("altitude")
    @Expose
    private Long altitude;
    @SerializedName("datetime")
    @Expose
    private Long datetime;
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    @SerializedName("passes")
    @Expose
    private Long passes;

    public Long getAltitude() {
        return altitude;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Long getPasses() {
        return passes;
    }

    public void setPasses(Long passes) {
        this.passes = passes;
    }

}
