package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = -4733923099035037402L;

    private String o_p_id;

    private Order order;

    private ProductView product;

    private int quantity;

    private double price;

    public OrderProduct() {
        super();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getO_p_id() {
        return o_p_id;
    }

    public void setO_p_id(String o_p_id) {
        this.o_p_id = o_p_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductView getProduct() {
        return product;
    }

    public void setProduct(ProductView product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
