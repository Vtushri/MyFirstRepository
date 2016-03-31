package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.constants.PaymentModes;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.Payment;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OrderConnections {

    public static Order getOrder(Context context, String str_order_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.getRequest(context, connections.getSINGLE_ORDER_URL() + str_order_id);



        try {
            JsonStringToJava j2p = new JsonStringToJava();
            Order ord_order = j2p.convert_order_json(str_response);
            return ord_order;

        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static List<Order> getCurrentOrders(Context context, String str_user_id, int page, int count) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.getRequest(context, connections.getCURRENT_ORDER_URL() + str_user_id + "?page=" + page + "&count=" + count);



        try {
            JsonStringToJava j2p = new JsonStringToJava();
            List<Order> list_ord_orders = j2p.convert_json_order_list(str_response);
            return list_ord_orders;

        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    public static ArrayList<Order> getOrderHistory(Context context, String str_user_id, int page, int count) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.getRequest(context, connections.getORDER_HISTORY_URL() + str_user_id + "?page=" + page + "&count=" + count);

        ArrayList<Order> list_ord_orders = null;


        try {
            JsonStringToJava j2p = new JsonStringToJava();
            list_ord_orders = j2p.convert_json_order_list(str_response);
            return list_ord_orders;
        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

//    public static String createOrder(String str_user_id, String str_store_id, Order ord_order) {
//
//        String str_order = null;
//        try {
//            str_order = new JavaToJsonString().convert_order(ord_order);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ConnectionClass cc_connection = new ConnectionClass();
//        return cc_connection.postRequest(ConnectionURLs.CREATE_ORDER_URL + "?user_id=" + str_user_id + "&store_id=" + str_store_id, str_order);
//    }

    public static String createPayment(Context context, String user_id, Payment pay_payment) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_payment_json = null;

        try {
            str_payment_json = new JavaToJsonString().convert_payment(pay_payment);
            System.out.println(str_payment_json);
        } catch (IOException e) {
            e.printStackTrace();
            cc_connection.displayError(context, "Input Error");
            return null;
        }

        String str_response = cc_connection.postRequest(context, connections.getCREATE_PAYMENT_URL() + user_id, str_payment_json);

        if (str_response != null)
            return str_response;

        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static boolean placeOrder(Context context,
                                     String str_order_id,
                                     String user_id,
                                     String str_payment,
                                     String str_shipping_id,
                                     PaymentModes mode) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        if(str_payment == null)
            str_payment = "";

        String str_response = null;

        if (str_order_id != null && user_id != null && mode != null)
            str_response = cc_connection.postRequest(
                    context,
                    connections.getPLACE_ORDER_URL() + str_order_id +
                            "?shipping_id=" + str_shipping_id +
                            "&paymentmode=" + mode.name() +
                            "&userid=" + user_id,
                    str_payment);

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {

            }
        }
        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static boolean rateOrder(Context context, String order_id, int rating) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.postRequest(context, connections.getRATE_ORDER_URL() + order_id + "?rating" + rating, "");

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {

            }
        }

        cc_connection.displayError(context, "Response Error");
        return false;
    }
}
