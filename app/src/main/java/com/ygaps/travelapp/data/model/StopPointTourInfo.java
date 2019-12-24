package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StopPointTourInfo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("arrivalAt")
    @Expose
    private String arrivalAt;
    @SerializedName("leaveAt")
    @Expose
    private String leaveAt;
    @SerializedName("minCost")
    @Expose
    private String minCost;
    @SerializedName("maxCost")
    @Expose
    private String maxCost;
    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("index")
    @Expose
    private Integer index;

    public StopPointTourInfo() {
    }

    public StopPointTourInfo(Integer id, Integer serviceId, String address,
                             Integer provinceId, String name, String lat, String _long,
                             String arrivalAt, String leaveAt, String minCost, String maxCost,
                             Integer serviceTypeId, String avatar, Integer index) {
        this.id = id;
        this.serviceId = serviceId;
        this.address = address;
        this.provinceId = provinceId;
        this.name = name;
        this.lat = lat;
        this._long = _long;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.serviceTypeId = serviceTypeId;
        this.avatar = avatar;
        this.index = index;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(String arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public String getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(String leaveAt) {
        this.leaveAt = leaveAt;
    }

    public String getMinCost() {
        return minCost;
    }

    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
