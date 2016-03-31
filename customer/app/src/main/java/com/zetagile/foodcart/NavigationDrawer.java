package com.zetagile.foodcart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zetagile.foodcart.adapters.NavigationCategoryAdapter;
import com.zetagile.foodcart.adapters.NavigationMoreAdapter;
import com.zetagile.foodcart.asynctasks.CartCountTask;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.SettingsFragment;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.fragments.catelog.MainFragment;
import com.zetagile.foodcart.fragments.orders.OrderHistory;
import com.zetagile.foodcart.navigation.NavigationField;
import com.zetagile.foodcart.navigation.NavigationItemType;
import com.zetagile.foodcart.storage.CategoryStorage;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawer extends AppCompatActivity {

    public static LayoutType layout_type;


    public DrawerLayout dl_drawerLayout;
    protected RelativeLayout rl_fullLayout;
    protected ActionBarDrawerToggle abt_actionBarToggle;
    protected Activity activity_current_activity;
  /*  protected String str_store_id;
    List<Category> ll_category = new ArrayList<>();*/
    Toolbar toolbar;
    View cart_icon;

    private TextView tv_cart_count;

    User usr_user;

    List<Category> categories;
    NavigationCategoryAdapter categoryAdapter;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void setContentView(int layoutResID) {

        rl_fullLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_navigation_drawer, null);
        dl_drawerLayout = (DrawerLayout) rl_fullLayout.findViewById(R.id.drawer_layout);
        FrameLayout fl_frameLayout = (FrameLayout) dl_drawerLayout.findViewById(R.id.frame);

        getLayoutInflater().inflate(layoutResID, fl_frameLayout, true);

        toolbar = (Toolbar) rl_fullLayout.findViewById(R.id.tool_bar);
        LinearLayout nav_icon_layout = (LinearLayout) rl_fullLayout.findViewById(R.id.ll_nav_icon);

        nav_icon_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dl_drawerLayout.isDrawerOpen(Gravity.LEFT))
                    dl_drawerLayout.closeDrawer(Gravity.LEFT);
                else
                    dl_drawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        cart_icon = rl_fullLayout.findViewById(R.id.cart_icon);
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(activity_current_activity, CartActivity.class));
                CartFragment.createCartFragment(getFragmentManager());
            }
        });

        View search_icon = rl_fullLayout.findViewById(R.id.search_icon);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           
               SearchActivity.onSearchFragment(getFragmentManager());
            }
        });

        tv_cart_count = (TextView) rl_fullLayout.findViewById(R.id.cart_count);

        super.setContentView(rl_fullLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        layout_type = ConfigUtil.getLayoutType(this);
        usr_user = UserStorage.getUser(this);
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

       updateUserDetails();
    }

    public void updateUserDetails() {
        updateMenuHeader(rl_fullLayout);

        updateCartCount(this, usr_user, tv_cart_count);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (abt_actionBarToggle != null)
            abt_actionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (abt_actionBarToggle != null)
            abt_actionBarToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (dl_drawerLayout.isDrawerOpen(Gravity.LEFT))
            dl_drawerLayout.closeDrawers();

        else {
            if (getFragmentManager().getBackStackEntryCount() > 1)
                getFragmentManager().popBackStack();
            else {
                if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    protected void initializeActionBar(final Activity activity, final String activity_title) {
        setSupportActionBar(toolbar);

        InitializeMenu();

        activity_current_activity = activity;

        abt_actionBarToggle = new ActionBarDrawerToggle(activity_current_activity, dl_drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);


                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }

        };

        abt_actionBarToggle.setDrawerIndicatorEnabled(false);
        dl_drawerLayout.setDrawerListener(abt_actionBarToggle);
        abt_actionBarToggle.syncState();

    }

    private void updateMenuHeader(RelativeLayout layout) {

        usr_user = UserStorage.getUser(this);

        if (layout != null) {
            TextView nav_username = (TextView) layout.findViewById(R.id.guest_username);
            TextView nav_myorders = (TextView) layout.findViewById(R.id.nav_myorders);
            TextView nav_login = (TextView) layout.findViewById(R.id.login);
            TextView nav_settings = (TextView) layout.findViewById(R.id.my_settings);

            Button nav_track_order = (Button) layout.findViewById(R.id.track_my_order);

            nav_myorders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dl_drawerLayout.closeDrawers();
                    OrderHistory.createFragment(getFragmentManager(), true);
                }
            });

            nav_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dl_drawerLayout.closeDrawers();

                    SettingsFragment.createFragment(getFragmentManager());

                }
            });

            nav_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dl_drawerLayout.closeDrawers();

                    startLoginActivity();
                }
            });

            nav_track_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dl_drawerLayout.closeDrawers();
                    OrderHistory.createFragment(getFragmentManager(), false);
                }
            });

            if (usr_user == null || usr_user.isGuest()) {

                nav_myorders.setVisibility(View.INVISIBLE);
                nav_settings.setVisibility(View.INVISIBLE);
                nav_login.setVisibility(View.VISIBLE);

                nav_username.setText("Hi Guest");
            } else {
                nav_login.setVisibility(View.INVISIBLE);
                nav_myorders.setVisibility(View.VISIBLE);
                nav_settings.setVisibility(View.VISIBLE);

                if (usr_user.getFullName() != null)
                    nav_username.setText("Hi " + usr_user.getFullName());
                else if (usr_user.getFirstName() != null)
                    nav_username.setText("Hi " + usr_user.getFirstName());
                else
                    nav_username.setText("Hi ");

            }
        }
    }



    private void InitializeMenu() {

        createCategoryAdapter();

        final RecyclerView recycle_food_menu = (RecyclerView) rl_fullLayout.findViewById(R.id.recycle_food_menu);
        final LinearLayout bt_cat = (LinearLayout) rl_fullLayout.findViewById(R.id.btn_nav_cat);
        final LinearLayout bt_other = (LinearLayout) rl_fullLayout.findViewById(R.id.btn_nav_user);
        final ImageView nav_homeimage = (ImageView) rl_fullLayout.findViewById(R.id.nav_home);
        final TextView tv_nav_category = (TextView) rl_fullLayout.findViewById(R.id.tv_nav_category);
        final ImageView tv_nav_other = (ImageView) rl_fullLayout.findViewById(R.id.tv_nav_others);
        final LinearLayout other_ind = (LinearLayout) rl_fullLayout.findViewById(R.id.other_indicator);
        final LinearLayout category_ind = (LinearLayout) rl_fullLayout.findViewById(R.id.categories_indicator);

        List<NavigationField> navigationFields = new ArrayList<>();

        navigationFields.add(new NavigationField("Know us more", NavigationItemType.KNOW_US_MORE));
        navigationFields.add(new NavigationField("Events", NavigationItemType.EVENTS));
        navigationFields.add(new NavigationField("Feedback", NavigationItemType.FEEDBACK));
        navigationFields.add(new NavigationField("Offers", NavigationItemType.OFFERS));
        navigationFields.add(new NavigationField("Promotions", NavigationItemType.PROMOTIONS));
        navigationFields.add(new NavigationField("Stores", NavigationItemType.STORES));
        navigationFields.add(new NavigationField("Legal", NavigationItemType.POLICY));

        final NavigationMoreAdapter moreAdapter = new NavigationMoreAdapter(NavigationDrawer.this, dl_drawerLayout, navigationFields);

        recycle_food_menu.setLayoutManager(new LinearLayoutManager(NavigationDrawer.this, LinearLayoutManager.VERTICAL, false));
        recycle_food_menu.setAdapter(categoryAdapter);

        nav_homeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_drawerLayout.closeDrawers();
                MainFragment.createFragment(getFragmentManager());
            }
        });

        bt_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other_ind.setVisibility(View.INVISIBLE);
                category_ind.setVisibility(View.VISIBLE);
                tv_nav_category.setTypeface(Typeface.DEFAULT_BOLD);

                recycle_food_menu.setAdapter(categoryAdapter);
            }
        });

        bt_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_nav_category.setTypeface(Typeface.DEFAULT);
                category_ind.setVisibility(View.INVISIBLE);
                other_ind.setVisibility(View.VISIBLE);

                recycle_food_menu.setAdapter(moreAdapter);

            }
        });
    }

    public void createCategoryAdapter() {

        if (categories == null) {
            categories = new ArrayList<>();
            categoryAdapter = new NavigationCategoryAdapter(NavigationDrawer.this, dl_drawerLayout, categories);
        }

        if (categories.size() == 0) {
            categories.clear();
            categories.addAll(CategoryStorage.getCategories(NavigationDrawer.this));
        }

        categoryAdapter.notifyDataSetChanged();
    }

    private void updateCartCount(NavigationDrawer activity, User user, TextView tv_cart_count) {

        CartCountTask cartCountTask = new CartCountTask(activity, user, tv_cart_count);
        cartCountTask.execute();

    }

    public static void updateCartCount(int count, TextView tv_cart_count) {
        tv_cart_count.setText(String.valueOf(count));
    }

    private void startLoginActivity() {
//
//        smackClientIntent = new Intent(this, SmackClientService.class);
//        startService(smackClientIntent);
//    }
//
//    public void stopSmackService() {
//
//        smackClientIntent = new Intent(getApplication(), SmackClientService.class);
//        stopService(smackClientIntent);
//    }

        FragmentManager manager = getFragmentManager();

        Fragment fragment = manager.findFragmentByTag(CartFragment.TAG);

        if(fragment == null)
            fragment = manager.findFragmentByTag(OrderHistory.TAG_CURRENT);

        if(fragment == null)
            fragment = manager.findFragmentByTag(OrderHistory.TAG_HISTORY);

        if(fragment != null && fragment.isVisible())
            LoginActivity.startLoginActivity(fragment, this);
        else
            LoginActivity.startLoginActivity(this);
    }







    public TextView getCountTextView() {
        return tv_cart_count;
    }
}
