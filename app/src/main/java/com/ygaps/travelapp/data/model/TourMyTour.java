package com.ygaps.travelapp.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Tour for MyTour
public class TourMyTour {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private String minCost;
    @SerializedName("maxCost")
    @Expose
    private String maxCost;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("adults")
    @Expose
    private Integer adults;
    @SerializedName("childs")
    @Expose
    private Integer childs;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("isHost")
    @Expose
    private Boolean isHost;
    @SerializedName("isKicked")
    @Expose
    private Boolean isKicked;

    public TourMyTour() {
    }

    public TourMyTour(Integer id, String hostId, Integer status, String minCost, String maxCost,
                      String startDate, String endDate, Integer adults, Integer childs, String avatar, boolean isHost, boolean isKicked){
        this.id = id;
        this.hostId = hostId;
        this.status = status;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.avatar = avatar;
        this.isHost = isHost;
        this.isKicked = isKicked;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(Boolean isHost) {
        this.isHost = isHost;
    }

    public Boolean getIsKicked() {
        return isKicked;
    }

    public void setIsKicked(Boolean isKicked) {
        this.isKicked = isKicked;
    }
}
