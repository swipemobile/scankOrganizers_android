package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketSalesDetail {

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("TotalTickets")
    @Expose
    private String totalTickets;
    @SerializedName("TotalSoldTickets")
    @Expose
    private String totalSoldTickets;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("SellingRatio")
    @Expose
    private Double sellingRatio;
    @SerializedName("currentSales")
    @Expose
    private String currentSales;
    @SerializedName("expectedSales")
    @Expose
    private String expectedSales;
    @SerializedName("soldInDay")
    @Expose
    private String soldInDay;
    @SerializedName("TotalCheckedIn")
    @Expose
    private String totalCheckedIn;
    @SerializedName("CheckedInRatio")
    @Expose
    private Double checkedInRatio;
    @SerializedName("pendingSales")
    @Expose
    private String pendingSales;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(String totalTickets) {
        this.totalTickets = totalTickets;
    }

    public String getTotalSoldTickets() {
        return totalSoldTickets;
    }

    public void setTotalSoldTickets(String totalSoldTickets) {
        this.totalSoldTickets = totalSoldTickets;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getSellingRatio() {
        return sellingRatio;
    }

    public void setSellingRatio(Double sellingRatio) {
        this.sellingRatio = sellingRatio;
    }

    public String getCurrentSales() {
        return currentSales;
    }

    public void setCurrentSales(String currentSales) {
        this.currentSales = currentSales;
    }

    public String getExpectedSales() {
        return expectedSales;
    }

    public void setExpectedSales(String expectedSales) {
        this.expectedSales = expectedSales;
    }

    public String getSoldInDay() {
        return soldInDay;
    }

    public void setSoldInDay(String soldInDay) {
        this.soldInDay = soldInDay;
    }

    public String getTotalCheckedIn() {
        return totalCheckedIn;
    }

    public void setTotalCheckedIn(String totalCheckedIn) {
        this.totalCheckedIn = totalCheckedIn;
    }

    public Double getCheckedInRatio() {
        return checkedInRatio;
    }

    public void setCheckedInRatio(Double checkedInRatio) {
        this.checkedInRatio = checkedInRatio;
    }

    public String getPendingSales() {
        return pendingSales;
    }

    public void setPendingSales(String pendingSales) {
        this.pendingSales = pendingSales;
    }

}