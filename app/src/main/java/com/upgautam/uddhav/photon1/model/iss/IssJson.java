package com.upgautam.uddhav.photon1.model.iss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssJson {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("issRequest")
    @Expose
    private IssRequest issRequest;
    @SerializedName("issResponse")
    @Expose
    private List<IssResponse> issResponse = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IssRequest getIssRequest() {
        return issRequest;
    }

    public void setIssRequest(IssRequest issRequest) {
        this.issRequest = issRequest;
    }

    public List<IssResponse> getIssResponse() {
        return issResponse;
    }

    public void setIssResponse(List<IssResponse> issResponse) {
        this.issResponse = issResponse;
    }

}
