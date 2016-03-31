package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable{


    private static final long serialVersionUID = -7040635725080432937L;

    private String orderId;
    private Date orderDate;
    private int token;
    private double valueAddedTax;
    private double shippingCharges;
    private double serviceTax;
    private double serviceCharges;
    private double extraCharges;

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public double getValueAddedTax() {
        return valueAddedTax;
    }

    public void setValueAddedTax(double valueAddedTax) {
        this.valueAddedTax = valueAddedTax;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    private String orderStatus;
    private String orderType;
    private Date deliveryTime;
    private long processingTime;

    private double totalAmount;
    private double subTotal;

    private int rating;

    private Shipping shipping;

    private Payment payment;

    private UserProfile userprofile;

    private Store store;

    private Collection<OrderProduct> products = new ArrayList<OrderProduct>();

    private List<Offer> offers = new ArrayList<>();

    private FeedBack comment;

    public FeedBack getComment() {
        return comment;
    }

    public void setComment(FeedBack comment) {
        this.comment = comment;
    }

    public Order() {
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public double getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(double shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

    public Collection<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(Collection<OrderProduct> products) {
        this.products = products;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(double extraCharges) {
        this.extraCharges = extraCharges;
    }


}
