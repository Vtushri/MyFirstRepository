package com.zetagile.foodcart.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.ForgotPasswordActivity;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.asynctasks.LoginTask;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG="LOGIN_FRAGMENT";

    View view;
    Activity activity;
    private EditText et_mobile_no;
    private EditText et_password;
    private Button btn_login;
    private Button bt_register;
    private LinearLayout fb_google;
   /* private TextView tv_forgot_password;*/
    /*private CheckBox cb_show_password;*/
    ImageView img_password_eye;
    int count = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_login, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_mobile_no = (EditText) view.findViewById(R.id.tv_mobile_no);
        et_password = (EditText) view.findViewById(R.id.tv_password);
        btn_login = (Button) view.findViewById(R.id.btn_choose_option1);
        bt_register = (Button) view.findViewById(R.id.bt_register);
       /* tv_forgot_password = (TextView) view.findViewById(R.id.tv_forgot_password);*/
       /* cb_show_password = (CheckBox) view.findViewById(R.id.cb_show_password);*/
        fb_google = (LinearLayout) view.findViewById(R.id.fb_google);
        fb_google.setVisibility(View.GONE);
        img_password_eye = (ImageView) view.findViewById(R.id.login_eye_img);

        img_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    count++;
                if(count % 2 == 0){

                    img_password_eye.setImageResource(R.drawable.eye_visibility_off_icon);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_password_eye.setImageResource(R.drawable.eye_visibility_icon);
                }

            }
        });

        btn_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
       /* tv_forgot_password.setOnClickListener(this);*/


        /*cb_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                else
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });*/


        et_mobile_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_mobile_no.setGravity(Gravity.LEFT);
                    et_mobile_no.setHint("");
                } else {
                    if (et_mobile_no.getText().toString().isEmpty()) {
                        et_mobile_no.setGravity(Gravity.CENTER);
                        et_mobile_no.setHint(R.string.login_mobile_no);
                    } else {
                        et_mobile_no.setGravity(Gravity.LEFT);
                    }
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
                        et_password.setHint(R.string.login_password);
                    } else {
                        et_password.setGravity(Gravity.LEFT);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_choose_option1:
                if (et_mobile_no.getText().toString().equals(""))
                    Toast.makeText(activity, "Mobile number should not be empty", Toast.LENGTH_SHORT).show();

                else if (et_password.getText().toString().equals(""))
                    Toast.makeText(activity, "Password should not be empty", Toast.LENGTH_SHORT).show();
                else {
                    String user_name = et_mobile_no.getText().toString();
                    String password = et_password.getText().toString();

                    new LoginTask(activity).execute(user_name, password);
                }
                break;

           /* case R.id.tv_forgot_password:
                startActivity(new Intent(activity, ForgotPasswordActivity.class));
                break;*/

            case R.id.bt_register:
                SignUpFragment.createFragment(getFragmentManager(),null);
                break;

            default:
                break;
        }
    }

     public static void createFragment(FragmentManager manager) {
        if (manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null)
                fragment = new LoginFragment();

            if (!fragment.isVisible()) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_signup_frame, fragment, TAG);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }

}
