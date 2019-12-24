package com.ygaps.travelapp.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//this Stop Point for Add Stop Point To Tour in @Body Req /tour/set-stop-point
public class StopPointSetSP {
    @SerializedName("id") //id of Stop Point Optional. If exist is update, or not add new stop point
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("provinceId")
    @Expose
    private Integer privinceId;

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

    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;

    @SerializedName("minCost")
    @Expose
    private Integer minCost;

    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public StopPointSetSP(String id, String name, Integer privinceId, Integer lat,
                          Integer _long, Integer arrivalAt, Integer leaveAt, Integer serviceTypeId,
                          Integer minCost, Integer maxCost, String avatar) {
        this.id = id;
        this.name = name;
        this.privinceId = privinceId;
        this.lat = lat;
        this._long = _long;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrivinceId() {
        return privinceId;
    }

    public void setPrivinceId(Integer privinceId) {
        this.privinceId = privinceId;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer get_long() {
        return _long;
    }

    public void set_long(Integer _long) {
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

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
