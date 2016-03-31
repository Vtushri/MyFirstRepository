package com.zetagile.foodcart.dto;


import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Store implements Parcelable {

    private String storeId;
    private String name;
    private String latitude;
    private String longitude;
    private String storeAddress;
    private String storeCity;

    private long workingFrom;
    private long workingTill;
    private long waitingTime;

    private boolean enabled = false;

    private Collection<Order> order = new ArrayList<Order>();

    private List<Appointment> appointments = new ArrayList<>();

    private User user;

    public Store() {
        super();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public Collection<Order> getOrder() {
        return order;
    }

    public void setOrder(Collection<Order> order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getWorkingFrom() {
        return workingFrom;
    }

    public void setWorkingFrom(long workingFrom) {
        this.workingFrom = workingFrom;
    }

    public long getWorkingTill() {
        return workingTill;
    }

    public void setWorkingTill(long workingTill) {
        this.workingTill = workingTill;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    protected Store(Parcel in) {
        storeId = in.readString();
        name = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        storeAddress = in.readString();
        storeCity = in.readString();
        workingFrom = in.readLong();
        workingTill = in.readLong();
        waitingTime = in.readLong();
        enabled = in.readByte() != 0x00;
        order = (Collection) in.readValue(Collection.class.getClassLoader());
        if (in.readByte() == 0x01) {
            appointments = new ArrayList<Appointment>();
            in.readList(appointments, Appointment.class.getClassLoader());
        } else {
            appointments = null;
        }
        user = (User) in.readValue(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeId);
        dest.writeString(name);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(storeAddress);
        dest.writeString(storeCity);
        dest.writeLong(workingFrom);
        dest.writeLong(workingTill);
        dest.writeLong(waitingTime);
        dest.writeByte((byte) (enabled ? 0x01 : 0x00));
        dest.writeValue(order);
        if (appointments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(appointments);
        }
        dest.writeValue(user);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
}
