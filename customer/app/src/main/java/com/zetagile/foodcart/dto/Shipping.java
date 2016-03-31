package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipping implements Serializable {

    private static final long serialVersionUID = 6149249018686303674L;

    private String shippingId;
    private String shippingMethod;
    private String shippingType;
    private double shippingCharges;
    private boolean enabled = true;
    private String status;
    private int maxDays;

    private Order order;

    private AddressLog addressLog;

    public Shipping() {

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public double getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(double shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public AddressLog getAddressLog() {
        return addressLog;
    }

    public void setAddressLog(AddressLog addressLog) {
        this.addressLog = addressLog;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
