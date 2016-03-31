package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ProductConnections {

    public static String getProductDetails(Context context, String str_product_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        try {
            return cc_connection.getRequest(context, connections.getPRODUCT_DETAILS_URL() + str_product_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static ArrayList<ProductView> getProducts(Context context, String str_url, int page, int count) {

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, str_url + "?page=" + page + "&size=" + count);

        if (str_response != null) {
            try {
                return (ArrayList<ProductView>) new JsonStringToJava().convert_products_json(str_response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    public static ArrayList<ProductView> getProductsByCategory(Context context, String str_category_id, int page, int count) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, connections.getCATEGORY_PRODUCT_URL() + str_category_id + "?page=" + page + "&size=" + count);

        if (str_response != null) {
            try {
                return (ArrayList<ProductView>) new JsonStringToJava().convert_products_json(str_response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    public static List<ProductView> getAllProductsByName(Context context, String partial_name, int page, int count) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = null;
        try {
            str_response = cc_connection.getRequest(context, connections.getPRODUCT_BY_NAME_URL() + "?name=" + URLEncoder.encode(partial_name, "UTF-8") + "&page=" + page + "&size=" + count);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (str_response != null) {
            try {
                return new JsonStringToJava().convert_products_json(str_response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }
//
//    public static ArrayList<ProductView> getCartProductsByUser(Context context, String str_user_id) {
//        return null;
//    }

//    public static boolean addToCart(Context context, String str_user_id, String str_product_id) {
//        try {
//
//            JSONObject json_user_id_product_id = new JSONObject();
//            json_user_id_product_id.put("userAccountId", str_user_id);
//            json_user_id_product_id.put("productId", str_product_id);
//
//            ConnectionClass cc_connection = new ConnectionClass();
//            String str_result = cc_connection.postRequest(context, ConnectionURLs.ADD_TO_CART_URL, json_user_id_product_id.toString());
//            if (str_result != null)
//                return Boolean.parseBoolean(str_result);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

}
