package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("Token")
    @Expose
    private long token;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("User_ID")
    @Expose
    private Integer userID;

    @SerializedName("FName")
    @Expose
    private String FName;

    @SerializedName("LName")
    @Expose
    private String LName;

    @SerializedName("Mobile")
    @Expose
    private String Mobile;

    @SerializedName("EMail")
    @Expose
    private String EMail;

    @SerializedName("ProfilePicturePath")
    @Expose
    private String ProfilePicturePath;

    @SerializedName("AuthenticationResponse")
    @Expose
    private AuthenticationResponse authenticationResponse;

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public AuthenticationResponse getAuthenticationResponse() {
        return authenticationResponse;
    }

    public void setAuthenticationResponse(AuthenticationResponse authenticationResponse) {
        this.authenticationResponse = authenticationResponse;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getProfilePicturePath() {
        return ProfilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        ProfilePicturePath = profilePicturePath;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }
}