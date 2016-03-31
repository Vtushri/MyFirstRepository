package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment implements Serializable {

    private static final long serialVersionUID = -4372447041290417639L;

    private String paymentId;
    private double payAmount;
    private double paymentCharges;
    private String paymentStatus;
    private Date timestamp;
    private String paymentMode;
    private String referenceId;
    private String gatewayResponse;

    private AddressLog paymentAddress;

    private AddressLog billingAddress;

    private AddressLog shippingAddress;

    private Order order;

    private Card card;

    public Payment() {

    }

//	public Payment(String paymentId, double payAmount, double paymentCharges, String paymentStatus, Date timestamp,
//			String paymentMode, Order order, Card card) {
//		super();
//		this.paymentId = paymentId;
//		this.payAmount = payAmount;
//		this.paymentCharges = paymentCharges;
//		this.paymentStatus = paymentStatus;
//		this.timestamp = timestamp;
//		this.paymentMode = paymentMode;
//		this.order = order;
//		this.card = card;
//	}
//
//
//
//	public Payment(Payment payment) {
//		this.paymentId = payment.getPaymentId();
//		this.payAmount = payment.getPayAmount();
//		this.paymentCharges = payment.getPaymentCharges();
//		this.paymentStatus = payment.getPaymentStatus();
//		this.timestamp = payment.getTimestamp();
//		this.paymentMode = payment.getPaymentMode();
//		this.order = payment.getOrder();
//		this.card = payment.getCard();
//	}

    public AddressLog getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressLog shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getPaymentCharges() {
        return paymentCharges;
    }

    public void setPaymentCharges(double paymentCharges) {
        this.paymentCharges = paymentCharges;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public AddressLog getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(AddressLog paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public AddressLog getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressLog billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getGatewayResponse() {
        return gatewayResponse;
    }

    public void setGatewayResponse(String gatewayResponse) {
        this.gatewayResponse = gatewayResponse;
    }

}
