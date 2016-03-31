package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CartConnections {


    public static boolean clearCart(Context context, String str_user_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.postRequest(context, connections.getEMPTY_CART_URL() + str_user_id, "");

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {

            }
        }

        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static Cart updateProductOfCart(Context context, String str_user_id, String str_product_id, int int_qty) throws JSONException {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        JSONObject data = new JSONObject();
        data.put("userAccountId", str_user_id);
        data.put("productId", str_product_id);
        data.put("quantity", int_qty);

        String str_response = cc_connection.postRequest(context, connections.getCART_UPDATE_ITEM_URL(), data.toString());

        if (str_response != null) {
            try {
                return new JsonStringToJava().convertCartfromJson(str_response);
            } catch (Exception e) {

            }
        }
        cc_connection.displayError(context, "Response Error");

        return null;
    }

    public static int getCartProductCount(Context context, String str_user_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, connections.getCART_COUNT_URL() + str_user_id);
        if (str_response != null) {
            try {
                return Integer.parseInt(str_response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return 0;
    }

    public static Cart applyOfferOnCart(Context context, String str_user_id, String str_offer_code) {
        ConnectionClass cc_connection = new ConnectionClass();

        try {

            ConnectionURLs connections = ConnectionURLs.getInstance(context);

            String str_response = cc_connection.postRequest(context, connections.getAPPLY_OFFER_URL() + str_user_id + "?code=" + URLEncoder.encode(str_offer_code, "UTF-8") , "");

            if (str_response != null) {
                try {
                    return new JsonStringToJava().convertCartfromJson(str_response);
                } catch (Exception e) {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static String checkoutCart(Context context, String str_user_id, String str_store_id, OrderType orderType, long processingTime, long deliveryTime) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
//        System.out.println(str_user_id);
        String str_response = cc_connection.postRequest(context, connections.getCHECKOUT_URL() + str_user_id + "?storeid=" + str_store_id + "&ordertype=" + orderType.name() + "&processtime=" + processingTime + "&deliverytime=" + deliveryTime, "");

        if (str_response != null)
            return str_response;

        return null;
    }

    public static Cart getCart(Context context, String user_id) {
        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.getRequest(context, connections.getCART_URL() + user_id);


        if (str_response != null) {

            return new JsonStringToJava().convertCartfromJson(str_response);

        } else {
            cc_connection.displayError(context, "Response null");
        }
        return null;
    }

}
