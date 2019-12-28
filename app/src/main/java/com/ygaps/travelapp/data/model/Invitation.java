package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invitation {
    @SerializedName("tourId")
    @Expose
    private String tourId;
    @SerializedName("invitedUserId")
    @Expose
    private String invitedUserId;
    @SerializedName("isInvited")
    @Expose
    private Boolean isInvated;

    public Invitation(String tourId, String invitedUserId, Boolean isInvated) {
        this.tourId = tourId;
        this.invitedUserId = invitedUserId;
        this.isInvated = isInvated;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getInvitedUserId() {
        return invitedUserId;
    }

    public void setInvitedUserId(String invitedUserId) {
        this.invitedUserId = invitedUserId;
    }

    public Boolean getInvated() {
        return isInvated;
    }

    public void setInvated(Boolean invated) {
        isInvated = invated;
    }
}
