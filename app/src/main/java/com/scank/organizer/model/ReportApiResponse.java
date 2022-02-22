package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *   Api response model getter/setter class
 */
public class ReportApiResponse extends StaticModel {

    @SerializedName("EventSalesDetails")
    @Expose
    private EventSalesDetails eventSalesDetails;
    @SerializedName("TicketSalesDetails")
    @Expose
    private List<TicketSalesDetail> ticketSalesDetails = null;
    @SerializedName("State")
    @Expose
    private Boolean state;
    @SerializedName("StateCode")
    @Expose
    private Integer stateCode;
    @SerializedName("Message")
    @Expose
    private List<Object> message = null;

    public EventSalesDetails getEventSalesDetails() {
        return eventSalesDetails;
    }

    public void setEventSalesDetails(EventSalesDetails eventSalesDetails) {
        this.eventSalesDetails = eventSalesDetails;
    }

    public List<TicketSalesDetail> getTicketSalesDetails() {
        return ticketSalesDetails;
    }

    public void setTicketSalesDetails(List<TicketSalesDetail> ticketSalesDetails) {
        this.ticketSalesDetails = ticketSalesDetails;
    }
}
