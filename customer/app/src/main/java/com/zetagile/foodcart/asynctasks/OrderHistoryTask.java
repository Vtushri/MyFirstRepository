package com.zetagile.foodcart.asynctasks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.zetagile.foodcart.adapters.OrderAdapter;
import com.zetagile.foodcart.connections.OrderConnections;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderHistoryTask extends AsyncTask<String, Void, ArrayList<Order>> {

    ProgressDialog dialog_waiting;
    List<Order> orders;
    Activity activity;
    User user;
    OrderAdapter adapter;
    boolean history;
    TextView no_orders;

    public OrderHistoryTask(Activity activity, User user, List<Order> orders, OrderAdapter adapter, boolean history, TextView no_orders) {
        this.user = user;
        this.activity = activity;
        this.orders = orders;
        this.adapter = adapter;
        this.history = history;
        this.no_orders = no_orders;
    }

    @Override
    protected void onPreExecute() {
        dialog_waiting = ProgressDialog.show(activity, "", "Please Wait for a moment....", true);
        dialog_waiting.setCancelable(true);

    }

    @Override
    protected ArrayList<Order> doInBackground(String... params) {
        orders.clear();

        if (history) {
            List<Order> list_ord_orders_local = OrderConnections.getOrderHistory(activity, user.getUserAccountId(), 1, 50);
            Collections.sort(list_ord_orders_local, new Comparator<Order>() {
                @Override
                public int compare(Order lhs, Order rhs) {
                    return rhs.getDeliveryTime().compareTo(lhs.getDeliveryTime());
                }
            });
            if (list_ord_orders_local != null)
                orders.addAll(list_ord_orders_local);
        } else {
            List<Order> list_ord_orders_local = OrderConnections.getCurrentOrders(activity, user.getUserAccountId(), 1, 50);
            Collections.sort(list_ord_orders_local, new Comparator<Order>() {
                @Override
                public int compare(Order lhs, Order rhs) {
                    return rhs.getDeliveryTime().compareTo(lhs.getDeliveryTime());
                }
            });
            if (list_ord_orders_local != null)
                orders.addAll(list_ord_orders_local);
        }

        return null;
    }

    @Override
    protected void onPostExecute(final ArrayList<Order> list_ord_orders_local) {

        if (orders == null || orders.size() == 0)
            no_orders.setVisibility(View.VISIBLE);
        else
            no_orders.setVisibility(View.GONE);

        adapter.notifyDataSetChanged();

        if (dialog_waiting.isShowing())
            dialog_waiting.dismiss();
    }
}

