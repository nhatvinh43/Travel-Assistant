package com.example.doan.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListTour {
    @SerializedName("total")
    public String total;
    @SerializedName("Tours")
    public ArrayList<Tour> data;
}
