package com.zetagile.foodcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zetagile.foodcart.util.network.NetworkConnection;

public class NoInternetConnection extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NoInternetConnection.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);

        Button btn_try_again = (Button) findViewById(R.id.btn_try_again);
        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkConnection.checkNetworkConnection(NoInternetConnection.this)) {
                    Intent intent = new Intent(NoInternetConnection.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(NoInternetConnection.this);
                    NoInternetConnection.this.finish();
                }
            }
        });
    }
}
