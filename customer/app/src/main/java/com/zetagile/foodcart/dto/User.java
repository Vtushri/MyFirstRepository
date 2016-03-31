package com.zetagile.foodcart.dto;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private static final long serialVersionUID = 990693682429526981L;
    String userAccountId;
    String userRole;
    String userName;
    String password;
    String fullName;
    String firstName;
    String middleName;
    String lastName;
    String mobileNo;
    String emailId;
    String fbId;
    int token;
    boolean like;
    boolean loyal;
    boolean guest;
    boolean enabled;
    boolean newCustomer;
    private Cart cart;
    private UserProfile userprofile;

    public User() {

    }

    protected User(Parcel in) {
        userAccountId = in.readString();
        userRole = in.readString();
        userName = in.readString();
        password = in.readString();
        fullName = in.readString();
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        mobileNo = in.readString();
        emailId = in.readString();
        fbId = in.readString();
        token = in.readInt();
        like = in.readByte() != 0x00;
        loyal = in.readByte() != 0x00;
        guest = in.readByte() != 0x00;
        enabled = in.readByte() != 0x00;
        newCustomer = in.readByte() != 0x00;
        cart = (Cart) in.readValue(Cart.class.getClassLoader());
        userprofile = (UserProfile) in.readValue(UserProfile.class.getClassLoader());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isLoyal() {
        return loyal;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(boolean newCustomer) {
        this.newCustomer = newCustomer;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public UserProfile getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(UserProfile userprofile) {
        this.userprofile = userprofile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userAccountId);
        dest.writeString(userRole);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(firstName);
        dest.writeString(middleName);
        dest.writeString(lastName);
        dest.writeString(mobileNo);
        dest.writeString(emailId);
        dest.writeString(fbId);
        dest.writeInt(token);
        dest.writeByte((byte) (like ? 0x01 : 0x00));
        dest.writeByte((byte) (loyal ? 0x01 : 0x00));
        dest.writeByte((byte) (guest ? 0x01 : 0x00));
        dest.writeByte((byte) (enabled ? 0x01 : 0x00));
        dest.writeByte((byte) (newCustomer ? 0x01 : 0x00));
        dest.writeValue(cart);
        dest.writeValue(userprofile);
    }
}