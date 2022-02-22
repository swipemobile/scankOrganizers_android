package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitTicketResponse  {

    @SerializedName("TotalCounts")
    @Expose
    private Integer totalCounts;
    @SerializedName("SuccessCounts")
    @Expose
    private Integer successCounts;
    @SerializedName("FailedCounts")
    @Expose
    private Integer failedCounts;
    @SerializedName("State")
    @Expose
    private Boolean state;
    @SerializedName("StateCode")
    @Expose
    private Integer stateCode;
    @SerializedName("Message")
    @Expose
    private List<Object> message = null;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(Integer successCounts) {
        this.successCounts = successCounts;
    }

    public Integer getFailedCounts() {
        return failedCounts;
    }

    public void setFailedCounts(Integer failedCounts) {
        this.failedCounts = failedCounts;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public List<Object> getMessage() {
        return message;
    }

    public void setMessage(List<Object> message) {
        this.message = message;
    }
}