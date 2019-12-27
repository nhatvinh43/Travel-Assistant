package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourResFromTourCreate {
    @SerializedName("hostId")
    @Expose
    private String hostId;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("minCost")
    @Expose
    private Integer minCost;

    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;

    @SerializedName("startDate")
    @Expose
    private Long startDate;

    @SerializedName("endDate")
    @Expose
    private Long endDate;

    @SerializedName("adults")
    @Expose
    private Integer adults;

    @SerializedName("childs")
    @Expose
    private Integer childs;

    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("createdOn")
    @Expose
    private Long createdOn;

    @SerializedName("id")
    @Expose
    private String id;

    public TourResFromTourCreate(String hostId, Integer status, String name, Integer minCost,
                                 Integer maxCost, Long startDate, Long endDate, Integer adults, Integer childs,
                                 Boolean isPrivate, String avatar, Long createdOn, String id) {
        this.hostId = hostId;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
        this.createdOn = createdOn;
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
