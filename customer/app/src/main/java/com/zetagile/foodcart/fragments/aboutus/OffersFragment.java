package com.zetagile.foodcart.fragments.aboutus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.OffersAdapter;
import com.zetagile.foodcart.asynctasks.OfferAsync;
import com.zetagile.foodcart.dto.Offer;

import java.util.ArrayList;
import java.util.List;


public class OffersFragment extends Fragment {

    RecyclerView offerRecyclerView;
    RecyclerView.LayoutManager offerLayoutManager;
    RecyclerView.Adapter offerAdapter;
    List<Offer> offerData;
    TextView tv_no_offers;
    View offer_view;
    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        offerData = new ArrayList<>();
        offerLayoutManager = new LinearLayoutManager(activity);
        offerAdapter = new OffersAdapter(offerData);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OfferAsync offerAsync = new OfferAsync(offerData, activity, offerAdapter){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(offerData.size() == 0 ||offerData.isEmpty()){
                    tv_no_offers.setVisibility(View.VISIBLE);
                }
            }
        };
        offerAsync.execute();

        offer_view = inflater.inflate(R.layout.activity_offers, container, false);
        offerRecyclerView = (RecyclerView) offer_view.findViewById(R.id.offer_recycle);
        tv_no_offers = (TextView) offer_view.findViewById(R.id.no_offer);
        return offer_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        offerRecyclerView.setLayoutManager(offerLayoutManager);
        offerRecyclerView.setAdapter(offerAdapter);


    }
}
