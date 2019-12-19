package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//this comment is used for TourInfo`
public class Comment {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("avatar")
    @Expose
    private String avatar;


    public Comment(){
        this.userId = 0;
        this.avatar ="";
        this.name = "";
        this.comment="";
    }

    public Comment(Integer userId, String name, String comment, String avatar) {
        this.userId = userId;
        this.name = name;
        this.comment = comment;
        this.avatar = avatar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
