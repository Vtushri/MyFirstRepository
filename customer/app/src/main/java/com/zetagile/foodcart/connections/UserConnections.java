package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.dto.AddressLog;
import com.zetagile.foodcart.dto.FeedBack;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.dto.UserDetails;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;
import java.util.List;

public class UserConnections {

    public static User login(Context context, String str_mobile_no, String str_password) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        UserDetails udt_user_details = new UserDetails();

        udt_user_details.setMobileNo(str_mobile_no);
        udt_user_details.setPassword(str_password);

        String str_user = null;

        ConnectionClass cc_connection = new ConnectionClass();
        try {
            str_user = new JavaToJsonString().convert_userdetails(udt_user_details);
        } catch (IOException e) {
            e.printStackTrace();
            cc_connection.displayError(context, "Input Error");
            return null;
        }

        String str_response = cc_connection.postRequest(context, connections.getLOGIN_URL(), str_user);

        if (str_response != null) {
            try {
                return new JsonStringToJava().convert_user_json(str_response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return null;

    }

    public static User signUp(Context context, User usr_user) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        String str_user = null;

        ConnectionClass cc_connection = new ConnectionClass();
        try {
            str_user = new JavaToJsonString().convert_userpojo(usr_user);
        } catch (IOException e) {
            e.printStackTrace();
            cc_connection.displayError(context, "Input Error");
            return null;
        }
        String str_response = cc_connection.postRequest(context, connections.getSIGN_UP_URL(), str_user);

        if (str_response != null) {
            try {
                return new JsonStringToJava().convert_user_json(str_response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static boolean checkUserRegistered(Context context, String str_user_name) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, connections.getUSER_NAME_AVAILABLE_URL() + str_user_name);

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static boolean markAsFbLiked(Context context, String user_id, String fb_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        String str_user = null;

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.postRequest(context, connections.getLIKED_FB_URL() + user_id + "?fbid=" + fb_id, str_user);

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static boolean submitFeedback(Context context, String user_id, FeedBack feedBack) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        String str_feedback = null;

        ConnectionClass cc_connection = new ConnectionClass();

        try {
            str_feedback = new JavaToJsonString().convert_feedback_pojo(feedBack);
        } catch (IOException e) {
            e.printStackTrace();
            cc_connection.displayError(context, "Input Error");
            return false;
        }

        String str_response = cc_connection.postRequest(context, connections.getSUBMIT_FEEDBACK_URL() + user_id, str_feedback);

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static List<FeedBack> getAllFeedback(Context context) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, connections.getGET_ALL_FEEDBACK_URL());

        try {

            return new JsonStringToJava().convertFeedbackStringToJava(str_response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static boolean editUser(Context context, User user) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        String str_user = null;

        ConnectionClass cc_connection = new ConnectionClass();

        try {
            str_user = new JavaToJsonString().convert_userpojo(user);
        } catch (IOException e) {
            e.printStackTrace();
            cc_connection.displayError(context, "Input Error");
            return false;
        }

        String str_response = cc_connection.postRequest(context, connections.getUSER_EDIT_URL() + user.getUserAccountId(), str_user);

        if (str_response != null) {
            try {
                return Boolean.parseBoolean(str_response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cc_connection.displayError(context, "Response Error");
        return false;
    }

    public static List<AddressLog> getAddressLogList(Context context, String user_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.getRequest(context, connections.getGET_ADDR_URL() + user_id);

        try {
            return new JsonStringToJava().convert_address_log_from_string(str_response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cc_connection.displayError(context, "Response Error");
        return null;
    }

    public static String addAddressLog (Context context, String user_id, String address) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();
        String str_response = cc_connection.postRequest(context, connections.getADD_ADDR_URL() + user_id, address);

        try {
            return str_response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        cc_connection.displayError(context, "Response Error");
        return null;
    }
}