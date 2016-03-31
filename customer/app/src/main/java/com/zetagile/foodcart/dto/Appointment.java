package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private int seats;
    private Date reservedTime;

    private boolean enabled = true;

    private UserProfile userprofile;

    private Store store;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Date getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(Date reservedTime) {
        this.reservedTime = reservedTime;
    }

    public UserProfile getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(UserProfile userprofile) {
        this.userprofile = userprofile;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
