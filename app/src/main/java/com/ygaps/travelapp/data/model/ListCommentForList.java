package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListCommentForList {
    @SerializedName("commentList")
    @Expose
    private ArrayList<CommentForList> commentForLists;

    public ArrayList<CommentForList> getCommentForLists() {
        return commentForLists;
    }

    public void setCommentForLists(ArrayList<CommentForList> commentForLists) {
        this.commentForLists = commentForLists;
    }
}
