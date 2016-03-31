package com.zetagile.foodcart.fragments.orders;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.SecondProductListAdapter;
import com.zetagile.foodcart.connections.OrderConnections;
import com.zetagile.foodcart.constants.OrderStatus;
import com.zetagile.foodcart.constants.PaymentModes;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.OrderProduct;
import com.zetagile.foodcart.recyclerview.CustomLinearLayoutManager;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class SingleInvoiceFragment extends Fragment {

    public static final String TAG = "_ORDER_DETAILS";
    public static final String ORDER_ID = "ORDERID";

    View layout;

    String str_order_id;
    Activity activity;


    public SingleInvoiceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_single_invoice, container, false);

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        str_order_id = getArguments().getString(ORDER_ID);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ConfigUtil configUtil = new ConfigUtil(activity);

        if (!configUtil.isVatApplicable())
            layout.findViewById(R.id.order_detail_vat_ll).setVisibility(View.GONE);

        if (!configUtil.isServiceTaxApplicable())
            layout.findViewById(R.id.order_detail_service_tax_ll).setVisibility(View.GONE);

        if (!configUtil.isServiceChargesApplicable())
            layout.findViewById(R.id.order_detail_service_charge_ll).setVisibility(View.GONE);

        if (!configUtil.isOtherChargeApplicable())
            layout.findViewById(R.id.order_detail_othercharges_ll).setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        String order_id = null;

        if (getArguments() != null)
            order_id = getArguments().getString(ORDER_ID);

        SingleOrderAsyncTask order_task = new SingleOrderAsyncTask();
        order_task.execute();
    }

    private class SingleOrderAsyncTask extends AsyncTask<String, Void, Order> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Order doInBackground(String... params) {

            return OrderConnections.getOrder(getActivity(), str_order_id);

        }

        @Override
        protected void onPostExecute(Order ord_order) {
            createOrderDialog(activity, ord_order, layout);

            if(dialog != null && dialog.isShowing())
                dialog.dismiss();
        }
    }


    public void createOrderDialog(final Activity activity, final Order order, View layout) {

        TextView token;
        TextView delivery_time;
        TextView status;
        TextView total_amount;
        TextView tv_order_id;
        TextView tv_other_charges;
        TextView tv_service_charge;
        TextView tv_service_tax;
        TextView tv_vat;
        TextView tv_order_type;
        TextView tv_payment;
        TextView tv_subtotal;
        TextView tv_offers_applied;
        TextView tv_store_name;
        TextView tv_store_address;
        TextView tv_store_city;
        TextView tv_order_date;
        Button btn_cancel;
        RecyclerView product_listview;

        tv_order_id = (TextView) layout.findViewById(R.id.order_detail_order_id);
        product_listview = (RecyclerView) layout.findViewById(R.id.order_detail_shipping_list);
        delivery_time = (TextView) layout.findViewById(R.id.order_detail_delivery_time);
        token = (TextView) layout.findViewById(R.id.order_detail_token);
        status = (TextView) layout.findViewById(R.id.order_detail_status);
        tv_other_charges = (TextView) layout.findViewById(R.id.order_detail_other_charges);
        total_amount = (TextView) layout.findViewById(R.id.order_detail_total_amount);
        tv_service_charge = (TextView) layout.findViewById(R.id.order_detail_service_charges);
        tv_service_tax = (TextView) layout.findViewById(R.id.order_detail_service_tax);
        tv_vat = (TextView) layout.findViewById(R.id.order_detail_vat_charges);
        tv_order_type = (TextView) layout.findViewById(R.id.order_detail_type);
        tv_payment = (TextView) layout.findViewById(R.id.order_detail_payment);
        tv_subtotal = (TextView) layout.findViewById(R.id.order_detail_sub_total);
        tv_offers_applied = (TextView) layout.findViewById(R.id.order_detail_offers_applied);
        tv_store_name = (TextView) layout.findViewById(R.id.order_store_name);
        tv_store_address = (TextView) layout.findViewById(R.id.order_store_address);
        tv_store_city = (TextView) layout.findViewById(R.id.order_store_city);
        btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        tv_order_date = (TextView) layout.findViewById(R.id.order_detail_date);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragmentManager().popBackStack();
            }
        });

        if (order.getProducts() != null) {
            product_listview.setLayoutManager(new CustomLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            SecondProductListAdapter productAdapter = new SecondProductListAdapter(activity, (List<OrderProduct>) order.getProducts());
            product_listview.setAdapter(productAdapter);
        }

        if (order.getOrderId() != null)
            tv_order_id.setText(order.getOrderId());

        if (order.getDeliveryTime() != null) {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
            delivery_time.setText(format.format(order.getDeliveryTime()));
        }

        if(order.getSubTotal() != 0)
            PriceAndCurrency.setPriceWithPrecision(tv_subtotal, order.getSubTotal());

        if (order.getTotalAmount() != 0)
            PriceAndCurrency.setPriceWithPrecision(total_amount, order.getTotalAmount());

        if(order.getOffers() != null && order.getOffers().size() != 0)
            tv_offers_applied.setText("( "+ order.getOffers().size() +" offers applied)");
        else
            tv_offers_applied.setVisibility(View.GONE);

        if (order.getOrderType() != null)
            tv_order_type.setText(order.getOrderType());

        if (order.getPayment() != null) {
            if (order.getPayment().getPaymentMode() != null && order.getPayment().getPaymentMode().equals(PaymentModes.COP.name()))
                tv_payment.setText(R.string.payment_cod);
            else if (order.getPayment().getPaymentMode() != null && order.getPayment().getPaymentMode().equals(PaymentModes.PAYPAL.name()))
                tv_payment.setText(R.string.payment_paypal);
            else
                tv_payment.setText(R.string.payment_no_pay);
        } else
            tv_payment.setText(R.string.payment_no_pay);

        PriceAndCurrency.setPriceWithPrecision(tv_vat, order.getValueAddedTax());
        PriceAndCurrency.setPriceWithPrecision(tv_service_tax, order.getServiceTax());
        PriceAndCurrency.setPriceWithPrecision(tv_service_charge, order.getServiceCharges());
        PriceAndCurrency.setPriceWithPrecision(tv_other_charges, order.getExtraCharges());

        if (order.getOrderStatus() != null) {
            status.setText(order.getOrderStatus());

            if (order.getOrderStatus() == null ||
                    order.getOrderStatus().equals(OrderStatus.CANCELLED.name())
                    || order.getOrderStatus().equals(OrderStatus.PENDING.name())
                    || order.getOrderStatus().equals(OrderStatus.SHIPPED.name()) ) {

                    token.setText("-");
            } else if (order.getOrderStatus().equals(OrderStatus.PLACED.name()) ||
                    order.getOrderStatus().equals(OrderStatus.PROCESSING.name())) {
                token.setText(String.valueOf(order.getToken()));
            }
        }

        if(order.getStore() != null) {
            tv_store_name.setText(order.getStore().getName());
            tv_store_address.setText(order.getStore().getStoreAddress());
            tv_store_city.setText(order.getStore().getStoreCity());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy", Locale.US);
        tv_order_date.setText(dateFormat.format(order.getOrderDate()));
    }

    public static void createFragment(FragmentManager manager, String str_order_id) {
        if (manager != null && str_order_id != null) {
            Fragment fragment = manager.findFragmentByTag(str_order_id + TAG);

            if (fragment == null) {
                fragment = new SingleInvoiceFragment();

                Bundle params = new Bundle();
                params.putString(ORDER_ID, str_order_id);

                fragment.setArguments(params);
            }

            if (!fragment.isVisible()) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment, str_order_id + TAG);
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }

}
