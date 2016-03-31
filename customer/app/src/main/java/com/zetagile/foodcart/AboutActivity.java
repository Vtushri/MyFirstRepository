package com.zetagile.foodcart;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.zetagile.foodcart.fragments.aboutus.EventsFragment;
import com.zetagile.foodcart.fragments.aboutus.FeedBackFragment;
import com.zetagile.foodcart.fragments.aboutus.KnowUsFragment;
import com.zetagile.foodcart.fragments.aboutus.OffersFragment;
import com.zetagile.foodcart.fragments.aboutus.PromosFragment;
import com.zetagile.foodcart.navigation.NavigationItemType;


public class AboutActivity extends BackNavigation {


    public static final String TAB_TYPE = "TABTYPE";
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_about);

        String type = getIntent().getStringExtra(TAB_TYPE);
        int position = 0;

        if (type != null && type.equals(NavigationItemType.KNOW_US_MORE.name()))
            position = 0;
        else if (type != null && type.equals(NavigationItemType.EVENTS.name()))
            position = 1;
        else if (type != null && type.equals(NavigationItemType.FEEDBACK.name()))
            position = 2;
        else if (type != null && type.equals(NavigationItemType.OFFERS.name()))
            position = 3;
        else if (type != null && type.equals(NavigationItemType.PROMOTIONS.name()))
            position = 4;


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), AboutActivity.this);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(0);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0;i < tabLayout.getTabCount();i++){
            TabLayout.Tab tabs = tabLayout.getTabAt(i);
            tabs.setCustomView(tabAdapter.getTabView(i));

        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if (tab != null)
            tab.select();
        else {
            tab = tabLayout.getTabAt(0);
            tab.select();
        }

    }

    private class TabAdapter extends FragmentStatePagerAdapter {

        String[] tabs = {"KnowUs","Events", "Feedback",  "Offers" , "Promotions" };
        Context context;

        public TabAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new KnowUsFragment(viewPager);
                case 1:
                    return new EventsFragment();

                case 2:
                    return new FeedBackFragment();
                case 3:
                    return new OffersFragment();
                case 4:

                return new PromosFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(AboutActivity.this).inflate(R.layout.custom_tabview, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabs[position]);
            return tab;
        }
    }
}



