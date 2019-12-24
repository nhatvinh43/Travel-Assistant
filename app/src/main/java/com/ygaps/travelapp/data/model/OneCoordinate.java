package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// description for that class....
public class OneCoordinate {
    @SerializedName("hasOneCoordinate")
    @Expose
    private Boolean hasOneCoordinate;

    @SerializedName("coordList")
    @Expose
    private Coordinate coordList;

    public OneCoordinate(Boolean hasOneCoordinate, Coordinate coordList) {
        this.hasOneCoordinate = hasOneCoordinate;
        this.coordList = coordList;
    }

    public Boolean getHasOneCoordinate() {
        return hasOneCoordinate;
    }

    public void setHasOneCoordinate(Boolean hasOneCoordinate) {
        this.hasOneCoordinate = hasOneCoordinate;
    }

    public Coordinate getCoordList() {
        return coordList;
    }

    public void setCoordList(Coordinate coordList) {
        this.coordList = coordList;
    }
}
