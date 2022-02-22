package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventGuestDetail extends RealmObject {
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("StartDateStr")
    @Expose
    private String startDateStr;
    @SerializedName("EndDateStr")
    @Expose
    private String endDateStr;
    @SerializedName("Sales")
    @Expose
    private String sales;
    @SerializedName("TicketCount")
    @Expose
    private String ticketCount;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("CoverPicture")
    @Expose
    private String coverPicture;
    @SerializedName("Video")
    @Expose
    private String video;
    @SerializedName("NameOfUser")
    @Expose
    private String nameOfUser;
    @SerializedName("IsFavourite")
    @Expose
    private Boolean isFavourite;
    @SerializedName("MapLat")
    @Expose
    private String mapLat;
    @SerializedName("MapLng")
    @Expose
    private String mapLng;
    @SerializedName("EventARStatus")
    @Expose
    private String eventARStatus;
    @SerializedName("EventARStatusId")
    @Expose
    private Integer eventARStatusId;
   /* @SerializedName("Features")
    @Expose
    private RealmList<String> features = null;*/
/*    @SerializedName("CoverPictures")
    @Expose
    private RealmList<String> coverPictures = null;*/
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Venue")
    @Expose
    private String venue;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Postcode")
    @Expose
    private String postcode;
    @SerializedName("ManageUrl")
    @Expose
    private String manageUrl;
    @SerializedName("ReportUrl")
    @Expose
    private String reportUrl;
  /*  @SerializedName("RestrictionList")
    @Expose
    private RealmList<Restriction> restrictionList = null;*/

    @PrimaryKey
    @SerializedName("EventId")
    @Expose
    private Integer eventId;
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
    private Float checkedInRatio;
    @SerializedName("IsUnlock")
    @Expose
    private Boolean isUnlock;
    @SerializedName("List")
    @Expose
    private RealmList<TicketList> list = null;

    public EventGuestDetail(){} // RealmObject subclasses must provide an empty constructor

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

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

    public float getCheckedInRatio() {
        return checkedInRatio;
    }

    public void setCheckedInRatio(Float checkedInRatio) {
        this.checkedInRatio = checkedInRatio;
    }

    public Boolean getIsUnlock() {
        return isUnlock;
    }

    public void setIsUnlock(Boolean isUnlock) {
        this.isUnlock = isUnlock;
    }

    public RealmList<TicketList> getList() {
        return list;
    }

    public void setList(RealmList<TicketList> list) {
        this.list = list;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getManageUrl() {
        return manageUrl;
    }

    public void setManageUrl(String manageUrl) {
        this.manageUrl = manageUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}