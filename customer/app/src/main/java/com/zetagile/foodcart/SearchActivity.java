package com.zetagile.foodcart;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zetagile.foodcart.adapters.ProductGridRecycleAdapter;
import com.zetagile.foodcart.connections.ProductConnections;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends Fragment {

    private static final String TAG = "SEARCH_FRAGMENT";
    View fragmentView;
    EditText search_text;
    String search_string;
    Activity activity;
    List<ProductView> productViews;
    RecyclerView search_recycle;
    ProductGridRecycleAdapter productGridRecycleAdapter;
    LayoutType layoutType;
    ImageView img_search;
    TextView no_items_found;
    private long mLastClickTime = 0;
    ProgressBar searchprogressBar;

    public SearchActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        layoutType = ConfigUtil.getLayoutType(activity);
        productViews = new ArrayList<>();
        productGridRecycleAdapter = new ProductGridRecycleAdapter(activity, productViews);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        search_text.setOnFocusChangeListener(null);
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_search, container, false);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchprogressBar = (ProgressBar) fragmentView.findViewById(R.id.search_progress);
        no_items_found = (TextView) fragmentView.findViewById(R.id.no_search_item);

        search_text = (EditText) fragmentView.findViewById(R.id.ed_search);
        img_search = (ImageView) fragmentView.findViewById(R.id.img_search);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    System.out.println("BUTTON CLICKED TWICE");
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                InputMethodManager inputManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                search_string = search_text.getText().toString().toLowerCase(Locale.getDefault());

                productViews.clear();
                SearchTask searchTask = new SearchTask(activity, search_string, productViews, productGridRecycleAdapter);
                searchTask.execute();
            }
        });

        search_recycle = (RecyclerView) fragmentView.findViewById(R.id.search_recycle);

        if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT))
            search_recycle.setLayoutManager(new GridLayoutManager(activity, 2));
        else
            search_recycle.setLayoutManager(new GridLayoutManager(activity, 1));

        search_recycle.setAdapter(productGridRecycleAdapter);


        search_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    search_text.setHint(" ");
                    search_text.setGravity(Gravity.LEFT);
                } else if (search_text.getText().toString().isEmpty()) {
                    search_text.setHint("Enter Name");
                    search_text.setGravity(Gravity.CENTER);
                } else search_text.setGravity(Gravity.LEFT);
            }
        });
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    img_search.setVisibility(View.VISIBLE);
                } else {
                    img_search.setVisibility(View.GONE);
                }
                productViews.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {
                productViews.clear();
                productGridRecycleAdapter.notifyDataSetChanged();
            }
        });
    }
    public static void onSearchFragment(FragmentManager manager) {
        if (manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null)
                fragment = new SearchActivity();

            if (!fragment.isVisible()) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment, TAG);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        }
    }


    class SearchTask extends AsyncTask<Void, Void, Void> {
        Activity activity;
        String ser_str;
        List<ProductView> productViewList;
        RecyclerView.Adapter adapter;


        public SearchTask(Activity activity, String ser_str, List<ProductView> productViewList, RecyclerView.Adapter adapter) {
            this.activity = activity;
            this.ser_str = ser_str;
            this.productViewList = productViewList;
            this.adapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            searchprogressBar.setVisibility(View.VISIBLE);
            no_items_found.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<ProductView> productViews = ProductConnections.getAllProductsByName(activity, ser_str, 1, 10);
            if (productViews != null) {
                productViewList.addAll(productViews);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            searchprogressBar.setVisibility(View.GONE);
            if (productViews.size() == 0) {
                no_items_found.setVisibility(View.VISIBLE);
            } else {
                no_items_found.setVisibility(View.INVISIBLE);
            }
            adapter.notifyDataSetChanged();

        }
    }

}
