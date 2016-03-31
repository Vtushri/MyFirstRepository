package com.zetagile.foodcart.services.util;

import android.content.Context;
import android.util.Log;

import com.zetagile.foodcart.connections.ConnectionURLs;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;

public abstract class SmackClient implements ConnectionListener {

    private int reconnect_attempts = 5;

    private static final String TAG = "SMACK_CLIENT";
    XMPPTCPConnection connection;
    ConnectionURLs connections;

    String xmpphost;
    int xmppport;
    String xmppservice;

    String group_name = "group";

    public boolean connectToServer(Context context) {

        if (connection == null || !connection.isConnected()) {

            connections = ConnectionURLs.getInstance(context);

            xmpphost = connections.getXmppdomain();
            xmppport = connections.getXmppport();
            xmppservice = connections.getXmppservice();

            XMPPTCPConnectionConfiguration connConfig = XMPPTCPConnectionConfiguration.builder()
                    .setHost(xmpphost)
                    .setPort(xmppport)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setCompressionEnabled(false)
                    .setServiceName(xmppservice).build();

            connection = new XMPPTCPConnection(connConfig);
            connection.addConnectionListener(this);

            Roster roster = Roster.getInstanceFor(connection);
            roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);

            try {
                connection.connect();
                return true;
            } catch (SmackException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        } else
            return true;
    }

    public boolean login(String user_name, String password) {
        if (connection != null && connection.isConnected()) {
            try {
                AccountManager manager = AccountManager.getInstance(connection);

                try {
                    manager.createAccount(user_name, password);
                } catch (XMPPException.XMPPErrorException e) {
                    //If already registered
                    e.printStackTrace();
                }

                connection.login(user_name, password);
                return true;
            } catch (SmackException.AlreadyLoggedInException e) {
                e.printStackTrace();
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            } catch (SmackException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else
            return false;
    }

    public void sendMessage(String receiver_id, Message.Type message_type, String message_body) {
        try {

            Message message = new Message();
            message.setBody(message_body);
            message.setType(message_type);
            message.setTo(receiver_id + "@" + xmpphost);

            connection.sendStanza(message);

        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public String getStatus(String id) throws SmackException.NotLoggedInException, SmackException.NotConnectedException {
            Log.d(TAG, "Getting status of "+id);
        try {
            String[] groups = {group_name};
            String jid = id + "@" + xmpphost;
            Roster roster = Roster.getInstanceFor(connection);
            if (!roster.contains(jid)) {
                Log.d(TAG, "Rooster does not contain " + id + " in list. Adding to list...");
                Presence presence = new Presence(Presence.Type.subscribe);
                presence.setTo(jid);
                connection.sendStanza(presence);
                roster.createEntry(jid, jid, groups);
                String status;
                for(int i = 0; i < reconnect_attempts; i++) {

                    Thread.sleep(1000);
                    status = getStatus(id);
                    if(status != null)
                        return status;

                }

                return  null;
            }
            Presence presence = roster.getPresence(jid);

            if (presence != null) {
                Log.d(TAG, "Received status of "+id+ " is: "+ presence.getStatus());
                return presence.getStatus();
            } else {
                Log.d(TAG, "Received presence of "+id+ " is null");
                return null;
            }
        } catch (SmackException.NotConnectedException e) {
            Log.d(TAG, "Not connected to xmpp: "+e.getLocalizedMessage());
            throw new SmackException.NotConnectedException();
        } catch (SmackException.NotLoggedInException e) {
            Log.d(TAG, "Not logged into xmpp: "+e.getLocalizedMessage());
            throw new SmackException.NotLoggedInException();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean disconnect() {
        try {
            if (connection != null && connection.isConnected()) {
                connection.disconnect();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        if (connection != null)
            return connection.isConnected();
        return false;
    }

    public void setMessageListener(final ChatMessageListener listener) {
        if (connection != null) {
            ChatManager manager = ChatManager.getInstanceFor(connection);
            manager.addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally) {
                    chat.addMessageListener(listener);
                }
            });
        }

    }

    public abstract void onConnected(SmackClient client);

    @Override
    public void connected(XMPPConnection connection) {
        onConnected(this);
    }


    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception e) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectingIn(int seconds) {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }
}
