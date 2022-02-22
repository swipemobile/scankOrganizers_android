package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *   Api response model getter/setter class
 */
public class TicketRequest {

    @SerializedName("CheckedInTicketId")
    @Expose
    private String CheckedInTicketId;

    public String getCheckedInTicketId() {
        return CheckedInTicketId;
    }

    public void setCheckedInTicketId(String checkedInTicketId) {
        CheckedInTicketId = checkedInTicketId;
    }
}

