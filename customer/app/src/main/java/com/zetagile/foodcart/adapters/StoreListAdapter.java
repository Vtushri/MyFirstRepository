package com.zetagile.foodcart.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.StoreListActivity;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.util.LatLngDistance;
import com.zetagile.foodcart.util.time.TimeDateDistanceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreListAdapter extends ArrayAdapter<Store> {
    StoreListActivity activity;
    List<Store> stores_list ;
    Map<String, Long> storeDistances;
    Map<String, Long> storeDurations;
    Location user_location;

    public StoreListAdapter(StoreListActivity activity, List<Store> stores, int resource, Location location) {
        super(activity, resource, stores);
        this.stores_list = stores;
        this.activity = activity;
        this.user_location = location;
        this.storeDistances = new HashMap<>();
        this.storeDurations = new HashMap<>();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final StoreHolder holder;

        if (row == null) {

            LayoutInflater inflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater1.inflate(R.layout.store_list_item, parent, false);

            holder = new StoreHolder();

            holder.ll_view_map = (LinearLayout) row.findViewById(R.id.ll_view_map);
            holder.name = (TextView) row.findViewById(R.id.store_name);
            holder.store_address = (TextView) row.findViewById(R.id.store_address);
            holder.store_city = (TextView) row.findViewById(R.id.store_city);
            holder.store_timings = (TextView) row.findViewById(R.id.store_timing);
            holder.pick_store = (Button) row.findViewById(R.id.btn_pick_store);
            holder.time = (TextView) row.findViewById(R.id.store_time);
            holder.distance = (TextView) row.findViewById(R.id.store_distance);

            row.setTag(holder);
        } else
            holder = (StoreHolder) row.getTag();

        if (user_location.getLatitude() != null
                && user_location.getLongitude() != null
                && !user_location.getLongitude().equals("0")
                && !user_location.getLatitude().equals("0")) {

            holder.distance.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);

            double store_latitude = 0;
            double store_longitude = 0;
            if(stores_list.get(position).getLatitude() != null) {
                store_latitude = Double.parseDouble(stores_list.get(position).getLatitude());
                store_longitude = Double.parseDouble(stores_list.get(position).getLongitude());
            }

            double user_latitude = Double.parseDouble(user_location.getLatitude());
            double user_longitude = Double.parseDouble(user_location.getLongitude());

            LatLng user_latLng = new LatLng(user_latitude, user_longitude);
            LatLng store_latLng = new LatLng(store_latitude, store_longitude);

            LatLngDistance findDistance = new LatLngDistance() {
                @Override
                public void onDistanceCalculated(long distance_meters, long time_milliseconds) {
                    Long distance = storeDistances.get(stores_list.get(position).getStoreId());
                    Long duration = storeDurations.get(stores_list.get(position).getStoreId());
                    if (distance == null || duration == null) {
                        storeDistances.put(stores_list.get(position).getStoreId(), distance_meters);
                        storeDurations.put(stores_list.get(position).getStoreId(), time_milliseconds);
                        distance = distance_meters;
                        duration = time_milliseconds;
                    }
                    holder.time.setText(TimeDateDistanceUtil.getDurationHrMin(duration));
                    holder.distance.setText(TimeDateDistanceUtil.getDistance(distance));
                    holder.time_in_milliseconds = time_milliseconds;
                }
            };
            findDistance.getDirectionUrl(user_latLng, store_latLng);

        } else {
            holder.distance.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);
        }

        Store store = stores_list.get(position);
        holder.name.setText(store.getName());
        holder.store_address.setText(store.getStoreAddress());
        holder.store_city.setText(store.getStoreCity());
        holder.store_timings.setText(TimeDateDistanceUtil.getDurationAmPm(store.getWorkingFrom()) + " - " + TimeDateDistanceUtil.getDurationAmPm(store.getWorkingTill()));

        holder.ll_view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.createMapFragment(stores_list.get(position), holder.time_in_milliseconds);
            }
        });

        holder.pick_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_intent = new Intent();

                return_intent.putExtra(StoreListActivity.SELECTED_STORE, stores_list.get(position));
                return_intent.putExtra(StoreListActivity.LOCATION, user_location);
                return_intent.putExtra(StoreListActivity.DURATION, holder.time_in_milliseconds);
                activity.setResult(Activity.RESULT_OK, return_intent);
                activity.finish();
            }
        });

        return row;
    }

    private class StoreHolder {
        LinearLayout ll_view_map;
        TextView name;
        TextView store_address;
        TextView store_city;
        Button pick_store;
        TextView time;
        TextView distance;
        TextView store_timings;
        long time_in_milliseconds;
//        TextView vertical_line;
    }
}