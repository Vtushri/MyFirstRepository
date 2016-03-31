package com.zetagile.foodcart.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.dto.Cart;

public class ApplyOfferTask extends AsyncTask<Void, Void, Cart> {

    ProgressDialog dialog;

    Context context;
    String userId;
    String offer_code;

    public ApplyOfferTask(Context context, String user_id, String offer_code) {
        this.context = context;
        this.userId = user_id;
        this.offer_code = offer_code;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Checking", "Applying offer...");
    }

    @Override
    protected Cart doInBackground(Void... params) {

        return CartConnections.applyOfferOnCart(context, userId, offer_code);
    }

    @Override
    protected void onPostExecute(Cart cart_result) {
        super.onPostExecute(cart_result);

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        boolean found = false;
        if (cart_result != null) {
            for(int i = 0; cart_result.getAppliedOffers().size() > 0
                    && i < cart_result.getAppliedOffers().size(); i++) {
                if (cart_result.getAppliedOffers().get(i).getCouponCode().equals(offer_code))
                    found = true;

            }

            if(found)
                Toast.makeText(context, "Congratulations!!! Offer applied.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Sorry!!! Offer not applicable.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, "Some problem occurred. Please try again!!!", Toast.LENGTH_SHORT).show();
    }
}
