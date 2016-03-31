package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties
public class Cart implements Serializable {

    private static final long serialVersionUID = -6868180260426309596L;

    private String cartId;
    private double totalPrice;
    private double subTotal;
    private double shippingCharges;
    private double serviceTax;
    private double valueAddedTax;
    private double serviceCharges;
    private double extraCharges;
    private Date expiry;

    private User user;

    private List<Cart_ProductView> products = new ArrayList<Cart_ProductView>();

    private List<Offer> appliedOffers = new ArrayList<>();

    public Cart() {

    }

    public double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(double extraCharges) {
        this.extraCharges = extraCharges;
    }

    public List<Offer> getAppliedOffers() {
        return appliedOffers;
    }

    public void setAppliedOffers(List<Offer> appliedOffers) {
        this.appliedOffers = appliedOffers;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(double shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public double getValueAddedTax() {
        return valueAddedTax;
    }

    public void setValueAddedTax(double valueAddedTax) {
        this.valueAddedTax = valueAddedTax;
    }

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Cart_ProductView> getProducts() {
        return products;
    }

    public void setProducts(List<Cart_ProductView> products) {
        this.products = products;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

}
