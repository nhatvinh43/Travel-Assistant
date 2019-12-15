package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoreOneCoordinate {
    @SerializedName("hasOneCoordinate")
    @Expose
    private Boolean hasOneCoordinate;

    @SerializedName("coordList")
    @Expose
    private ArrayList<CoordinateSet> coordinateSetArrayList;


    public MoreOneCoordinate(Boolean hasOneCoordinate, ArrayList<CoordinateSet> coordinateSetArrayList) {
        this.hasOneCoordinate = hasOneCoordinate;
        this.coordinateSetArrayList = coordinateSetArrayList;
    }

    public Boolean getHasOneCoordinate() {
        return hasOneCoordinate;
    }

    public void setHasOneCoordinate(Boolean hasOneCoordinate) {
        this.hasOneCoordinate = hasOneCoordinate;
    }

    public ArrayList<CoordinateSet> getCoordinateSetArrayList() {
        return coordinateSetArrayList;
    }

    public void setCoordinateSetArrayList(ArrayList<CoordinateSet> coordinateSetArrayList) {
        this.coordinateSetArrayList = coordinateSetArrayList;
    }
}
