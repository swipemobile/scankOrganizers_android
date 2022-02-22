package com.scank.organizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class EventData implements Parcelable{

    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("StatusId")
    @Expose
    private Integer statusId;
    @SerializedName("Hidden")
    @Expose
    private Boolean hidden;
    @SerializedName("Sales")
    @Expose
    private String sales;
    @SerializedName("TicketCount")
    @Expose
    private String ticketCount;
    @SerializedName("SellingRatio")
    @Expose
    private Double sellingRatio;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Price")
    @Expose
    private Double price;
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
    @SerializedName("IsDraft")
    @Expose
    private Boolean isDraft;
    @SerializedName("MapLat")
    @Expose
    private Object mapLat;
    @SerializedName("MapLng")
    @Expose
    private Object mapLng;
    @SerializedName("EventARStatusId")
    @Expose
    private Integer eventARStatusId;
    @SerializedName("IsUnlock")
    @Expose
    private Boolean isUnlock;
    @SerializedName("Features")
    @Expose
    private java.util.List<Object> features = null;
    @SerializedName("CoverPictures")
    @Expose
    private java.util.List<Object> coverPictures = null;
    @SerializedName("Venue")
    @Expose
    private String venue;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Postcode")
    @Expose
    private Object postcode;
    @SerializedName("CheckedIn")
    @Expose
    private Integer checkedIn;
    @SerializedName("TotalAttendees")
    @Expose
    private Integer totalAttendees;
    @SerializedName("ManageUrl")
    @Expose
    private String manageUrl;

    @SerializedName("ReportUrl")
    @Expose
    private String reportUrl;

    @SerializedName("RestrictionList")
    @Expose
    private java.util.List<Object> restrictionList = null;

    RealmList<TicketList> ticketList;

    private int totalGuest;
    private int checkedInGuest;
    private float checkedInRatio;

    protected EventData(Parcel in) {
        picture = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        currency = in.readString();
        if (in.readByte() == 0) {
            statusId = null;
        } else {
            statusId = in.readInt();
        }
        byte tmpHidden = in.readByte();
        hidden = tmpHidden == 0 ? null : tmpHidden == 1;
        sales = in.readString();
        ticketCount = in.readString();
        if (in.readByte() == 0) {
            sellingRatio = null;
        } else {
            sellingRatio = in.readDouble();
        }
        address = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        startDate = in.readString();
        endDate = in.readString();
        startDateStr = in.readString();
        endDateStr = in.readString();
        coverPicture = in.readString();
        video = in.readString();
        nameOfUser = in.readString();
        byte tmpIsFavourite = in.readByte();
        isFavourite = tmpIsFavourite == 0 ? null : tmpIsFavourite == 1;
        byte tmpIsDraft = in.readByte();
        isDraft = tmpIsDraft == 0 ? null : tmpIsDraft == 1;
        if (in.readByte() == 0) {
            eventARStatusId = null;
        } else {
            eventARStatusId = in.readInt();
        }
        byte tmpIsUnlock = in.readByte();
        isUnlock = tmpIsUnlock == 0 ? null : tmpIsUnlock == 1;
        venue = in.readString();
        city = in.readString();
        if (in.readByte() == 0) {
            checkedIn = null;
        } else {
            checkedIn = in.readInt();
        }
        if (in.readByte() == 0) {
            totalAttendees = null;
        } else {
            totalAttendees = in.readInt();
        }
        manageUrl = in.readString();
        reportUrl = in.readString();
        totalGuest = in.readInt();
        checkedInGuest = in.readInt();
        checkedInRatio = in.readFloat();
    }

    public static final Creator<EventData> CREATOR = new Creator<EventData>() {
        @Override
        public EventData createFromParcel(Parcel in) {
            return new EventData(in);
        }

        @Override
        public EventData[] newArray(int size) {
            return new EventData[size];
        }
    };

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public RealmList<TicketList> getTicketList() {
        return ticketList;
    }

    public void setTicketList(RealmList<TicketList> ticketList) {
        this.ticketList = ticketList;
    }

    public EventData() {

    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
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

    public Double getSellingRatio() {
        return sellingRatio;
    }

    public void setSellingRatio(Double sellingRatio) {
        this.sellingRatio = sellingRatio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Object getMapLat() {
        return mapLat;
    }

    public void setMapLat(Object mapLat) {
        this.mapLat = mapLat;
    }

    public Object getMapLng() {
        return mapLng;
    }

    public void setMapLng(Object mapLng) {
        this.mapLng = mapLng;
    }

    public Integer getEventARStatusId() {
        return eventARStatusId;
    }

    public void setEventARStatusId(Integer eventARStatusId) {
        this.eventARStatusId = eventARStatusId;
    }

    public Boolean getIsUnlock() {
        return isUnlock;
    }

    public void setIsUnlock(Boolean isUnlock) {
        this.isUnlock = isUnlock;
    }

    public java.util.List<Object> getFeatures() {
        return features;
    }

    public void setFeatures(java.util.List<Object> features) {
        this.features = features;
    }

    public java.util.List<Object> getCoverPictures() {
        return coverPictures;
    }

    public void setCoverPictures(java.util.List<Object> coverPictures) {
        this.coverPictures = coverPictures;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getPostcode() {
        return postcode;
    }

    public void setPostcode(Object postcode) {
        this.postcode = postcode;
    }

    public Integer getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Integer checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Integer getTotalAttendees() {
        return totalAttendees;
    }

    public void setTotalAttendees(Integer totalAttendees) {
        this.totalAttendees = totalAttendees;
    }

    public String getManageUrl() {
        return manageUrl;
    }

    public void setManageUrl(String manageUrl) {
        this.manageUrl = manageUrl;
    }

    public java.util.List<Object> getRestrictionList() {
        return restrictionList;
    }

    public void setRestrictionList(java.util.List<Object> restrictionList) {
        this.restrictionList = restrictionList;
    }

    public int getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(int totalGuest) {
        this.totalGuest = totalGuest;
    }

    public int getCheckedInGuest() {
        return checkedInGuest;
    }

    public void setCheckedInGuest(int checkedInGuest) {
        this.checkedInGuest = checkedInGuest;
    }

    public float getCheckedInRatio() {
        return checkedInRatio;
    }

    public void setCheckedInRatio(float checkedInRatio) {
        this.checkedInRatio = checkedInRatio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(picture);
        parcel.writeString(title);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(currency);
        if (statusId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(statusId);
        }
        parcel.writeByte((byte) (hidden == null ? 0 : hidden ? 1 : 2));
        parcel.writeString(sales);
        parcel.writeString(ticketCount);
        if (sellingRatio == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(sellingRatio);
        }
        parcel.writeString(address);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(startDateStr);
        parcel.writeString(endDateStr);
        parcel.writeString(coverPicture);
        parcel.writeString(video);
        parcel.writeString(nameOfUser);
        parcel.writeByte((byte) (isFavourite == null ? 0 : isFavourite ? 1 : 2));
        parcel.writeByte((byte) (isDraft == null ? 0 : isDraft ? 1 : 2));
        if (eventARStatusId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(eventARStatusId);
        }
        parcel.writeByte((byte) (isUnlock == null ? 0 : isUnlock ? 1 : 2));
        parcel.writeString(venue);
        parcel.writeString(city);
        if (checkedIn == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(checkedIn);
        }
        if (totalAttendees == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalAttendees);
        }
        parcel.writeString(manageUrl);
        parcel.writeString(reportUrl);
        parcel.writeInt(totalGuest);
        parcel.writeInt(checkedInGuest);
        parcel.writeFloat(checkedInRatio);
    }
}
