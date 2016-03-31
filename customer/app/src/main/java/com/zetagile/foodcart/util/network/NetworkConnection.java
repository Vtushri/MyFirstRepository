package com.zetagile.foodcart.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zetagile.foodcart.NoInternetConnection;

public class NetworkConnection {
    public static boolean checkNetworkConnection(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
                return true;
            else
                return false;

        } else
            return false;
    }

    public static boolean checkInternetConnection(Context context) {

        if (context != null && !checkNetworkConnection(context)) {
            NoInternetConnection.startActivity(context);
            return false;
        } else
            return true;
    }
}
