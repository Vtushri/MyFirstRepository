package com.zetagile.foodcart.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.zetagile.foodcart.connections.OrderConnections;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.LocationUtil;
import com.zetagile.foodcart.util.SmackClientUtil;

import java.util.Date;
import java.util.List;

public class TrackService extends Service {

    private static final String TRACKING_ACTION = "com.zetagile.TRACKING_ACTION";
    SmackClientUtil smackClientUtil;

    User user;

    LocationUtil locationUtil;

    List<Order> orders;

    public TrackService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        user = UserStorage.getUser(this);

        smackClientUtil = new SmackClientUtil(this, user, null) {
            @Override
            public void onConnected() {
                super.onConnected();

                new GetCurrentOrders().execute();
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        smackClientUtil.connectToServer();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class GetCurrentOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            orders = OrderConnections.getCurrentOrders(TrackService.this, user.getUserAccountId(), 1, 10);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (orders != null && orders.size() > 0) {

                locationUtil = new LocationUtil(TrackService.this) {
                    @Override

                    public void onLocationChanged(android.location.Location loc) {
                        Order order;
                        Store store;

                        Location location = new Location();
                        location.setLatitude(String.valueOf(loc.getLatitude()));
                        location.setLongitude(String.valueOf(loc.getLongitude()));
                        for (int i = 0; i < orders.size(); i++) {
                            order = orders.get(i);
                            store = order.getStore();

                            smackClientUtil.sendLocation(store.getStoreId(), location, order);
                        }
                        stopLocationListener();
                    }
                };

                locationUtil.initializeLocation();

            } else if (orders != null && orders.size() == 0) {
                stopTracking(TrackService.this);
            }

            stopSelf();
        }
    }

    public static void startTracking(Context context) {

        Intent startIntent = new Intent(TRACKING_ACTION);
        PendingIntent startPIntent = PendingIntent.getBroadcast(context, 0, startIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                new Date().getTime() + ConfigUtil.getTrackInterval(context),
                ConfigUtil.getTrackInterval(context), startPIntent);

    }

    public static void stopTracking(Context context) {
        Intent startIntent = new Intent(TRACKING_ACTION);
        PendingIntent stopPIntent = PendingIntent.getBroadcast(context, 0, startIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(stopPIntent);
    }

    public static void startService(Context context) {

        context.startService(new Intent(context, TrackService.class));

    }
}
