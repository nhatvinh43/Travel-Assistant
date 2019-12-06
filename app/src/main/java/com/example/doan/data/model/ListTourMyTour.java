package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListTourMyTour {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tours")
    @Expose
    private ArrayList<TourMyTour> tours = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<TourMyTour> getTours() {
        return tours;
    }

    public void setTours(ArrayList<TourMyTour> tours) {
        this.tours = tours;
    }
}
