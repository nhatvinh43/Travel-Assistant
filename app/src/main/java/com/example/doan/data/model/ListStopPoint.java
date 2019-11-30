package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListStopPoint {
    @SerializedName("stopPoints")
    @Expose
    private ArrayList<StopPoint> stopPoints = null;

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }
}
