package com.zetagile.foodcart.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.zetagile.foodcart.PaymentStartActivity;
import com.zetagile.foodcart.connections.OrderConnections;
import com.zetagile.foodcart.constants.PaymentModes;

public class PaymentTask extends AsyncTask<Object, Void, Boolean> {

    private static final String TAG = "PAYMENT_ASYNC_TASK";
    Activity activity;
    String gateWayResponse;

    String str_order_id;
    String str_user_id;
    PaymentModes mode;
    String addressId;

    ProgressDialog dialog_waiting;

    public PaymentTask(Activity paymentStartActivity, String order_id, String user_id, String gateWayResponse, PaymentModes mode, String addressId) {
        this.activity = paymentStartActivity;
        this.str_order_id = order_id;
        this.str_user_id = user_id;
        this.gateWayResponse = gateWayResponse;
        this.mode = mode;
        this.addressId = addressId;
    }

    @Override
    protected void onPreExecute() {
        dialog_waiting = ProgressDialog.show(activity, "Info", "Placing Order...", false);
        dialog_waiting.setProgressDrawable(new ColorDrawable(Color.parseColor("#005959")));
        dialog_waiting.show();
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        try {
            Log.i(TAG, "Placing order...");

            return OrderConnections.placeOrder(activity, str_order_id, str_user_id, gateWayResponse, addressId, mode);

        } catch (Exception e) {
            Log.i(TAG, "Error while placing the order");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean bln_result) {

        if (dialog_waiting.isShowing()) {
            dialog_waiting.dismiss();
        }

        if(activity instanceof PaymentStartActivity) {
            PaymentStartActivity paymentStartActivity = (PaymentStartActivity) activity;
            if (bln_result)
                paymentStartActivity.setResult(Activity.RESULT_OK);

            else
                paymentStartActivity.setResult(PaymentStartActivity.RESULT_SERVER_ERROR);

            paymentStartActivity.finish();
        }
    }
}