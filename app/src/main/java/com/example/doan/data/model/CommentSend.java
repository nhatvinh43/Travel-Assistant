package com.example.doan.data.model;

public class CommentSend {
    private String tourId,userId,comment;

    public CommentSend(String tourId, String userId, String comment) {
        this.tourId = tourId;
        this.userId = userId;
        this.comment = comment;
    }
    public String getTourId() {
        return tourId;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }
}
