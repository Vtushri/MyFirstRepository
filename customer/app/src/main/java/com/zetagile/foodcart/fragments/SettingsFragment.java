package com.zetagile.foodcart.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.NavigationDrawer;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.asynctasks.UpdateUserTask;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.UserStorage;

public class SettingsFragment extends Fragment {
    private static final String TAG = "FRAGMENT_SETTINGS";
    ImageView iv_edit_name, iv_edit_email;
    TextView tv_user_name, tv_user_emailid, tv_user_phoneno;
    RelativeLayout ll_logout;
//    CheckBox notificationcheckBox;
    User usr_user;
    Activity activity;
    EditText dialog_name;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        usr_user = UserStorage.getUser(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View settingsView = inflater.inflate(R.layout.fragment_settings, container, false);
        tv_user_name = (TextView) settingsView.findViewById(R.id.tv_settings_name);
        tv_user_phoneno = (TextView) settingsView.findViewById(R.id.tv_settings_phoneno);
        tv_user_emailid = (TextView) settingsView.findViewById(R.id.tv_settings_emailid);
        iv_edit_name = (ImageView) settingsView.findViewById(R.id.img_name_edit);
        ll_logout = (RelativeLayout) settingsView.findViewById(R.id.ll_logout);
        iv_edit_email = (ImageView) settingsView.findViewById(R.id.img_emailid_edit);
//        notificationcheckBox = (CheckBox) settingsView.findViewById(R.id.ch_notifications);
        onclickForImages();
        return settingsView;
    }


    public void onclickForImages() {

        if (usr_user.getFullName() != null)
            tv_user_name.setText(usr_user.getFullName());
        else
            tv_user_name.setText("Full Name");

        tv_user_emailid.setText(usr_user.getEmailId());
        tv_user_phoneno.setText(usr_user.getMobileNo());

        iv_edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle("Change name");
                dialog.setContentView(R.layout.dialoglayout);
                dialog_name = (EditText) dialog.findViewById(R.id.dialog_ed_name);
                Button dialog_submit = (Button) dialog.findViewById(R.id.dialouge_submit);
                Button dialog_cancel = (Button) dialog.findViewById(R.id.dialouge_cancel);
                dialog_name.setHint(usr_user.getFullName());
                dialog_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String user_full_name = String.valueOf(dialog_name.getText());

                        if (user_full_name != null && !user_full_name.isEmpty() && user_full_name.matches("[A-Za-z0-9 ]+")) {
                            updateUserDetails(user_full_name, usr_user.getEmailId());
                        } else
                            Toast.makeText(activity, "Please enter valid name", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


        iv_edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle("Change Email Id");
                dialog.setContentView(R.layout.dialoglayout);
                dialog_name = (EditText) dialog.findViewById(R.id.dialog_ed_name);
                Button dialog_submit = (Button) dialog.findViewById(R.id.dialouge_submit);
                Button dialog_cancel = (Button) dialog.findViewById(R.id.dialouge_cancel);
                dialog_name.setHint(usr_user.getEmailId());
                dialog_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email_id = String.valueOf(dialog_name.getText());

                        if (email_id != null && !email_id.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                            updateUserDetails(usr_user.getFullName(), email_id);
                        } else
                            Toast.makeText(activity, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                dialog.setContentView(R.layout.dialog_with_two_buttons);
                TextView title = (TextView) dialog.findViewById(R.id.dialog_info);
                Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
                Button dialog_cancel = (Button) dialog.findViewById(R.id.dialog_continue);
                title.setText("Do you want to logout?");
                dialog_ok.setText("OK");
                dialog_cancel.setText("CANCEL");

                dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserStorage.clearUserDetails(activity);
                        activity.getFragmentManager().popBackStack();
                        dialog.dismiss();
                    }
                });

                dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    private void updateUserDetails(String fullName, String email_id) {
        final User user = new User();

        user.setUserAccountId(usr_user.getUserAccountId());
        user.setMobileNo(usr_user.getMobileNo());
        user.setPassword(usr_user.getPassword());

        user.setFullName(fullName);
        user.setEmailId(email_id);

            UpdateUserTask userTask = new UpdateUserTask(activity, user) {
                @Override
                protected void onPostExecute(Boolean result) {
                    super.onPostExecute(result);
                    if (result != null && result) {
                        updateUserDetails(user);

                        Toast.makeText(activity, "Your details updated successfully.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(activity, "Unsuccessfull!!! Please try again...!!!", Toast.LENGTH_SHORT).show();
                }
            };
            userTask.execute();

    }

    private void updateUserDetails(User user) {

        usr_user.setEmailId(user.getEmailId());
        usr_user.setFullName(user.getFullName());

        tv_user_emailid.setText(usr_user.getEmailId());
        tv_user_name.setText(usr_user.getFullName());

        UserStorage.putUser(activity, usr_user);
        if (activity instanceof NavigationDrawer)
            ((NavigationDrawer) activity).updateUserDetails();

    }

    public static void createFragment(FragmentManager manager) {

        if (manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null)
                fragment = new SettingsFragment();

            if (!fragment.isVisible()) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment, TAG);
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }
}
