package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestData {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Ticket")
    @Expose
    private String ticket;
    @SerializedName("OrderNumber")
    @Expose
    private Integer orderNumber;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CheckIn")
    @Expose
    private Boolean checkIn;
    @SerializedName("Refund")
    @Expose
    private Boolean refund;
    @SerializedName("LastUpdateDate")
    @Expose
    private String lastUpdateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Boolean getRefund() {
        return refund;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
