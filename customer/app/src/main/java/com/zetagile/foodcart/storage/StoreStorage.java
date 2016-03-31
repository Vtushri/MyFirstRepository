package com.zetagile.foodcart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;
import org.codehaus.jackson.map.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
public class StoreStorage {

    public static final String STORE_PREFS = "STORE_PREFS";
    public static final String STORE_ID = "STORE_ID";
    private static final String STORES_LIST = "STORES_LIST";

    public static String getStoreId(Context context) {
        try {
            return context.getSharedPreferences(STORE_PREFS, Context.MODE_PRIVATE).getString(STORE_ID, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean putStoreId(Context context, String store_id) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(STORE_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            editor.putString(STORE_ID, store_id);

            editor.apply();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean putStores(Context context, List<Store> stores) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(STORE_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            if(stores != null) {
                String str_stores = new ObjectMapper().writeValueAsString(stores);
                editor.putString(STORES_LIST, str_stores);
            }
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Store> getAllStores(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(STORE_PREFS, Context.MODE_PRIVATE);
            String str_stores = preferences.getString(STORES_LIST, "[]");
            System.out.println("In get all stores method");
            if(str_stores != null)
                return new JsonStringToJava().convert_stores(str_stores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
