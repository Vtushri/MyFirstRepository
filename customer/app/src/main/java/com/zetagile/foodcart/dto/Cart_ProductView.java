package com.zetagile.foodcart.dto;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart_ProductView implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cart_ProductView> CREATOR = new Parcelable.Creator<Cart_ProductView>() {
        @Override
        public Cart_ProductView createFromParcel(Parcel in) {
            return new Cart_ProductView(in);
        }

        @Override
        public Cart_ProductView[] newArray(int size) {
            return new Cart_ProductView[size];
        }
    };
    private String c_p_id;
    private Cart cart;
    private ProductView product;
    private int quantity;

    public Cart_ProductView() {

    }

    public Cart_ProductView(Cart_ProductView cart_productview) {
        super();
        this.c_p_id = cart_productview.getC_p_id();
        this.cart = cart_productview.getCart();
        this.product = cart_productview.getProduct();
        this.quantity = cart_productview.getQuantity();
    }

    public Cart_ProductView(String c_p_id, Cart cart, ProductView product, int quantity) {
        super();
        this.c_p_id = c_p_id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    protected Cart_ProductView(Parcel in) {
        c_p_id = in.readString();
        cart = (Cart) in.readValue(Cart.class.getClassLoader());
        product = (ProductView) in.readValue(ProductView.class.getClassLoader());
        quantity = in.readInt();
    }

    public String getC_p_id() {
        return c_p_id;
    }

    public void setC_p_id(String c_p_id) {
        this.c_p_id = c_p_id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

    @Override
    public int hashCode() {

        return c_p_id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this.hashCode() == o.hashCode())
            return true;
        else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(c_p_id);
        dest.writeValue(cart);
        dest.writeValue(product);
        dest.writeInt(quantity);
    }
}