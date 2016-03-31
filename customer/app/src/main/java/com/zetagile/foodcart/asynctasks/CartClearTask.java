package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.zetagile.foodcart.NavigationDrawer;
import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;


public class CartClearTask {

    Context context;
    User usr_user;

    public CartClearTask(Context context, User usr_user) {
        this.context = context;
        this.usr_user = usr_user;
    }

    public void clearCart() {


        if (usr_user != null) {

            CartClearAsyncTask task = new CartClearAsyncTask();
            task.execute(usr_user.getUserAccountId());

        } else {
            CartStorage.clearCart(context);
        }
    }

    private class CartClearAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {

            String str_user_id = urls[0];
            return CartConnections.clearCart(context, str_user_id);
        }

        @Override
        protected void onPostExecute(Boolean bln_response) {
            super.onPostExecute(bln_response);

            if (bln_response)
//                NavigationDrawer.resetCartCount();
            ;
            else
                Toast.makeText(context, "Could not process your request!! Please try again!!", Toast.LENGTH_SHORT).show();
        }
    }
}
