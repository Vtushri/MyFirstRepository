package com.zetagile.foodcart;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zetagile.foodcart.adapters.StoreListAdapter;
import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.fragments.StoreMapFragment;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.storage.StoreStorage;
import com.zetagile.foodcart.util.LocationUtil;

import java.util.List;


public class StoreListActivity extends BackNavigation {

    public static final String SELECTED_STORE = "SELECTED_STORE";
    private static final int LOCATION_SETTING_CODE = 3;
    public static final String LOCATION = "LOCATION";
    public static final String DURATION = "DURATION";

    ProgressDialog locationDialog;

    ListView store_list_view;
    StoreListAdapter adapter;
    StoreMapFragment mFragment;

    Location location;
    String select_store_request;
    String order_type;
    private LocationUtil locationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        List<Store> stores_list = StoreStorage.getAllStores(this);
        location = new Location();
        locationDialog = new ProgressDialog(this);
        locationDialog.setMessage("Getting your location...");
        adapter = new StoreListAdapter(StoreListActivity.this, stores_list, R.layout.store_list_item, location);
        adapter.setNotifyOnChange(true);


        store_list_view = (ListView) findViewById(R.id.store_list_view);
        store_list_view.setAdapter(adapter);


        //Store request related code
        select_store_request = getIntent().getStringExtra(CartFragment.SELECT_STORE_REQUEST);

        TextView tv_store_list_title = (TextView) findViewById(R.id.tv_store_list_title);
        if (select_store_request != null && select_store_request.equals(CartFragment.SELECT_STORE_REQUEST)) {

            toolbar.setVisibility(View.GONE);
            tv_store_list_title.setText("Select Store");
            order_type = getIntent().getStringExtra(CartFragment.ORDER_TYPE);

            if(order_type != null && order_type.equals(OrderType.DRIVETHRU.name())) {
                locationDialog.setCancelable(false);
            }
        } else {
            tv_store_list_title.setText("Stores");
        }

        //Location related code

        if (!LocationUtil.isLocationEnabled(this))
            createInfoDialog();
        else if(locationDialog != null && !locationDialog.isShowing())
            locationDialog.show();

        locationUtil = new LocationUtil(this) {
            @Override
            public void onLocationChanged(android.location.Location loc) {
                location.setLatitude(String.valueOf(loc.getLatitude()));
                location.setLongitude(String.valueOf(loc.getLongitude()));
                if (locationDialog != null && locationDialog.isShowing())
                    locationDialog.dismiss();
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                stopLocationListener();
            }
        };
        locationUtil.initializeLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_SETTING_CODE) {
            if (LocationUtil.isLocationEnabled(this)) {
                if (location.getLongitude() == null && locationDialog != null && !locationDialog.isShowing())
                    locationDialog.show();
            } else if (order_type != null && order_type.equals(OrderType.DRIVETHRU.name()))
                createInfoDialog();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (locationUtil != null)
            locationUtil.startLocationListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationUtil != null)
            locationUtil.stopLocationListener();
    }

    private void createInfoDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_with_two_buttons);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);

        ((TextView) dialog.findViewById(R.id.dialog_title)).setText("Location");
        Button btn_continue = (Button) dialog.findViewById(R.id.dialog_continue);
        Button btn_ok = (Button) dialog.findViewById(R.id.dialog_ok);
        TextView tv_info = (TextView) dialog.findViewById(R.id.dialog_info);

        dialog.setCancelable(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, LOCATION_SETTING_CODE);

            }
        });

        if (select_store_request != null && select_store_request.equals(CartFragment.SELECT_STORE_REQUEST)) {
            if (order_type != null && order_type.equals(OrderType.DRIVETHRU.name())) {
                tv_info.setText("Your order is in pick up mode...\n\n Enable your location services for Drive thru.");
                btn_continue.setText("Cancel");
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                        finish();
            }
        });


                dialog.show();
            }

        } else {
            tv_info.setText("Enable your location to find your distance from stores.");
            btn_continue.setText("Continue");
            btn_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            getFragmentManager().beginTransaction().remove(mFragment).commit();
            mFragment = null;
        } else {
            setResult(Activity.RESULT_CANCELED);
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    public void createMapFragment(Store store, long time_in_milliseconds) {
        mFragment = new StoreMapFragment();
        Bundle b = new Bundle();

        b.putParcelable(StoreMapFragment.STORE, store);
        b.putParcelable(StoreMapFragment.LOCATION, location);
        b.putString(CartFragment.SELECT_STORE_REQUEST, select_store_request);
        b.putLong(StoreListActivity.DURATION, time_in_milliseconds);
        mFragment.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.store_list_layout, mFragment, StoreMapFragment.STORE_MAP_FRAGMENT_TAG).commit();
    }


}
