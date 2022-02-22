package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventTicketResponse extends StaticModel {

    @SerializedName("EventGuestDetails")
    @Expose
    private List<EventGuestDetail> eventGuestDetails = null;


    public List<EventGuestDetail> getEventGuestDetails() {
        return eventGuestDetails;
    }

    public void setEventGuestDetails(List<EventGuestDetail> eventGuestDetails) {
        this.eventGuestDetails = eventGuestDetails;
    }

}