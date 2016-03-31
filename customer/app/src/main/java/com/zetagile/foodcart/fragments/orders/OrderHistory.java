package com.zetagile.foodcart.fragments.orders;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.LoginActivity;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.OrderAdapter;
import com.zetagile.foodcart.asynctasks.OrderHistoryTask;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends Fragment {

    private static final String HISTORY = "HISTORY";
    public static final String TAG_HISTORY = "TAG_HISTORY";
    public static final String TAG_CURRENT = "TAG_CURRENT";

    List<Order> list_ord_orders = new ArrayList<>();
    OrderAdapter adapter_order;

    Activity activity;
    RecyclerView recycle_orderHistory;
    TextView tv_no_orders;

    boolean history;

    public OrderHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        list_ord_orders = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View orderHistoryView = inflater.inflate(R.layout.fragment_order_history, container, false);
        tv_no_orders = (TextView) orderHistoryView.findViewById(R.id.no_orders);
        recycle_orderHistory = (RecyclerView) orderHistoryView.findViewById(R.id.recycle_order_history);

        return orderHistoryView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        history = getArguments().getBoolean(HISTORY);

        adapter_order = new OrderAdapter(activity, list_ord_orders);

        recycle_orderHistory.setLayoutManager(new LinearLayoutManager(activity));
        recycle_orderHistory.setAdapter(adapter_order);

    }

    @Override
    public void onResume() {
        super.onResume();

        User usr_user = UserStorage.getUser(activity);

        if (usr_user != null) {
            OrderHistoryTask async_task_order_history = new OrderHistoryTask(activity, usr_user, list_ord_orders, adapter_order, history, tv_no_orders);
            async_task_order_history.execute();

        } else {
            Toast.makeText(activity, "Please login...", Toast.LENGTH_SHORT).show();
            activity.getFragmentManager().popBackStack();
        }

    }

    public static void createFragment(FragmentManager manager, boolean history) {
        if (manager != null) {
            if (history) {
                Fragment fragment = manager.findFragmentByTag(TAG_HISTORY);

                if (fragment == null) {
                    fragment = new OrderHistory();

                    Bundle bundle = new Bundle();
                    bundle.putBoolean(HISTORY, true);
                    fragment.setArguments(bundle);
                }

                if (!fragment.isVisible()) {

                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame, fragment, TAG_HISTORY);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    manager.executePendingTransactions();
                }
            } else {

                Fragment fragment = manager.findFragmentByTag(TAG_CURRENT);

                if (fragment == null) {
                    fragment = new OrderHistory();

                    Bundle bundle = new Bundle();
                    bundle.putBoolean(HISTORY, false);
                    fragment.setArguments(bundle);
                }

                if (!fragment.isVisible()) {

                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame, fragment, TAG_CURRENT);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    manager.executePendingTransactions();
                }

            }

        }
    }

}
