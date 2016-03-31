package com.zetagile.foodcart;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.zetagile.foodcart.fragments.catelog.MainFragment;
import com.zetagile.foodcart.util.network.NetworkConnection;


public class MainActivity extends NavigationDrawer {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActionBar(this, "Home");

        if (!NetworkConnection.checkInternetConnection(this))
            this.finish();

        MainFragment.createFragment(getFragmentManager());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }
}
