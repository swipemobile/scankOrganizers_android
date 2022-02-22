package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *   Api response model getter/setter class
 */
public class CheckInResponse extends StaticModel {
    @SerializedName("ticketDetails")
    @Expose
    private TicketDetails ticketDetails;


    public TicketDetails getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(TicketDetails ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

}
