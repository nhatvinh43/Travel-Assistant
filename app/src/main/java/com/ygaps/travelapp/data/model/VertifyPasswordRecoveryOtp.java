package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VertifyPasswordRecoveryOtp {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;
    @SerializedName("verifyCode")
    @Expose
    private String verifyCode;

    public VertifyPasswordRecoveryOtp(Integer userId, String newPassword, String verifyCode) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.verifyCode = verifyCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
