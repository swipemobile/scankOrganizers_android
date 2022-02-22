package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *   Api response model getter/setter class
 */
public class ApiResponse {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("LocationTrackingFrequency")
    @Expose
    private long locationTrackingFrequency;
    @SerializedName("Details")
    @Expose
    private String details;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLocationTrackingFrequency() {
        return locationTrackingFrequency;
    }

    public void setLocationTrackingFrequency(long locationTrackingFrequency) {
        this.locationTrackingFrequency = locationTrackingFrequency;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
