package com.scank.organizer.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *   Api response model getter/setter class
 */
public class LoginResponse extends StaticModel{

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("ProfilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("TicketCount")
    @Expose
    private Integer ticketCount;
    @SerializedName("SavedCount")
    @Expose
    private Integer savedCount;
    @SerializedName("isSocial")
    @Expose
    private Boolean isSocial;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("State")


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Integer getSavedCount() {
        return savedCount;
    }

    public void setSavedCount(Integer savedCount) {
        this.savedCount = savedCount;
    }

    public Boolean getIsSocial() {
        return isSocial;
    }

    public void setIsSocial(Boolean isSocial) {
        this.isSocial = isSocial;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
