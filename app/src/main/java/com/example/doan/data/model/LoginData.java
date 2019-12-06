package com.example.doan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("emailPhone")
    @Expose
    private String emailPhone;
    @SerializedName("password")
    @Expose
    private String password;

    public LoginData(){
        this.emailPhone = "";
        this.password = "";
    }

    public LoginData(String emailPhone, String password){
        this.emailPhone = emailPhone;
        this.password = password;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
