package com.scank.organizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetails implements Parcelable {

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

    public TicketDetails() {

    }

    protected TicketDetails(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        ticket = in.readString();
        if (in.readByte() == 0) {
            orderNumber = null;
        } else {
            orderNumber = in.readInt();
        }
        name = in.readString();
        email = in.readString();
        byte tmpCheckIn = in.readByte();
        checkIn = tmpCheckIn == 0 ? null : tmpCheckIn == 1;
        byte tmpRefund = in.readByte();
        refund = tmpRefund == 0 ? null : tmpRefund == 1;
        lastUpdateDate = in.readString();
    }

    public static final Creator<TicketDetails> CREATOR = new Creator<TicketDetails>() {
        @Override
        public TicketDetails createFromParcel(Parcel in) {
            return new TicketDetails(in);
        }

        @Override
        public TicketDetails[] newArray(int size) {
            return new TicketDetails[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(ticket);
        if (orderNumber == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(orderNumber);
        }
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeByte((byte) (checkIn == null ? 0 : checkIn ? 1 : 2));
        parcel.writeByte((byte) (refund == null ? 0 : refund ? 1 : 2));
        parcel.writeString(lastUpdateDate);
    }
}