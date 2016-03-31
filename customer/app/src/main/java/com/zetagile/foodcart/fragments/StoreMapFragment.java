package com.zetagile.foodcart.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.StoreListActivity;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.fragments.catelog.CartFragment;


public class StoreMapFragment extends MapFragment {

    public static final String LOCATION = "LOCATION";
    public static final String STORE = "STORE";

    public static final String STORE_MAP_FRAGMENT_TAG = "STORE_MAP_FRAGMENT_TAG";
    Button proceed_button;
    private Store store;
    private Location user_location;

    public StoreMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapView = super.onCreateView(inflater, container, savedInstanceState);

        RelativeLayout view = new RelativeLayout(getActivity());
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.addView(mapView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        proceed_button = new Button(getActivity());
        proceed_button.setText("Next >>");

        proceed_button.setTextColor(getResources().getColor(R.color.black_color));
        proceed_button.setBackgroundColor(getResources().getColor(R.color.light_accent_color));
        proceed_button.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) proceed_button.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_RIGHT);

        proceed_button.setLayoutParams(params);
        view.addView(proceed_button);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        store = getArguments().getParcelable(STORE);
        user_location = getArguments().getParcelable(LOCATION);
        final long duration = getArguments().getLong(StoreListActivity.DURATION);
        String previous_activity = getArguments().getString(CartFragment.SELECT_STORE_REQUEST);

        GoogleMap map = getMap();

        if (store != null && store.getLatitude() != null && store.getLongitude() != null) {
            LatLng latLng = new LatLng(Double.parseDouble(store.getLatitude()), Double.parseDouble(store.getLongitude()));
            map.addMarker(new MarkerOptions().position(latLng).title(store.getName())).showInfoWindow();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 13.0f);
        map.moveCamera(update);
        }
        map.setOnMapClickListener(null);

        if (previous_activity != null && previous_activity.equals(CartFragment.SELECT_STORE_REQUEST)) {
            proceed_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent return_intent = new Intent();

                    return_intent.putExtra(StoreListActivity.SELECTED_STORE, store);
                    return_intent.putExtra(StoreListActivity.LOCATION, user_location);
                    return_intent.putExtra(StoreListActivity.DURATION, duration);

                    getActivity().setResult(Activity.RESULT_OK, return_intent);
                    getActivity().finish();

                }
            });
        } else {
            proceed_button.setText("<< BACK");
            proceed_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment mFragment = getFragmentManager().findFragmentByTag(STORE_MAP_FRAGMENT_TAG);
                    getFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            });
        }

    }
}
