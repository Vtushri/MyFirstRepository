package com.zetagile.foodcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zetagile.foodcart.asynctasks.GetSubCategoriesTask;
import com.zetagile.foodcart.connections.StoreConnections;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.Store;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;
import com.zetagile.foodcart.storage.CategoryStorage;
import com.zetagile.foodcart.storage.StoreStorage;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.network.NetworkConnection;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private static final long SPLASH_TIME = 1000;
    Button splash_login, splash_signup;
    TextView maintext, tv_continue_shop;
    private User user;
    private List<Category> categoryList;

    GetSubCategoriesTask categoriesTask;
    private View login_layout;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!NetworkConnection.checkInternetConnection(this))
            this.finish();



        tv_continue_shop = (TextView) findViewById(R.id.continue_shop);
        splash_login = (Button) findViewById(R.id.splash_login);
        splash_signup = (Button) findViewById(R.id.splash_signup);
       /* maintext = (TextView) findViewById(R.id.spalsh_maintext);*/
        login_layout = findViewById(R.id.login_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_splash);
        categoryList = new ArrayList<>();

        user = UserStorage.getUser(SplashActivity.this);
        if(user != null && !user.isGuest()) {
            tv_continue_shop.setVisibility(View.GONE);
            login_layout.setVisibility(View.GONE);
        }
        categoriesTask = new GetSubCategoriesTask(SplashActivity.this, categoryList, "CAT") {
            @Override
            protected Void doInBackground(Void... params) {
                List<Store> stores = StoreConnections.getAllStores(SplashActivity.this, "hyderabad");
                StoreStorage.putStores(SplashActivity.this, stores);
                CartStorage.clearCart(SplashActivity.this);
                if(user != null && user.isGuest())
                    UserStorage.clearUserDetails(SplashActivity.this);
                return super.doInBackground(params);
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                CategoryStorage.putCategories(SplashActivity.this, categoryList);
                progressBar.setVisibility(View.INVISIBLE);
                if (user != null && !user.isGuest()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                } else
                    initializeLoginViews();
            }
        };
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                categoriesTask.execute();
            }
        }, SPLASH_TIME);
    }
    private void initializeLoginViews() {
        login_layout.setVisibility(View.VISIBLE);
        tv_continue_shop.setVisibility(View.VISIBLE);
        splash_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startSignupActivity(SplashActivity.this, false);
            }
        });
        splash_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.startLoginActivity(SplashActivity.this);
            }
        });

        tv_continue_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LoginActivity.LOGIN_REQUEST_CODE) {
        user = UserStorage.getUser(SplashActivity.this);

        if(user != null && !user.isGuest()) {
            tv_continue_shop.setVisibility(View.GONE);
            login_layout.setVisibility(View.GONE);

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
        }
            }
    }
}
