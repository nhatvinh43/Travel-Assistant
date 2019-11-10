package com.example.doan.data.model;

import androidx.annotation.NonNull;

public class Tour {
    private String id, status, name, minCost, maxCost, startDate, endDate, adults, childs, isPrivate, avatar;
    public Tour(String id, String status, String name, String minCost, String maxCost, String startDate, String endDate,
                String adults, String childs, String isPrivate, String avatar){
        this.id = id;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate ;
        this.adults = adults;
        this.childs = childs;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
    }

    public String getAdults() {
        return adults;
    }

    public String getChilds() {
        return childs;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public String getMinCost() {
        return minCost;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

