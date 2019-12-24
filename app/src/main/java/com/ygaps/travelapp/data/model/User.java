package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("emailPhone")
    @Expose
    private String emailPhone;

    @SerializedName("password")
    @Expose
    private String password;

    public User(String emailPhone, String password) {
        this.emailPhone = emailPhone;
        this.password = password;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
