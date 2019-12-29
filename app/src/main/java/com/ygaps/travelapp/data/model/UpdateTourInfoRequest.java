package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTourInfoRequest {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startDate")
    @Expose
    private long startDate;
    @SerializedName("endDate")
    @Expose
    private long endDate;
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
    @SerializedName("isPrivate")
    @Expose
    private Boolean isPrivate;
    @SerializedName("status")
    @Expose
    private Integer status;

    public UpdateTourInfoRequest(String id, String name, long startDate, long endDate, Integer adults, Integer childs, Integer minCost, Integer maxCost, Boolean isPrivate, Integer status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.isPrivate = isPrivate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public Integer getAdults() {
        return adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public Integer getMinCost() {
        return minCost;
    }

    public Integer getMaxCost() {
        return maxCost;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Integer getStatus() {
        return status;
    }
}