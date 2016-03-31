package com.zetagile.foodcart.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.TermsAndConditions;
import com.zetagile.foodcart.asynctasks.LoginTask;
import com.zetagile.foodcart.connections.UserConnections;
import com.zetagile.foodcart.constants.UserRoles;
import com.zetagile.foodcart.dto.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    public static final String GUEST_REQUEST = "GUEST_REQUEST";
    private static final String TAG = "SIGN_UP";
    String pattern = "^[A-Za-z0-9]{4,20}$";
    View view;
    Activity activity;

    EditText et_password, et_mobile_no, et_full_name, et_email_id;
    Button btn_sign_up;
    CheckBox cb1;
    TextView tv_login, cb_text, or;
    LinearLayout images , ll_signup_password;
    ImageView img_password_eye;
    int count = 1;
    User usr_user;

    boolean goto_cart = false;
    private boolean bln_user_name_available = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        String tag = getArguments().getString(GUEST_REQUEST);
        if (tag != null && tag.equals(GUEST_REQUEST))
            goto_cart = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_signup, null);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        et_password = (EditText) view.findViewById(R.id.ed2);
        et_full_name = (EditText) view.findViewById(R.id.f_name);
        et_mobile_no = (EditText) view.findViewById(R.id.phone_no);
        et_email_id = (EditText) view.findViewById(R.id.email_id);
        cb_text = (TextView) view.findViewById(R.id.cb_text);
        cb_text.setPaintFlags(cb_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        or = (TextView) view.findViewById(R.id.or);

        cb1 = (CheckBox) view.findViewById(R.id.cb1);


        images = (LinearLayout) view.findViewById(R.id.llsocial);
        images.setVisibility(View.GONE);
        or.setVisibility(View.GONE);
        ll_signup_password = (LinearLayout) view.findViewById(R.id.sign_up_ll_password);
        tv_login = (TextView) view.findViewById(R.id.tv_login_signup);
        tv_login.setOnClickListener(this);
        btn_sign_up = (Button) view.findViewById(R.id.sign_up);

        et_full_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    et_full_name.setGravity(Gravity.LEFT);
                    et_full_name.setHint("");
                } else {
                    if (et_full_name.getText().toString().isEmpty()) {
                        et_full_name.setGravity(Gravity.CENTER);
                        et_full_name.setHint(R.string.sign_up_name_hint);
                    } else {
                        et_full_name.setGravity(Gravity.LEFT);
                    }

                }

            }
        });


        img_password_eye = (ImageView) view.findViewById(R.id.registration_eye_img);

        img_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 == 0) {

                    img_password_eye.setImageResource(R.drawable.eye_visibility_off_icon);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_password_eye.setImageResource(R.drawable.eye_visibility_icon);
                }

            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    et_password.setGravity(Gravity.LEFT);
                    et_password.setHint("");
                } else {
                    if (et_password.getText().toString().isEmpty()) {
                        et_password.setGravity(Gravity.CENTER);
                        et_password.setHint(R.string.sign_up_password_hint);
                    } else {
                        et_password.setGravity(Gravity.LEFT);
                    }
                }
            }
        });

        et_email_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_email_id.setGravity(Gravity.LEFT);
                    et_email_id.setHint("");
                } else {
                    if (et_email_id.getText().toString().isEmpty()) {
                        et_email_id.setGravity(Gravity.CENTER);
                        et_email_id.setHint(R.string.sign_up_email_hint);
                    } else {
                        et_email_id.setGravity(Gravity.LEFT);
                    }
                }
            }
        });


        et_mobile_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_mobile_no.setGravity(Gravity.LEFT);
                    et_mobile_no.setHint("");
                } else {
                    if (et_mobile_no.getText().toString().isEmpty()) {
                        et_mobile_no.setGravity(Gravity.CENTER);
                        et_mobile_no.setHint(R.string.sign_up_mobile_no_hint);
                    } else {
                        et_mobile_no.setGravity(Gravity.LEFT);
                    }
                }
            }
        });


        btn_sign_up.setOnClickListener(this);
        cb_text.setOnClickListener(this);

        if (goto_cart) {
            et_password.setVisibility(View.GONE);

            et_password.setVisibility(View.GONE);
            ll_signup_password.setVisibility(View.GONE);
            tv_login.setVisibility(View.GONE);


            btn_sign_up.setText("SUBMIT");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up:
                checkUserAndRegister();
                break;
            case R.id.tv_login_signup:
                LoginFragment.createFragment(getFragmentManager());
                break;
            case R.id.cb_text:
                startActivity(new Intent(activity, TermsAndConditions.class));
            default:
                break;
        }
    }

    private void checkUserAndRegister() {

        AvailabilityTask async_task_avail = new AvailabilityTask();
        if (et_mobile_no != null)
            async_task_avail.execute(et_mobile_no.getText().toString());

    }

    private void signUpAction() {





        if (goto_cart) {
            if (et_email_id.getText().toString().equals("")
                    || et_mobile_no.getText().toString().equals("")) {
                Toast.makeText(activity, "Please fill all mandatory fields...", Toast.LENGTH_SHORT).show();
            }
            else if (et_mobile_no.getText().length() != 10) {
                Toast.makeText(activity, "Please enter a 10 digit mobile number...", Toast.LENGTH_SHORT).show();

            }else if (!cb1.isChecked()) {
                Toast.makeText(activity, "Please Agree the terms and conditions", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(String.valueOf(et_email_id.getText())).matches()){
                Toast.makeText(activity, "Please Enter valid email id", Toast.LENGTH_SHORT).show();
            }
            else {

                String str_first_name = et_full_name.getText().toString();
                String str_phone_no = et_mobile_no.getText().toString();
                String str_email_id = et_email_id.getText().toString();


                usr_user = new User();

                usr_user.setMobileNo(str_phone_no);
                usr_user.setEmailId(str_email_id);

                usr_user.setFullName(str_first_name);

                usr_user.setUserRole(UserRoles.CUSTOMER.name());

                new SignUpTask().execute();


            }

        } else {

            if (et_full_name.getText().toString().equals("")
                    || et_password.getText().toString().equals("")
                    || et_email_id.getText().toString().equals("")
                    || et_mobile_no.getText().toString().equals("")) {
                Toast.makeText(activity, "Please fill all mandatory fields...", Toast.LENGTH_SHORT).show();

            }else if (et_password.getText().toString().contains(" ")) {
                Toast.makeText(activity, "Spaces are not allowed in password", Toast.LENGTH_SHORT).show();
            }
            else if (et_mobile_no.getText().length() != 10) {
                Toast.makeText(activity, "Please provide a valid mobile number...", Toast.LENGTH_SHORT).show();
            } else if(!Patterns.EMAIL_ADDRESS.matcher(String.valueOf(et_email_id.getText())).matches()){
                Toast.makeText(activity, "Please Enter valid email id", Toast.LENGTH_SHORT).show();
            }
            else if (!cb1.isChecked()) {
                Toast.makeText(activity, "Please Agree the terms and conditions", Toast.LENGTH_SHORT).show();
            } else {


                String str_password = et_password.getText().toString();
                String str_confirm_password = et_password.getText().toString();

                String str_full_name = et_full_name.getText().toString();
                String str_phone_no = et_mobile_no.getText().toString();
                String str_email_id = et_email_id.getText().toString();

                if (str_password.equals(str_confirm_password)) {
                    usr_user = new User();
                    usr_user.setPassword(str_password);

                    usr_user.setMobileNo(str_phone_no);
                    usr_user.setEmailId(str_email_id);

                    usr_user.setFullName(str_full_name);

                    usr_user.setUserRole(UserRoles.CUSTOMER.name());

                    new SignUpTask().execute();
                } else
                    Toast.makeText(activity, "Passwords do not match...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SignUpTask extends AsyncTask<String, Integer, User> {
        ProgressDialog dialog_waiting;

        @Override
        protected void onPreExecute() {
            dialog_waiting = ProgressDialog.show(activity, "", " Registering...", true);
            dialog_waiting.setCancelable(true);
        }

        @Override
        protected User doInBackground(String... params) {

            return UserConnections.signUp(activity, usr_user);
        }

        @Override
        protected void onPostExecute(User usr_result_user) {
            if (dialog_waiting.isShowing())
                dialog_waiting.dismiss();

            if (usr_result_user != null) {
                String user_name = usr_result_user.getMobileNo();
                String password = usr_result_user.getPassword();

                if (goto_cart)
                    new LoginTask(activity).execute(et_mobile_no.getText().toString(), password);
                else
                    new LoginTask(activity).execute(user_name, password);
            } else
                Toast.makeText(activity, "Some error occurred, please try again...", Toast.LENGTH_SHORT).show();
        }
    }

    private class AvailabilityTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            if (UserConnections.checkUserRegistered(activity, params[0]))
                bln_user_name_available = false;
            else {
                bln_user_name_available = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (bln_user_name_available)
                signUpAction();
            else
                Toast.makeText(activity, "Mobile already registered...", Toast.LENGTH_SHORT).show();
        }
    }


    public static void createFragment(FragmentManager manager, String guest) {
        if (manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null) {
                fragment = new SignUpFragment();
                Bundle params = new Bundle();
                params.putString(GUEST_REQUEST, guest);
                fragment.setArguments(params);
            }
            if (!fragment.isVisible()) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_signup_frame, fragment, TAG);
                transaction.commit();
            }
        }
    }


}
