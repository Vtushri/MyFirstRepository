package com.zetagile.foodcart.asynctasks;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.fragments.OrderSummary;
import com.zetagile.foodcart.fragments.catelog.CartFragment;

public class OrderCreationTask extends AsyncTask<Object, Void, String> {

    private static final String TAG = "ORDER_CREATION TASK";
    Order order;
    String user_id;
    String store_id;
    Activity activity;
    ProgressDialog dialog;
    Fragment fragment;

    public OrderCreationTask(Fragment fragment, Activity activity, Order order, String user_id, String store_id) {
        this.order = order;
        this.user_id = user_id;
        this.store_id = store_id;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Redirecting to payment...");
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {

        Log.i(TAG, "Creating order");
        return CartConnections.checkoutCart(activity, user_id, store_id, OrderType.valueOf(order.getOrderType()), order.getProcessingTime(), order.getDeliveryTime().getTime());
    }

    @Override
    protected void onPostExecute(String result) {

        if (dialog.isShowing())
            dialog.dismiss();

        if (result != null) {
            Log.i(TAG, "Order created and redirecting to payment screens");
            //We may have to refer to shipping class in future while retrieving shippingaddressid
            OrderSummary.createFragment(activity.getFragmentManager(), result, order.getOrderType(), CartFragment.userShippingAddressID);
        } else
            Log.i(TAG, "Server error in creating order");
    }
}

