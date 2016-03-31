package com.zetagile.foodcart.fragments.catelog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.ProductGridRecycleAdapter;
import com.zetagile.foodcart.connections.ProductConnections;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.util.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class GridFragment extends Fragment {

    public static final String TAG = "TAG_GRID_FRAGMENT";
    public static final String PARAM_URL = "URL";
    private static final String PARAM_TITLE = "PARAM_TITLE";

    View v_layout_view;
    RecyclerView gv_product_grid_view;
    TextView tv_category_name;

    List<ProductView> productsList;
    ProductGridRecycleAdapter adapter_product_grid;

    Activity activity;

    String URL;
    String TITLE;

    int int_page = 0;

    LayoutType layoutType;
    public GridFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        layoutType = ConfigUtil.getLayoutType(activity);
        productsList = new ArrayList<>();
        adapter_product_grid = new ProductGridRecycleAdapter(activity, productsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProductsListTask async_task_product_list = new ProductsListTask();
        async_task_product_list.execute();

        URL = getArguments().getString(PARAM_URL);
        TITLE = getArguments().getString(PARAM_TITLE);

        v_layout_view = inflater.inflate(R.layout.fragment_product_grid, container, false);

        return v_layout_view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_category_name = (TextView) v_layout_view.findViewById(R.id.category_name);
        tv_category_name.setText(TITLE);

      gv_product_grid_view = (RecyclerView) v_layout_view.findViewById(R.id.product_grid_view);
        if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT))
            gv_product_grid_view.setLayoutManager(new GridLayoutManager(activity, 2));
        else
        gv_product_grid_view.setLayoutManager(new GridLayoutManager(activity, 1));
        gv_product_grid_view.setAdapter(adapter_product_grid);

    }

    private class ProductsListTask extends AsyncTask<String, Void, ArrayList<ProductView>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (int_page <= 1) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }
        }

        @Override
        protected ArrayList<ProductView> doInBackground(String... params) {

            ArrayList<ProductView> products = ProductConnections.getProducts(activity, URL, int_page, 0);
            int_page++;

            return products;
        }

        @Override
        protected void onPostExecute(final ArrayList<ProductView> products) {

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (products != null) {
                productsList.addAll(products);
                adapter_product_grid.notifyDataSetChanged();
            }
        }
    }

    public static void createGridFragment(FragmentManager manager, String title, String URL, String category_id) {

        if (manager != null && URL != null) {

            Fragment fragment = manager.findFragmentByTag(TAG + category_id);

            if (fragment == null) {
                fragment = new GridFragment();

                Bundle bundle_fragment_grid = new Bundle();

                bundle_fragment_grid.putString(PARAM_URL, URL);
                bundle_fragment_grid.putString(PARAM_TITLE, title);

                fragment.setArguments(bundle_fragment_grid);
            }

            if (!fragment.isVisible()) {
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.frame, fragment, TAG + category_id);
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }
}
