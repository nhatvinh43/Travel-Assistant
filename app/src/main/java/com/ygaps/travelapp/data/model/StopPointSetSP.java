package com.ygaps.travelapp.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//this Stop Point for Add Stop Point To Tour in @Body Req /tour/set-stop-point
public class StopPointSetSP {
    //if add a new Stoppoint id && serviceid: null
    //if add a exist StopPoint: id null, and serviceId is id of that Stoppoint
    //if update stoppoint: id is id of func getStopPoint
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("serviceId")
    @Expose
    private String serviceId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("provinceId")
    @Expose
    private Integer privinceId;

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("long")
    @Expose
    private Double _long;

    @SerializedName("arrivalAt")
    @Expose
    private Long arrivalAt;

    @SerializedName("leaveAt")
    @Expose
    private Long leaveAt;

    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;

    @SerializedName("minCost")
    @Expose
    private Integer minCost;

    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public StopPointSetSP(){
        this.id = "";
        this.serviceId = "";
        this.name = "";
        this.privinceId = 1;
        this.lat = 0.0;
        this._long = 0.0;
        this.arrivalAt = Long.valueOf(0);
        this.leaveAt = Long.valueOf(0);
        this.serviceTypeId = 1;
        this.avatar = "";
        this.address = "";
        this.minCost = 0 ;
        this.maxCost = 0;
    }

    public StopPointSetSP(String id, String serviceId, String name, Integer privinceId, Double lat,
                          Double _long, Long arrivalAt, Long leaveAt, Integer serviceTypeId,
                          Integer minCost, Integer maxCost, String address, String avatar) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.privinceId = privinceId;
        this.lat = lat;
        this._long = _long;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.address = address;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }

    public Long getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(Long arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public Long getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Long leaveAt) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
