package com.zetagile.foodcart.connections;

import android.content.Context;
import android.content.res.AssetManager;

import com.zetagile.foodcart.util.ConfigUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionURLs {

    protected String EMPTY_CART_URL = "/rest/cartrsrc/removeallcart/";
    protected String CART_PRODUCTS_URL = "/rest/cartrsrc/cartbyuserid/";
    protected String CART_COUNT_URL = "/rest/cartrsrc/cartcount/";
    protected String CART_UPDATE_ITEM_URL = "/rest/cartrsrc/updatecart/";
    //    protected String CART_TOTAL_PRICE_URL = "/rest/cartrsrc/carttotalprice/";
    protected String APPLY_OFFER_URL = "/rest/cartrsrc/applyoffer/";
    protected String CHECKOUT_URL = "/rest/cartrsrc/checkout/";
    protected String ALL_OFFER_URL = "/rest/promosoffers/getalloffers";
    protected String ALL_EVENT_URL = "/rest/eventnotification/getallevents";
    protected String ALL_PROMO_URL = "/rest/promosoffers/getallpromos";
    protected String SINGLE_ORDER_URL = "/rest/orderrsrc/order/";
    protected String CURRENT_ORDER_URL = "/rest/orderrsrc/currentorders/";
    protected String ORDER_HISTORY_URL = "/rest/orderrsrc/orderhistory/";
    protected String PLACE_ORDER_URL = "/rest/orderrsrc/placeorder/";
    protected String RATE_ORDER_URL = "/rest/orderrsrc/rateorder/";
    protected String CREATE_PAYMENT_URL = "/rest/orderrsrc/createpayment/";
    protected String PRODUCT_DETAILS_URL = "/rest/productrsrc/productbyid/";
    protected String CATEGORY_PRODUCT_URL = "/rest/productrsrc/productbycategory/";
    protected String PRODUCT_BY_NAME_URL = "/rest/productrsrc/productbyname";
    protected String LOGIN_URL = "/rest/userrsrc/userdetails";
    protected String SIGN_UP_URL = "/rest/userrsrc/userregistration";
    protected String USER_NAME_AVAILABLE_URL = "/rest/userrsrc/isregistered/";
    protected String SUBMIT_FEEDBACK_URL = "/rest/userrsrc/feedback/";
    protected String GET_ALL_FEEDBACK_URL = "/rest/userrsrc/getallfeedback";
    protected String LIKED_FB_URL = "/rest/userrsrc/likedfbpage/";
    protected String USER_EDIT_URL = "/rest/userrsrc/edituser/";
    protected String CATEGORY_URL = "/rest/catrsrc/getsubcategory/";
    protected String STORE_URL = "/rest/storersrc/storebycity/";
    protected String CART_URL = "/rest/cartrsrc/getcart/";
    protected String GET_ADDR_URL = "/rest/userrsrc/alladdresses/";
    protected String ADD_ADDR_URL = "/rest/userrsrc/addaddress/";
    protected String DEL_ADDR_URL = "/rest/userrsrc/removeaddress/";

    String host;
    String port;
    String server_secure_port;
    String name;
    String xmppdomain;
    int xmppport;
    String xmppservice;
    private String prefix = "";

    public ConnectionURLs(Context context) {
        try {
            AssetManager manager = context.getAssets();
            InputStream in = manager.open("config.properties");
            Properties properties = new Properties();
            properties.load(in);

            host = properties.getProperty("SERVER_HOST");
            port = properties.getProperty("SERVER_PORT");
            name = properties.getProperty("SERVER_NAME");
            server_secure_port = properties.getProperty("SERVER_SECURE_PORT");

            if (host != null)
                host = host.trim();
            if (port != null)
                port = port.trim();
            if (name != null)
                name = name.trim();
            if(server_secure_port != null)
                server_secure_port = server_secure_port.trim();

            ConfigUtil utils = new ConfigUtil(context);
            if (utils.isSecureProtocolEnabled())
                prefix = "https://" + host + ":" + server_secure_port + "/" + name;
            else
                prefix = "http://" + host + ":" + port + "/" + name;

            xmppdomain = properties.getProperty("XMPP_DOMAIN");
            xmppport = Integer.parseInt(properties.getProperty("XMPP_PORT"));
            xmppservice = properties.getProperty("XMPP_SERVICE");

            if (xmppdomain != null)
                xmppdomain = xmppdomain.trim();
            if (xmppservice != null)
                xmppservice = xmppservice.trim();

        } catch (IOException e) {
            e.printStackTrace();

//            Toast.makeText(context, "Error in loading config file...", Toast.LENGTH_LONG).show();
        }
    }

    public static ConnectionURLs getInstance(Context context) {
        return new ConnectionURLs(context);
    }

    public String getCART_URL() {
        return prefix + CART_URL;
    }
    public String getEMPTY_CART_URL() {
        return prefix + EMPTY_CART_URL;
    }

    public String getCART_PRODUCTS_URL() {
        return prefix + CART_PRODUCTS_URL;
    }

    public String getCART_COUNT_URL() {
        return prefix + CART_COUNT_URL;
    }

    public String getCART_UPDATE_ITEM_URL() {
        return prefix + CART_UPDATE_ITEM_URL;
    }

    public String getName() {
        return name;
    }

    public String getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

//    public String getCART_TOTAL_PRICE_URL() {
//        return prefix + CART_TOTAL_PRICE_URL;
//    }

    public String getAPPLY_OFFER_URL() {
        return prefix + APPLY_OFFER_URL;
    }

    public String getCHECKOUT_URL() {
        return prefix + CHECKOUT_URL;
    }

    public String getALL_OFFER_URL() {
        return prefix + ALL_OFFER_URL;
    }

    public String getALL_EVENT_URL() {
        return prefix + ALL_EVENT_URL;
    }

    public String getALL_PROMO_URL() {
        return prefix + ALL_PROMO_URL;
    }

    public String getSINGLE_ORDER_URL() {
        return prefix + SINGLE_ORDER_URL;
    }

    public String getCURRENT_ORDER_URL() {
        return prefix + CURRENT_ORDER_URL;
    }

    public String getPLACE_ORDER_URL() {
        return prefix + PLACE_ORDER_URL;
    }

    public String getORDER_HISTORY_URL() {
        return prefix + ORDER_HISTORY_URL;
    }

    public String getRATE_ORDER_URL() {
        return prefix + RATE_ORDER_URL;
    }

    public String getCREATE_PAYMENT_URL() {
        return prefix + CREATE_PAYMENT_URL;
    }

    public String getPRODUCT_DETAILS_URL() {
        return prefix + PRODUCT_DETAILS_URL;
    }

    public String getCATEGORY_PRODUCT_URL() {
        return prefix + CATEGORY_PRODUCT_URL;
    }

    public String getPRODUCT_BY_NAME_URL() {
        return prefix + PRODUCT_BY_NAME_URL;
    }

    public String getLOGIN_URL() {
        return prefix + LOGIN_URL;
    }

    public String getSIGN_UP_URL() {
        return prefix + SIGN_UP_URL;
    }

    public String getUSER_NAME_AVAILABLE_URL() {
        return prefix + USER_NAME_AVAILABLE_URL;
    }

    public String getSUBMIT_FEEDBACK_URL() {
        return prefix + SUBMIT_FEEDBACK_URL;
    }

    public String getGET_ALL_FEEDBACK_URL() {
        return prefix + GET_ALL_FEEDBACK_URL;
    }

    public String getLIKED_FB_URL() {
        return prefix + LIKED_FB_URL;
    }

    public String getUSER_EDIT_URL() {
        return prefix + USER_EDIT_URL;
    }

    public String getCATEGORY_URL() {
        return prefix + CATEGORY_URL;
    }

    public String getSTORE_URL() {
        return prefix + STORE_URL;
    }

    public String getXmppdomain() {
        return xmppdomain;
    }

    public int getXmppport() {
        return xmppport;
    }

    public String getXmppservice() {
        return xmppservice;
    }

//    public static  String HOME_PAGE_CATEGORY_URL = "http://52.74.237.28:8080/ecart/rest/homersrc/homecategories";
//    public static  String HOME_ADVERTISEMENT_URL = "http://52.74.237.28:8080/ecart/rest/homersrc/homeads";

//    public static final String LOCATION_SERVICE_URL = "ws://52.74.237.28:8080/ecart/track/";
//    public static final String NOTIFICATION_SERVICE_URL = "ws://52.74.237.28:8080/ecart/notify/";
//
//    public static final String HOST_PORT_URL = "ws://52.74.237.28:8080/ecart/rest/hostport";

//
//    public static final String NEW_APPOINTMENT_URL = "http://52.74.237.28:8080/ecart/rest/appointment/addnew";
//    public static final String GET_APPOINTMENTS_URL = "http://52.74.237.28:8080/ecart/rest/appointment/getallbyuser/";

    //    public static final String ADD_TO_CART_URL = "http://52.74.237.28:8080/ecart/rest/cartrsrc/addtocart";
    //    public static final String CREATE_ORDER_URL = "http://52.74.237.28:8080/ecart/rest/orderrsrc/create_order";

    public String getGET_ADDR_URL() {
        return prefix + GET_ADDR_URL;
    }

    public String getADD_ADDR_URL() {
        return prefix + ADD_ADDR_URL;
    }

    public String getDEL_ADDR_URL() {
        return prefix + DEL_ADDR_URL;
    }
}
