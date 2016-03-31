package com.zetagile.foodcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class BackNavigation extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    public void setContentView(int layoutResId) {

        RelativeLayout rl_fullLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_back_navigation, null);

        FrameLayout fl_contentLayout = (FrameLayout) rl_fullLayout.findViewById(R.id.back_tool_bar_frame);

        getLayoutInflater().inflate(layoutResId, fl_contentLayout, true);

        super.setContentView(rl_fullLayout);

        toolbar = (Toolbar) findViewById(R.id.back_tool_bar);

        LinearLayout back_nav_icon = (LinearLayout) toolbar.findViewById(R.id.back_nav_icon);

        back_nav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackNavigation.this.finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
