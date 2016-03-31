package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.util.PriceAndCurrency;

public class GetCartTotalPriceTask extends AsyncTask<Void, Void, Double> {

    TextView totalPrice;
    Context context;
    String userId;
    Order ord_order;

    public GetCartTotalPriceTask(Context context, String user_id, TextView textView, Order ord_order) {
        totalPrice = textView;
        this.context = context;
        this.userId = user_id;
        this.ord_order = ord_order;
    }

    @Override
    protected Double doInBackground(Void... params) {

//        ord_order.setTotalAmount(CartConnections.getCartTotalPrice(context, userId));

        return ord_order.getTotalAmount();
    }

    @Override
    protected void onPostExecute(Double aDouble) {
        super.onPostExecute(aDouble);

        PriceAndCurrency.setPriceWithPrecision(totalPrice, ord_order.getTotalAmount());
    }
}
