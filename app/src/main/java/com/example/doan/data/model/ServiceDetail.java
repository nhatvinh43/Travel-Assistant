package com.example.doan.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//this class is Detail of a Stop Point
public class ServiceDetail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("provinceId")
    @Expose
    private Integer provinceId;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("selfStarRatings")
    @Expose
    private Object selfStarRatings;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
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
    private Object avatar;
    @SerializedName("landingTimes")
    @Expose
    private Integer landingTimes;

    public ServiceDetail(Integer id, String name, String address,
                         Integer provinceId, String contact, Object selfStarRatings, String lat,
                         String _long, String minCost, String maxCost, Integer serviceTypeId, Object avatar, Integer landingTimes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.contact = contact;
        this.selfStarRatings = selfStarRatings;
        this.lat = lat;
        this._long = _long;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.serviceTypeId = serviceTypeId;
        this.avatar = avatar;
        this.landingTimes = landingTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Object getSelfStarRatings() {
        return selfStarRatings;
    }

    public void setSelfStarRatings(Object selfStarRatings) {
        this.selfStarRatings = selfStarRatings;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
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

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public Integer getLandingTimes() {
        return landingTimes;
    }

    public void setLandingTimes(Integer landingTimes) {
        this.landingTimes = landingTimes;
    }
}
