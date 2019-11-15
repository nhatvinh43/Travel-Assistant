package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopPoint {
    @SerializedName("tourId")
    @Expose
    private Integer tourId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("long")
    @Expose
    private Integer _long;
    @SerializedName("arrivalAt")
    @Expose
    private Integer arrivalAt;
    @SerializedName("leaveAt")
    @Expose
    private Integer leaveAt;
    @SerializedName("minCost")
    @Expose
    private Integer minCost;
    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;
    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("avatar")
    @Expose
    private String avatar;


    public StopPoint(){}

    public StopPoint(Integer tourId, String name, Integer lat, Integer _long, Integer arrivalAt, Integer leaveAt,
                     Integer minCost, Integer maxCost, Integer serviceTypeId, Integer id, String avatar){
        this.tourId = tourId;
        this.name = name;
        this.lat = lat;
        this._long = _long;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.serviceTypeId = serviceTypeId;
        this.id = id;
        this.avatar = avatar;
    }
    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLong() {
        return _long;
    }

    public void setLong(Integer _long) {
        this._long = _long;
    }

    public Integer getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(Integer arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public Integer getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Integer leaveAt) {
        this.leaveAt = leaveAt;
    }

    public Integer getMinCost() {
        return minCost;
    }

    public void setMinCost(Integer minCost) {
        this.minCost = minCost;
    }

    public Integer getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(Integer maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
