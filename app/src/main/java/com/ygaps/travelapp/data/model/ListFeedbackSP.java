package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListFeedbackSP {
    @SerializedName("feedbackList")
    @Expose
    private ArrayList<FeedbackSP> feedbackList;

    public ListFeedbackSP(ArrayList<FeedbackSP> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public ArrayList<FeedbackSP> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(ArrayList<FeedbackSP> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
