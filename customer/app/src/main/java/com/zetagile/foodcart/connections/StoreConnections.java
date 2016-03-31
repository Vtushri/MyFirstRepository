package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;
import java.util.ArrayList;

public class StoreConnections {


    public static ArrayList<Store> getAllStores(Context context, String city) {
        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_stores = cc_connection.getRequest(context, connections.getSTORE_URL() + city);

        try {
            return new JsonStringToJava().convert_stores(str_stores);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }
}
