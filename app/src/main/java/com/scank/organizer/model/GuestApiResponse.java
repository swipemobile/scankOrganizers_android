package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *   Api response model getter/setter class
 */
public class GuestApiResponse extends StaticModel {

    @SerializedName("EventTitle")
    @Expose
    private String eventTitle;
    @SerializedName("StatusId")
    @Expose
    private Integer statusId;
    @SerializedName("TotalGuest")
    @Expose
    private Integer totalGuest;
    @SerializedName("CheckedInGuest")
    @Expose
    private Integer checkedInGuest;
    @SerializedName("CheckedInRatio")
    @Expose
    private float checkedInRatio;
    @SerializedName("IsUnlock")
    @Expose
    private Boolean isUnlock;
    @SerializedName("List")
    @Expose
    private List<GuestData> list = null;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(Integer totalGuest) {
        this.totalGuest = totalGuest;
    }

    public Integer getCheckedInGuest() {
        return checkedInGuest;
    }

    public void setCheckedInGuest(Integer checkedInGuest) {
        this.checkedInGuest = checkedInGuest;
    }

    public int getCheckedInRatio() {
        return (int) checkedInRatio;
    }

    public void setCheckedInRatio(float checkedInRatio) {
        this.checkedInRatio = checkedInRatio;
    }

    public Boolean getIsUnlock() {
        return isUnlock;
    }

    public void setIsUnlock(Boolean isUnlock) {
        this.isUnlock = isUnlock;
    }

    public List<GuestData> getList() {
        return list;
    }

    public void setList(List<GuestData> list) {
        this.list = list;
    }
}
