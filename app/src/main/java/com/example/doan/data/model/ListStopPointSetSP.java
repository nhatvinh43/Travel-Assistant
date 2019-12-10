package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListStopPointSetSP {
    @SerializedName("tourId")
    @Expose
    private String tourId;

    @SerializedName("stopPoints")
    @Expose
    private ArrayList<StopPointSetSP> stopPointSetSPS = null;

    @SerializedName("deleteIds")// to save List ID of Stop Points to Delete
    @Expose
    private ArrayList<Integer> deleteIds = null;

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public ArrayList<StopPointSetSP> getStopPointSetSPS() {
        return stopPointSetSPS;
    }

    public void setStopPointSetSPS(ArrayList<StopPointSetSP> stopPointSetSPS) {
        this.stopPointSetSPS = stopPointSetSPS;
    }

    public ArrayList<Integer> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(ArrayList<Integer> deleteIds) {
        this.deleteIds = deleteIds;
    }
}

