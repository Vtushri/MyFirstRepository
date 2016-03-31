package com.zetagile.foodcart.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zetagile.foodcart.connections.UserConnections;
import com.zetagile.foodcart.dto.AddressLog;

import java.util.List;

public class AddUserShippingAddress extends AsyncTask<Void, Void, String> {

    List<AddressLog> addressLogList;
    Context context;
    String user_id;
    String address;
    ProgressDialog dialog;

    public AddUserShippingAddress(Context context, String user_id, String address) {
        this.context = context;
        this.user_id = user_id;
        this.address = address;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Checking", "Saving Addresses...");
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.i("HD", "Adding address with content " + address + " to user " + user_id);
        String result = UserConnections.addAddressLog(context, user_id, address);
        return result;
    }

    @Override
    protected void onPostExecute(final String result) {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}