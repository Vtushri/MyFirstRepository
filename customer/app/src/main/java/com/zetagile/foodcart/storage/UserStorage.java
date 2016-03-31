package com.zetagile.foodcart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.io.IOException;

public class UserStorage {
    public static final String USER = "USER";
    public static final String USER_PREFS = "USER_PREFS";

    public static User getUser(Context context) {
        User usr_user = null;
        SharedPreferences prefs_user = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);

        if (prefs_user != null) {

            String user_string = prefs_user.getString(USER, null);

            try {
                if (user_string != null) {
                    usr_user = new JsonStringToJava().convert_user_json(user_string);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return usr_user;
    }

    public static boolean clearUserDetails(Context context) {
        try {
            SharedPreferences.Editor editor_user_prefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();

            editor_user_prefs.clear();
            editor_user_prefs.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean putUser(Context context, User usr_user) {
        try {
            SharedPreferences prefs_user = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs_user.edit();

            editor.putString(USER, new JavaToJsonString().convert_userpojo(usr_user));
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
