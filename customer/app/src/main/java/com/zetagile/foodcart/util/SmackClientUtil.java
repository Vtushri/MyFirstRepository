package com.zetagile.foodcart.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zetagile.foodcart.constants.MessageType;
import com.zetagile.foodcart.constants.OrderType;
import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.dto.Location;
import com.zetagile.foodcart.dto.Order;
import com.zetagile.foodcart.dto.OrderNotification;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.services.util.SmackClient;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;


public class SmackClientUtil {

    private static final String TAG = "SMACK_CLIENT_UTIL";
    private SmackClient client;
    Context context;
    CartFragment.SmackMessageListener listener;
    User user;

    public SmackClientUtil(Context context, User user, CartFragment.SmackMessageListener listener) {
        this.user = user;
        this.context = context;
        this.listener = listener;
    }

    public void connectToServer() {

        if (client == null || !client.isConnected()) {

            client = new SmackClient() {
                @Override
                public void onConnected(SmackClient client) {
                    SmackClientUtil.this.onConnected();
                }
            };

        }

        ClientConnectTask connectTask = new ClientConnectTask();
        connectTask.execute();
    }

    public void onConnected() {

        if (listener != null)
            client.setMessageListener(listener);

        if (user != null) {
            client.login(user.getUserAccountId(), "123456");
            System.out.println(user.getUserAccountId() + "----->" + "Connection Created");
        }
    }

    public boolean isConnected() {
        if(client != null && client.isConnected())
            return true;
        return false;
    }

    public void disconnect() {
        if(client != null && client.isConnected())
            client.disconnect();
    }

    private class ClientConnectTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            if (client.connectToServer(context)) {
                return true;
            }
            return false;
        }

    }

    public String getStoreStatus(String store_id) {
        try {
            if (client != null && client.isConnected())
                return client.getStatus(store_id);
            else
                Log.d(TAG, "SmackClient is not initialized or not connected ");

        } catch (SmackException.NotLoggedInException e) {
            Log.d(TAG, "Client "+store_id+" not logged into xmpp. Logging in... ");
            client.login(user.getUserAccountId(), "123456");
        } catch (SmackException.NotConnectedException e) {
            Log.d(TAG, "Client "+store_id+" not connected to xmpp. Trying to connect again... ");
            connectToServer();
        }

        return null;
    }

    public void sendDrivethruNotification(String store_id, Order ord_order, long time_reach_store, Location location) {
        if (user != null && client.isConnected()) {

            client.login(user.getUserAccountId(), "123456");

            OrderNotification notification = new OrderNotification();

            notification.setLocation(location);
            notification.setArrivalTime(time_reach_store);
            notification.setOrderType(OrderType.DRIVETHRU.name());

            notification.setMessageType(MessageType.NOTIFY.name());
            notification.setOrder(ord_order);
            notification.setFrom(user);

            String str_notification = new JavaToJsonString().convert_order_notification(notification);

            client.sendMessage(store_id, Message.Type.chat, str_notification);
        }
    }

    public void sendPickupNotification(String store_id, Order ord_order, long sec_arrivalTime, AddressLog shippingAddress) {
        if (user != null && client.isConnected()) {

            //#TODO : Investigate if we can avoid hardcode passwords
            client.login(user.getUserAccountId(), "123456");

            OrderNotification notification = new OrderNotification();

            //notification.setOrderType(OrderType.PICKUP.name());
            //We will send the same notification for PICKUP and HOMEDELIVERY modes
            notification.setOrderType(ord_order.getOrderType());
            notification.setMessageType(MessageType.NOTIFY.name());
            notification.setOrder(ord_order);
            notification.setFrom(user);
            if (ord_order.getOrderType().equals(OrderType.HOMEDELIVERY.name())) {
                notification.setAddressLog(shippingAddress);
            }

            notification.setArrivalTime(sec_arrivalTime);

            String str_notification = new JavaToJsonString().convert_order_notification(notification);

            client.sendMessage(store_id, Message.Type.chat, str_notification);
        }
    }


    public void sendLocation(final String store_id, final Location location, Order order) {
        if (user != null && client.isConnected()) {

                client.login(user.getUserAccountId(), "123456");

            OrderNotification locationNotification = new OrderNotification();

            locationNotification.setLocation(location);
            locationNotification.setMessageType(MessageType.LOCATION.name());
            locationNotification.setOrder(order);
            locationNotification.setFrom(user);

            String str_notification = new JavaToJsonString().convert_order_notification(locationNotification);

            client.sendMessage(store_id, Message.Type.chat, str_notification);
        }
    }
}
