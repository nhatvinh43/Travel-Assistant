package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackSend {
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("point")
    @Expose
    private Integer point;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public FeedbackSend(Integer serviceId, String feedback, Integer point) {
        this.serviceId = serviceId;
        this.feedback = feedback;
        this.point = point;
    }
}
