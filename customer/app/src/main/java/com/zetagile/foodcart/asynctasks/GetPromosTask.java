package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.zetagile.foodcart.adapters.BannerListAdapter;
import com.zetagile.foodcart.connections.OfferPromoEventConnections;
import com.zetagile.foodcart.dto.Promo;

import java.util.List;

public class GetPromosTask extends AsyncTask<String, Void, String> {

    List<Promo> promos;
    Context context;
    RecyclerView.Adapter adapter;

    public GetPromosTask(Context context, RecyclerView.Adapter adapter, List<Promo> promos) {
        this.adapter = adapter;
        this.promos = promos;
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        List<Promo> promos = OfferPromoEventConnections.getAllPromos(context, 1, 5);
        if (promos != null) {
            this.promos.addAll(promos);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.notifyDataSetChanged();

    }
}
