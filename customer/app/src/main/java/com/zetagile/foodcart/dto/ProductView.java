package com.zetagile.foodcart.dto;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductView implements Parcelable {


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductView> CREATOR = new Parcelable.Creator<ProductView>() {
        @Override
        public ProductView createFromParcel(Parcel in) {
            return new ProductView(in);
        }

        @Override
        public ProductView[] newArray(int size) {
            return new ProductView[size];
        }
    };
    private String productId;
    private String productName;
    private String productDesc;
    private double productPrice1;
    private double productPrice2;
    private String manufacturer;
    private int qty;
    private Date addedDate;
    private Double rating;
    private String imagesUrl;
    private String categoryId;
    private boolean enabled = true;
    private Collection<Cart_ProductView> cart_product = new ArrayList<Cart_ProductView>();

    public ProductView() {

    }

    protected ProductView(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDesc = in.readString();
        productPrice1 = in.readDouble();
        productPrice2 = in.readDouble();
        manufacturer = in.readString();
        qty = in.readInt();
        long tmpAddedDate = in.readLong();
        addedDate = tmpAddedDate != -1 ? new Date(tmpAddedDate) : null;
        rating = in.readByte() == 0x00 ? null : in.readDouble();
        imagesUrl = in.readString();
        categoryId = in.readString();
        cart_product = (Collection) in.readValue(Collection.class.getClassLoader());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getProductPrice1() {
        return productPrice1;
    }

    public void setProductPrice1(double productPrice1) {
        this.productPrice1 = productPrice1;
    }

    public double getProductPrice2() {
        return productPrice2;
    }

    public void setProductPrice2(double productPrice2) {
        this.productPrice2 = productPrice2;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Collection<Cart_ProductView> getCart_product() {
        return cart_product;
    }

    public void setCart_product(Collection<Cart_ProductView> cart_product) {
        this.cart_product = cart_product;
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(productDesc);
        dest.writeDouble(productPrice1);
        dest.writeDouble(productPrice2);
        dest.writeString(manufacturer);
        dest.writeInt(qty);
        dest.writeLong(addedDate != null ? addedDate.getTime() : -1L);

        if (rating == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(rating);
        }
        dest.writeString(imagesUrl);
        dest.writeString(categoryId);
        dest.writeValue(cart_product);
    }

    @Override
    public boolean equals(Object o) {
        if (this.hashCode() == o.hashCode())
            return true;
        else
            return false;
    }
}