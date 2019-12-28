package com.ygaps.travelapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterFCM {
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("platform")
    @Expose
    private Integer platform;

    @SerializedName("appVersion")
    @Expose
    private String appVersion;

    public RegisterFCM(String fcmToken, String deviceId, Integer platform, String appVersion) {
        this.fcmToken = fcmToken;
        this.deviceId = deviceId;
        this.platform = platform;
        this.appVersion = appVersion;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
