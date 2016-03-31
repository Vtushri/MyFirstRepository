package com.zetagile.foodcart.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.PaymentStartActivity;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.asynctasks.CartClearTask;
import com.zetagile.foodcart.asynctasks.PaymentTask;
import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.constants.PaymentModes;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.orders.OrderHistory;
import com.zetagile.foodcart.recyclerview.CustomLinearLayoutManager;
import com.zetagile.foodcart.services.TrackService;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.util.ArrayList;
import java.util.List;

public class OrderSummary extends Fragment {

    public static final String TAG = "ORDER_SUMMARY_FRAGMENT";
    private static final String ORDERID = "ORDER_ID";
    private static final String ORDERTYPE = "ORDER_TYPE";

    public static final int PAYMENT_REQUEST_CODE = 1;
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_AMOUNT = "ORDER_AMOUNT";
    public static final String ORDER_USER_ID = "ORDER_USER_ID";
    public static final String SHIPPING_ADDRESS_ID = "ADDRESS_ID";

    Activity activity;

    View order_summery_fragment_view;
    RecyclerView order_summery_recyclerView;

    RadioGroup radioGroup;
    RadioButton cod, pg_paypal;

    TextView tv_subtotal,
            tv_vattax,
            tv_servicetax,
            tv_service_charge,
            tv_other_chrges,
            tv_total,
            tv_offer_label;

    Button order_continue;

    OrderSummaryAdapter orderSummaryAdapter;
    List<Cart_ProductView> cart_product_list;

    Cart cart;
    User user;

    String order_id;
    String orderType;
    String addressId;

    PaymentModes payment_mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        user = UserStorage.getUser(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        order_summery_fragment_view = inflater.inflate(R.layout.fragment_order_summery, container, false);

        return order_summery_fragment_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        order_id = getArguments().getString(ORDERID);
        orderType = getArguments().getString(ORDERTYPE);
        addressId = getArguments().getString(SHIPPING_ADDRESS_ID);

        ConfigUtil configUtil = new ConfigUtil(activity);

        if (!configUtil.isVatApplicable())
            order_summery_fragment_view.findViewById(R.id.vat_layout).setVisibility(View.GONE);

        if (!configUtil.isServiceTaxApplicable())
            order_summery_fragment_view.findViewById(R.id.service_tax_layout).setVisibility(View.GONE);

        if (!configUtil.isServiceChargesApplicable())
            order_summery_fragment_view.findViewById(R.id.service_charges_layout).setVisibility(View.GONE);

        if (!configUtil.isOtherChargeApplicable())
            order_summery_fragment_view.findViewById(R.id.other_charges_layout).setVisibility(View.GONE);

        tv_subtotal = (TextView) order_summery_fragment_view.findViewById(R.id.order_summery_subtotal);
        tv_vattax = (TextView) order_summery_fragment_view.findViewById(R.id.order_summery_vat);
        tv_servicetax = (TextView) order_summery_fragment_view.findViewById(R.id.order_summery_servicetax);
        tv_other_chrges = (TextView) order_summery_fragment_view.findViewById(R.id.order_summary_other_charges);
        tv_service_charge = (TextView) order_summery_fragment_view.findViewById(R.id.order_summary_service_charge);
        tv_total = (TextView) order_summery_fragment_view.findViewById(R.id.order_summery_total);
        tv_offer_label = (TextView) order_summery_fragment_view.findViewById(R.id.offer_label);

        order_summery_recyclerView = (RecyclerView) order_summery_fragment_view.findViewById(R.id.order_summery_recyclerview);
        order_summery_recyclerView.setLayoutManager(new CustomLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        cart_product_list = new ArrayList<>();
        orderSummaryAdapter = new OrderSummaryAdapter(cart_product_list);
        order_summery_recyclerView.setAdapter(orderSummaryAdapter);

        radioGroup = (RadioGroup) order_summery_fragment_view.findViewById(R.id.radiogroup);

        cod = (RadioButton) order_summery_fragment_view.findViewById(R.id.cop);
        pg_paypal = (RadioButton) order_summery_fragment_view.findViewById(R.id.pg_paypal);

        if (!configUtil.isCOPApplicable())
            cod.setVisibility(View.GONE);
        else if(payment_mode == null) {
            payment_mode = PaymentModes.COP;
            cod.setChecked(true);
        }

        if (!configUtil.isPaypalApplicalble())
            pg_paypal.setVisibility(View.GONE);
        else if(payment_mode == null) {
            payment_mode = PaymentModes.PAYPAL;
            pg_paypal.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.cop) {
                    payment_mode = PaymentModes.COP;
                    System.out.println("");
                }

                else if (checkedId == R.id.pg_paypal)
                    payment_mode = PaymentModes.PAYPAL;

                else
                    Toast.makeText(activity, "Please select payment mode ", Toast.LENGTH_SHORT).show();
            }
        });

        order_continue = (Button) order_summery_fragment_view.findViewById(R.id.order_summery_continue);
        order_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment_mode != null)
                    gotoPayment();
                else
                    Toast.makeText(activity, "Please select payment option", Toast.LENGTH_SHORT);
            }
        });

        GetCartAsyncTask getCartAsyncTask = new GetCartAsyncTask();
        getCartAsyncTask.execute();
    }

    private void gotoPayment() {
        if (payment_mode != null && payment_mode.equals(PaymentModes.PAYPAL)) {
            Log.d(TAG, "Paypal gateway mode selected");

            Intent intent_payment = new Intent(activity, PaymentStartActivity.class);

            intent_payment.putExtra(ORDER_ID, order_id);
            intent_payment.putExtra(ORDER_AMOUNT, cart.getTotalPrice());
            intent_payment.putExtra(ORDER_USER_ID, user.getUserAccountId());
            intent_payment.putExtra(SHIPPING_ADDRESS_ID, addressId);

            startActivityForResult(intent_payment, PAYMENT_REQUEST_CODE);

        } else if (payment_mode != null && payment_mode.equals(PaymentModes.COP)) {
            Log.d(TAG, "COP mode selected");
            PaymentTask task = new PaymentTask(activity, order_id, user.getUserAccountId(), null, PaymentModes.COP, addressId) {
                @Override
                protected void onPostExecute(Boolean bln_result) {
                    super.onPostExecute(bln_result);
                    postOrderActions();
                }
            };
            task.execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                postOrderActions();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Payment was unsuccessful!! Please Try Again!!", Toast.LENGTH_LONG).show();
            }

            if (resultCode == PaymentStartActivity.RESULT_SERVER_ERROR) {
                Toast.makeText(activity, "Payment was successful!! But order could not be placed!! Please contact store!!!", Toast.LENGTH_LONG).show();
            }

            if (resultCode == PaymentStartActivity.RESULT_INVALID_CONFIG) {
                Toast.makeText(activity, "Invalid payment configuration!!! Please try again!!!", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void postOrderActions() {

        Log.i(TAG, "Placed order and going to invoice page");

        if (orderType != null && equals(OrderType.DRIVETHRU.name()))
            TrackService.startTracking(activity);

        CartClearTask clearTask = new CartClearTask(activity, user);
        clearTask.clearCart();

        activity.getFragmentManager().popBackStack();
        OrderHistory.createFragment(activity.getFragmentManager(), false);
    }

    private class GetCartAsyncTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            cart = CartConnections.getCart(activity, user.getUserAccountId());

            if (cart != null)
                cart_product_list.addAll(cart.getProducts());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            orderSummaryAdapter.notifyDataSetChanged();
            if (cart != null) {

//                if(Double.compare(cart.getServiceCharges(), 0.0) == 0)
//                    tv_service_charge.setVisibility(View.GONE);
//
//                if(Double.compare(cart.getServiceTax(), 0.0) == 0)
//                    tv_servicetax.setVisibility(View.GONE);
//
//                if(Double.compare(cart.getValueAddedTax(), 0.0) == 0)
//                    tv_vattax.setVisibility(View.GONE);
//
//                if(Double.compare(cart.getExtraCharges(), 0.0) == 0)
//                    tv_other_chrges.setVisibility(View.GONE);
//
                PriceAndCurrency.setPriceWithPrecision(tv_subtotal, cart.getSubTotal());
                PriceAndCurrency.setPriceWithPrecision(tv_total, cart.getTotalPrice());
                PriceAndCurrency.setPriceWithPrecision(tv_servicetax, cart.getServiceTax());
                PriceAndCurrency.setPriceWithPrecision(tv_service_charge, cart.getServiceCharges());
                PriceAndCurrency.setPriceWithPrecision(tv_other_chrges, cart.getExtraCharges());
                PriceAndCurrency.setPriceWithPrecision(tv_vattax, cart.getValueAddedTax());

                if (cart.getAppliedOffers() != null && cart.getAppliedOffers().size() > 0)
                    tv_offer_label.setText("( " + cart.getAppliedOffers().size() + " offers applied)");
                else
                    tv_offer_label.setVisibility(View.GONE);
            }
        }
    }

    public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {
        List<Cart_ProductView> cart_products_list;

        public OrderSummaryAdapter(List<Cart_ProductView> cart_products_list) {
            this.cart_products_list = cart_products_list;
        }

        @Override
        public OrderSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.invoice_product_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(OrderSummaryAdapter.ViewHolder holder, int position) {

            holder.tv_title.setText(cart_products_list.get(position).getProduct().getProductName());
            holder.tv_qty.setText(String.valueOf(cart_products_list.get(position).getQuantity()));
            double price = PriceAndCurrency.getProductPrice(cart_products_list.get(position).getProduct());
            PriceAndCurrency.setPriceWithPrecision(holder.tv_price, price);

        }

        @Override
        public int getItemCount() {
            return cart_products_list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title, tv_qty, tv_price;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.product_name);
                tv_qty = (TextView) itemView.findViewById(R.id.product_quantity);
                tv_price = (TextView) itemView.findViewById(R.id.product_price);
            }
        }
    }

    public static void createFragment(FragmentManager manager, String order_id, String order_type, String AddressID) {
        if (manager != null) {

            Fragment fragment = new OrderSummary();
            Bundle bundle = new Bundle();
            bundle.putString(ORDERID, order_id);
            bundle.putString(ORDERTYPE, order_type);
            bundle.putString(SHIPPING_ADDRESS_ID, AddressID);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, fragment, TAG);
            transaction.addToBackStack(null);
            transaction.commit();

            manager.executePendingTransactions();
        }
    }
}
