package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderNotification {

    private String messageType;
    private Order order;
    private Location location;
    private String orderType;
    private long arrivalTime;
    private User from;
    private int seats;
    private AddressLog AddressLog;

    public AddressLog getAddressLog() {
        return AddressLog;
    }

    public void setAddressLog(AddressLog addressLog) {
        AddressLog = addressLog;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        OrderNotification notification = (OrderNotification) o;
        if (notification.getFrom() != null)
            return from.getUserAccountId().equals(notification.getFrom().getUserAccountId());

        return false;
    }

    @Override
    public int hashCode() {
        if (getFrom() != null)
            return from.getUserAccountId().hashCode();
        else
            return super.hashCode();
    }
}
