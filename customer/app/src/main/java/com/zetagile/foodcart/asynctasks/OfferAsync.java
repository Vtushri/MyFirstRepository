package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.zetagile.foodcart.connections.OfferPromoEventConnections;
import com.zetagile.foodcart.dto.Offer;

import java.util.List;

public class OfferAsync extends AsyncTask<String, Void, String> {

    Context context;
    List<Offer> offers;
    RecyclerView.Adapter adapter;

    public OfferAsync(List<Offer> offers, Context context, RecyclerView.Adapter adapter) {
        this.offers = offers;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... params) {


        List<Offer> offerData = OfferPromoEventConnections.getAllOffers(context, 1, 5);

        if (offerData != null && offerData.size() > 0) {
            offers.addAll(offerData);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.notifyDataSetChanged();
    }
}

