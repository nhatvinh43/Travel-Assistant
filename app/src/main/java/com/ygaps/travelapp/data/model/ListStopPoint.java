package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

///this StopPoint for Get History Destination of a User /tour/get/suggested-destination-list
public class ListStopPoint {
    @SerializedName("stopPoints")
    @Expose
    private ArrayList<StopPoint> stopPoints = null;

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }
}
