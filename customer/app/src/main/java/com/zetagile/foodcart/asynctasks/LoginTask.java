package com.zetagile.foodcart.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zetagile.foodcart.connections.UserConnections;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;
import com.zetagile.foodcart.storage.UserStorage;

import java.util.List;

public class LoginTask extends AsyncTask<String, Integer, String> {

    User usr_user;
    Activity context;
    ProgressDialog progress_dialog;
    List<Cart_ProductView> list_cart_products;

    public LoginTask(Activity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress_dialog = ProgressDialog.show(context, "Login", "Please wait for a moment...");
        progress_dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String str_user_name = params[0];
            String str_password = params[1];

            usr_user = UserConnections.login(context, str_user_name, str_password);

            if (usr_user != null) {
                UserStorage.putUser(context, usr_user);
                Cart cart = CartStorage.getCart(context);

                if (cart != null && cart.getProducts() != null)
                    list_cart_products = cart.getProducts();

                CartStorage.clearCart(context);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e("LOGIN", "ERROR: " + exception.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String json_user) {

        if (progress_dialog.isShowing())
            progress_dialog.dismiss();

        if (usr_user != null) {

            for (int int_product_count = 0; int_product_count < list_cart_products.size(); int_product_count++) {

                UpdateCartItemTask task = new UpdateCartItemTask(context, list_cart_products.get(int_product_count), usr_user);
                task.execute();
            }

            context.finish();
        } else
            Toast.makeText(context, "Invalid username or password... Please try again", Toast.LENGTH_LONG).show();
    }
}