package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//tour for add tour
public class TourCreate {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("startDate")
    @Expose
    private Integer startDate;

    @SerializedName("endDate")
    @Expose
    private Integer endDate;

    @SerializedName("sourceLat")
    @Expose
    private Double sourceLat;

    @SerializedName("sourceLong")
    @Expose
    private Double sourceLong;

    @SerializedName("desLat")
    @Expose
    private Double desLat;

    @SerializedName("desLong")
    @Expose
    private Double desLong;

    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;

    @SerializedName("adults")
    @Expose
    private Integer adults;

    @SerializedName("childs")
    @Expose
    private Integer childs;

    @SerializedName("minCost")
    @Expose
    private Integer minCost;

    @SerializedName("maxCost")
    @Expose
    private Integer maxCost;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public TourCreate(String name, Integer startDate, Integer endDate,
                      Double sourceLat, Double sourceLong, Double desLat, Double desLong,
                      Boolean isPrivate, Integer adults, Integer childs, Integer minCost, Integer maxCost, String avatar) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(Double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public Double getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(Double sourceLong) {
        this.sourceLong = sourceLong;
    }

    public Double getDesLat() {
        return desLat;
    }

    public void setDesLat(Double desLat) {
        this.desLat = desLat;
    }

    public Double getDesLong() {
        return desLong;
    }

    public void setDesLong(Double desLong) {
        this.desLong = desLong;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
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
