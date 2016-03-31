package com.zetagile.foodcart.asynctasks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.zetagile.foodcart.connections.UserConnections;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.SettingsFragment;
import com.zetagile.foodcart.storage.UserStorage;

public class UpdateUserTask extends AsyncTask<String, Integer, Boolean> {
    ProgressDialog dialog_waiting;
    User user;
    Activity activity;

    public UpdateUserTask(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog_waiting = ProgressDialog.show(activity, "Changing Username", "loading...");
        dialog_waiting.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return UserConnections.editUser(activity, user);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(dialog_waiting.isShowing())
            dialog_waiting.dismiss();
        if(result != null &&  result) {

            UserStorage.putUser(activity, user);
            SettingsFragment.createFragment(activity.getFragmentManager());

        } else
            Toast.makeText(activity, "Unsuccessfull!!! Please try again...!!!", Toast.LENGTH_SHORT).show();
    }
}
