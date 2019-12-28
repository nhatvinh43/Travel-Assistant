package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("tourId")
    @Expose
    private String tourId;

    @SerializedName("userId")
    @Expose
    private String userId  ;

    @SerializedName("noti")
    @Expose
    private String noti;

    public Notification(String tourId, String userId, String noti) {
        this.tourId = tourId;
        this.userId = userId;
        this.noti = noti;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoti() {
        return noti;
    }

    public void setNoti(String noti) {
        this.noti = noti;
    }
}
