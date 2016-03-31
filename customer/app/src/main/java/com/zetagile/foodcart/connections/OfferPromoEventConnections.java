package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.dto.Event;
import com.zetagile.foodcart.dto.Offer;
import com.zetagile.foodcart.dto.Promo;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;
import java.util.ArrayList;

public class OfferPromoEventConnections {

    public static ArrayList<Offer> getAllOffers(Context context, int page, int size) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_offers = cc_connection.getRequest(context, connections.getALL_OFFER_URL() + "?page=" + page + "&size=" + size);

        try {
            return new JsonStringToJava().convert_offer_list_json(str_offers);

        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    public static ArrayList<Promo> getAllPromos(Context context, int page, int size) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_offers = cc_connection.getRequest(context, connections.getALL_PROMO_URL() + "?page=" + page + "&size=" + size);

        try {
            return new JsonStringToJava().convert_promo_list_json(str_offers);

        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    public static ArrayList<Event> getAllEvents(Context context, int page, int size) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_events = cc_connection.getRequest(context, connections.getALL_EVENT_URL() + "?page=" + page + "&size=" + size);

        try {
            return new JsonStringToJava().convert_event_list_json(str_events);

        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

//    public static ArrayList<ProductView> getOfferProducts(String str_offer_id, int page, int count) {
//        ConnectionClass cc_connection = new ConnectionClass();
//        String str_products = cc_connection.getRequest(ConnectionURLs.OFFER_PRODUCTS_URL + str_offer_id + "?page=" + page + "&count=" + count);
//
//        try {
//            ArrayList<ProductView> list_products = (ArrayList<ProductView>) new JsonStringToJava().convert_products_json(str_products);
//            if (list_products != null)
//                return list_products;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}
