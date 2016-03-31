package com.zetagile.foodcart;

import android.os.Bundle;

public class AppointmentActivity extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initializeActionBar(this, "Appointments");

    }

}
