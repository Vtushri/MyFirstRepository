package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements Serializable {

    private static final long serialVersionUID = -1588261933793393341L;

    private String eventId;
    private String eventDescription;
    private String eventName;
    private Date validFrom;
    private Date validTill;
    private long alertTime;
    private String imageUrl;
    private boolean enabled;

    private Date createdTime;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date from) {
        this.validFrom = from;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date to) {
        this.validTill = to;
    }

    public long getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(long alertTime) {
        this.alertTime = alertTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
