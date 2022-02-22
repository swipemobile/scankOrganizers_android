package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *   Api response model getter/setter class
 */
public class EventsResponse extends StaticModel {

    @SerializedName("List")
    @Expose
    private List<EventData> list = null;

    public List<EventData> getList() {
        return list;
    }

    public void setList(List<EventData> list) {
        this.list = list;
    }
}
