package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationOnRoad {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("long")
    @Expose
    private Double lng;
    @SerializedName("tourId")
    @Expose
    private Integer tourId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("notificationType")
    @Expose
    private Integer notificationType;
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("note")
    @Expose
    private String note;

    public NotificationOnRoad(Double lat, Double lng, Integer tourId, Integer userId,
                              Integer notificationType, Integer speed, String note) {
        this.lat = lat;
        this.lng = lng;
        this.tourId = tourId;
        this.userId = userId;
        this.notificationType = notificationType;
        this.speed = speed;
        this.note = note;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(Integer notificationType) {
        this.notificationType = notificationType;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
