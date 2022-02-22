package com.scank.organizer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventSalesDetails {

    @SerializedName("totalTickets")
    @Expose
    private Integer totalTickets;
    @SerializedName("totalSoldTickets")
    @Expose
    private Integer totalSoldTickets;
    @SerializedName("sellingRate")
    @Expose
    private Double sellingRate;
    @SerializedName("soldInDay")
    @Expose
    private Integer soldInDay;
    @SerializedName("soldInWeek")
    @Expose
    private Integer soldInWeek;
    @SerializedName("soldInMonth")
    @Expose
    private Integer soldInMonth;
    @SerializedName("totalGuest")
    @Expose
    private Integer totalGuest;
    @SerializedName("checkedInGuest")
    @Expose
    private Integer checkedInGuest;
    @SerializedName("checkInRate")
    @Expose
    private Double checkInRate;
    @SerializedName("currentSales")
    @Expose
    private Double currentSales;
    @SerializedName("expectedSales")
    @Expose
    private Double expectedSales;
    @SerializedName("sellingRation")
    @Expose
    private Double sellingRation;
    @SerializedName("refundNo")
    @Expose
    private Integer refundNo;
    @SerializedName("totalRefundAmount")
    @Expose
    private Double totalRefundAmount;
    @SerializedName("scanViews")
    @Expose
    private Integer scanViews;
    @SerializedName("viewInDay")
    @Expose
    private Integer viewInDay;
    @SerializedName("viewInWeek")
    @Expose
    private Integer viewInWeek;
    @SerializedName("viewInMonth")
    @Expose
    private Integer viewInMonth;
    @SerializedName("TotalGrossSales")
    @Expose
    private Double totalGrossSales;
    @SerializedName("TotalNetAmount")
    @Expose
    private Double totalNetAmount;
    @SerializedName("TotalPayableAmount")
    @Expose
    private Double totalPayableAmount;
    @SerializedName("GrossSalesInDay")
    @Expose
    private Double grossSalesInDay;
    @SerializedName("NetAmountInDay")
    @Expose
    private Double netAmountInDay;
    @SerializedName("GrossSalesInWeek")
    @Expose
    private Double grossSalesInWeek;
    @SerializedName("NetAmountInWeek")
    @Expose
    private Double netAmountInWeek;
    @SerializedName("GrossSalesInMonth")
    @Expose
    private Double grossSalesInMonth;
    @SerializedName("NetAmountInMonth")
    @Expose
    private Double netAmountInMonth;
    @SerializedName("DiscountAmount")
    @Expose
    private Double discountAmount;
    @SerializedName("VoucherType")
    @Expose
    private String voucherType;
    @SerializedName("VoucherCode")
    @Expose
    private String voucherCode;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getTotalSoldTickets() {
        return totalSoldTickets;
    }

    public void setTotalSoldTickets(Integer totalSoldTickets) {
        this.totalSoldTickets = totalSoldTickets;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public Integer getSoldInDay() {
        return soldInDay;
    }

    public void setSoldInDay(Integer soldInDay) {
        this.soldInDay = soldInDay;
    }

    public Integer getSoldInWeek() {
        return soldInWeek;
    }

    public void setSoldInWeek(Integer soldInWeek) {
        this.soldInWeek = soldInWeek;
    }

    public Integer getSoldInMonth() {
        return soldInMonth;
    }

    public void setSoldInMonth(Integer soldInMonth) {
        this.soldInMonth = soldInMonth;
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

    public Double getCheckInRate() {
        return checkInRate;
    }

    public void setCheckInRate(Double checkInRate) {
        this.checkInRate = checkInRate;
    }

    public Double getCurrentSales() {
        return currentSales;
    }

    public void setCurrentSales(Double currentSales) {
        this.currentSales = currentSales;
    }

    public Double getExpectedSales() {
        return expectedSales;
    }

    public void setExpectedSales(Double expectedSales) {
        this.expectedSales = expectedSales;
    }

    public Double getSellingRation() {
        return sellingRation;
    }

    public void setSellingRation(Double sellingRation) {
        this.sellingRation = sellingRation;
    }

    public Integer getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(Integer refundNo) {
        this.refundNo = refundNo;
    }

    public Double getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Double totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public Integer getScanViews() {
        return scanViews;
    }

    public void setScanViews(Integer scanViews) {
        this.scanViews = scanViews;
    }

    public Integer getViewInDay() {
        return viewInDay;
    }

    public void setViewInDay(Integer viewInDay) {
        this.viewInDay = viewInDay;
    }

    public Integer getViewInWeek() {
        return viewInWeek;
    }

    public void setViewInWeek(Integer viewInWeek) {
        this.viewInWeek = viewInWeek;
    }

    public Integer getViewInMonth() {
        return viewInMonth;
    }

    public void setViewInMonth(Integer viewInMonth) {
        this.viewInMonth = viewInMonth;
    }

    public Double getTotalGrossSales() {
        return totalGrossSales;
    }

    public void setTotalGrossSales(Double totalGrossSales) {
        this.totalGrossSales = totalGrossSales;
    }

    public Double getTotalNetAmount() {
        return totalNetAmount;
    }

    public void setTotalNetAmount(Double totalNetAmount) {
        this.totalNetAmount = totalNetAmount;
    }

    public Double getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(Double totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public Double getGrossSalesInDay() {
        return grossSalesInDay;
    }

    public void setGrossSalesInDay(Double grossSalesInDay) {
        this.grossSalesInDay = grossSalesInDay;
    }

    public Double getNetAmountInDay() {
        return netAmountInDay;
    }

    public void setNetAmountInDay(Double netAmountInDay) {
        this.netAmountInDay = netAmountInDay;
    }

    public Double getGrossSalesInWeek() {
        return grossSalesInWeek;
    }

    public void setGrossSalesInWeek(Double grossSalesInWeek) {
        this.grossSalesInWeek = grossSalesInWeek;
    }

    public Double getNetAmountInWeek() {
        return netAmountInWeek;
    }

    public void setNetAmountInWeek(Double netAmountInWeek) {
        this.netAmountInWeek = netAmountInWeek;
    }

    public Double getGrossSalesInMonth() {
        return grossSalesInMonth;
    }

    public void setGrossSalesInMonth(Double grossSalesInMonth) {
        this.grossSalesInMonth = grossSalesInMonth;
    }

    public Double getNetAmountInMonth() {
        return netAmountInMonth;
    }

    public void setNetAmountInMonth(Double netAmountInMonth) {
        this.netAmountInMonth = netAmountInMonth;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
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

}