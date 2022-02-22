package com.scank.organizer.model;

import android.os.Build;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *   Api response model getter/setter class
 */
public class LoginRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("UdId")
    @Expose
    private String UdId;

    @SerializedName("DeviceCode")
    @Expose
    private String DeviceCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUdId() {
        return UdId;
    }

    public void setUdId(String udId) {
        UdId = udId;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }
}

