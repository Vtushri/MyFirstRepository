package com.zetagile.foodcart.asynctasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.connections.ProductConnections;
import com.zetagile.foodcart.connections.UserConnections;
import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.storage.UserStorage;

import java.util.Iterator;
import java.util.List;

public class GetAddressesByUserID extends AsyncTask<Void, Void, List<AddressLog>> {

    List<AddressLog> addressLogList;
    Context context;
    String user_id;
    ProgressDialog dialog;

    public GetAddressesByUserID(Context context, String user_id) {
        this.context = context;
        this.user_id = user_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Checking", "Getting Addresses...");
    }

    @Override
    protected List<AddressLog> doInBackground(Void... params) {
        addressLogList = UserConnections.getAddressLogList(context, user_id);
        Log.i("HD", "Retrieved address list, has elements : " + addressLogList.size());
        return addressLogList;
    }

    @Override
    protected void onPostExecute(final List<AddressLog> addressLogList) {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}