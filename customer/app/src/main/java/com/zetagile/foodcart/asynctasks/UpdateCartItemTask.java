package com.zetagile.foodcart.asynctasks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.zetagile.foodcart.NavigationDrawer;
import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;

import org.json.JSONException;

public class UpdateCartItemTask extends AsyncTask<Object, Void, Cart> {

    protected ProgressDialog dialog_waiting;
    Cart_ProductView product;
    Activity activity;
    User user;

    public UpdateCartItemTask(Activity activity, Cart_ProductView product, User user) {
        super();

        this.activity = activity;
        this.product = product;
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        dialog_waiting = ProgressDialog.show(activity, "Please wait...", "Please wait for a moment...");
    }

    @Override
    protected Cart doInBackground(Object... params) {

        try {
            if (user != null) {
                return CartConnections.updateProductOfCart(activity, user.getUserAccountId(), product.getProduct().getProductId(), product.getQuantity());
            } else {
                return CartStorage.updateCart(product, activity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Cart cart_result) {
        super.onPostExecute(cart_result);
        if (!activity.isFinishing() && (dialog_waiting != null && dialog_waiting.isShowing()))
            dialog_waiting.dismiss();

        if (activity instanceof NavigationDrawer && cart_result != null && cart_result.getProducts() != null) {
            int count = cart_result.getProducts().size();
            NavigationDrawer.updateCartCount(count, ((NavigationDrawer) activity).getCountTextView());
        }
    }
}
