package com.zetagile.foodcart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zetagile.foodcart.fragments.login.LoginFragment;
import com.zetagile.foodcart.fragments.login.SignUpFragment;

public class LoginActivity extends AppCompatActivity {
    public static final int LOGIN_REQUEST_CODE = 5;

    public final static String LOGIN = "LOGIN";
    public final static String SIGN_UP = "SIGN_UP";
    public static final String PARAM = "PARAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_signup);

        String type = getIntent().getStringExtra(PARAM);

        if (type == null || type.equals(LOGIN)) {
            LoginFragment.createFragment(getFragmentManager());
        } else if (type.equals(SIGN_UP)) {
            String guest = getIntent().getStringExtra(SignUpFragment.GUEST_REQUEST);
            SignUpFragment.createFragment(getFragmentManager(), guest);
        }


    }

    public static void startLoginActivity(Activity context) {

        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LoginActivity.PARAM, LoginActivity.LOGIN);
        context.startActivityForResult(intent, LOGIN_REQUEST_CODE);

    }

    public static void startLoginActivity(Fragment fragment, Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LoginActivity.PARAM, LoginActivity.LOGIN);
        fragment.startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }
    public static void startSignupActivity(Activity context, boolean guest) {

        Intent intent = new Intent(context, LoginActivity.class);

        if (guest)
            intent.putExtra(SignUpFragment.GUEST_REQUEST, SignUpFragment.GUEST_REQUEST);

        intent.putExtra(LoginActivity.PARAM, LoginActivity.SIGN_UP);
        context.startActivityForResult(intent, LOGIN_REQUEST_CODE);

    }

    public static void startSignupActivity(Fragment fragment, Activity context, boolean guest) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (guest)
            intent.putExtra(SignUpFragment.GUEST_REQUEST, SignUpFragment.GUEST_REQUEST);
        intent.putExtra(LoginActivity.PARAM, LoginActivity.SIGN_UP);
        fragment.startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }
}