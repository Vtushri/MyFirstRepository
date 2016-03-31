package com.zetagile.foodcart.asynctasks;

import android.os.AsyncTask;
import android.widget.TextView;

import com.zetagile.foodcart.NavigationDrawer;
import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;
import com.zetagile.foodcart.storage.UserStorage;

public class CartCountTask extends AsyncTask<Void, Void, Integer> {

    User user;
    TextView tv_count;
    NavigationDrawer context;

    public CartCountTask(NavigationDrawer context, User user, TextView tv_count) {
        this.user = user;
        this.tv_count = tv_count;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int count = 0;
        if (user != null)
            count = CartConnections.getCartProductCount(context, user.getUserAccountId());
        else
            count = CartStorage.getCartProductCount(context);
        return count;
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
        tv_count.setText(String.valueOf(count));
    }
}
