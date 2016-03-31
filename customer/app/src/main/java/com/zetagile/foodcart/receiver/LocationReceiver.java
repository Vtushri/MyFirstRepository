package com.zetagile.foodcart.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zetagile.foodcart.services.TrackService;

public class LocationReceiver extends BroadcastReceiver {
    public LocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        TrackService.startService(context);
    }
}
