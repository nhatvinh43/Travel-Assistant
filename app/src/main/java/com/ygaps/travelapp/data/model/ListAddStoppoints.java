package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
//this class for API add stop point to tour when creating a tour.
public class ListAddStoppoints {
    @SerializedName("tourID")
    @Expose
    private int tourId;

    @SerializedName("stopPoints")
    @Expose
    private ArrayList<StopPoint> stopPoints;

    @SerializedName("deleteIds")
    @Expose
    private ArrayList<Integer> deleteIds;




}
