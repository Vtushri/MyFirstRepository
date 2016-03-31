package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 3404598134780599229L;

    String userAccountId;

    private User user;

    private Collection<Order> order = new ArrayList<Order>();

    private Collection<AddressLog> adrresses = new ArrayList<>();

    private Collection<Card> card = new ArrayList<Card>();

    private List<Appointment> appointments = new ArrayList<>();

    private List<Notification> notifications = new ArrayList<>();


    private Date timeStamp;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Order> getOrder() {
        return order;
    }

    public void setOrder(Collection<Order> order) {
        this.order = order;
    }

    public Collection<AddressLog> getAdrresses() {
        return adrresses;
    }

    public void setAdrresses(Collection<AddressLog> adrresses) {
        this.adrresses = adrresses;
    }

    public Collection<Card> getCard() {
        return card;
    }

    public void setCard(Collection<Card> card) {
        this.card = card;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
