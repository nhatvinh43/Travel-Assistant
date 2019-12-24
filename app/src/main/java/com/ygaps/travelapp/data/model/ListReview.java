package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListReview {
    @SerializedName("reviewList")
    @Expose
    private ArrayList<Review> reviewList;

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public ListReview(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
