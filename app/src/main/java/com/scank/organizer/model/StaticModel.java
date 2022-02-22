package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 29.08.2020.
 */

public class StaticModel {

    @SerializedName("State")
    @Expose
    private Boolean state;
    @SerializedName("StateCode")
    @Expose
    private Integer stateCode;
    @SerializedName("Message")
    @Expose
    private List<String> message = null;

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

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
