package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewSend {
    @SerializedName("tourId")
    @Expose
    private Integer tourId;

    @SerializedName("point")
    @Expose
    private Integer point;

    @SerializedName("review")
    @Expose
    private String review;

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public ReviewSend(Integer tourId, Integer point, String review) {
        this.tourId = tourId;
        this.point = point;
        this.review = review;
    }
}
