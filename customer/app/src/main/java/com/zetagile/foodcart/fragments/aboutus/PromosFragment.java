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
import com.zetagile.foodcart.adapters.PromosAdapter;
import com.zetagile.foodcart.asynctasks.GetPromosTask;
import com.zetagile.foodcart.dto.Promo;

import java.util.ArrayList;
import java.util.List;

public class PromosFragment extends Fragment {

    RecyclerView promoRecyclerView;
    List<Promo> listData;
    View promo_view;
    Activity activity;
    TextView tv_no_promos;
    private RecyclerView.Adapter promoAdapter;
    private RecyclerView.LayoutManager promoLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        listData = new ArrayList<>();
        promoLayoutManager = new LinearLayoutManager(activity);
        promoAdapter = new PromosAdapter(listData);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        promo_view = inflater.inflate(R.layout.fragment_promos, container, false);
        promoRecyclerView = (RecyclerView) promo_view.findViewById(R.id.promo_recycle);
        tv_no_promos = (TextView) promo_view.findViewById(R.id.no_promos);


        GetPromosTask getPromosTask = new GetPromosTask(activity, promoAdapter, listData) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (listData.size() == 0 || listData.isEmpty()) {
                    tv_no_promos.setVisibility(View.VISIBLE);
                }
            }
        };
        getPromosTask.execute();

        return promo_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        promoRecyclerView.setLayoutManager(promoLayoutManager);
        promoRecyclerView.setAdapter(promoAdapter);
    }
}
