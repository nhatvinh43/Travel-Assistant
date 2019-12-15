package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
//List tour for topTour /tour/list
public class ListTour {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tours")
    @Expose
    private ArrayList<Tour> tours;

    public ListTour() {
        total = "";
        tours = new ArrayList<>();
    }

    public ListTour(String total, ArrayList<Tour> tours) {
        this.total = total;
        this.tours = tours;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Tour> getTours() {
        return tours;
    }

    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }
}
