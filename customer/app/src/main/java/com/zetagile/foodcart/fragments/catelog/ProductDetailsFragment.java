package com.zetagile.foodcart.fragments.catelog;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.PriceAndCurrency;
import com.zetagile.foodcart.util.qtyutil.QuantityPickerDialog;

public class ProductDetailsFragment extends Fragment {


    private static final String TAG = "DETAILS_TAG";
    private static final String PARAM_TITLE = "PARAM_TITLE";
    private static final String PRODUCT = "PRODUCT";

    ProductView prv_product;

    String title;
    View fragmentView;
    Activity activity;

    public ProductDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        title = getArguments().getString(PARAM_TITLE);
        prv_product = getArguments().getParcelable(PRODUCT);

        System.out.println(prv_product.getProductId());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_product_details, null);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView details_back = (ImageView) fragmentView.findViewById(R.id.back_details);
        details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        ImageView iv_product_image = (ImageView) fragmentView.findViewById(R.id.product_image);

        TextView tv_product_name = (TextView) fragmentView.findViewById(R.id.product_name);
        TextView tv_product_actual_price = (TextView) fragmentView.findViewById(R.id.product_actual_price);
        TextView tv_product_offer_price = (TextView) fragmentView.findViewById(R.id.product_offer_price);
        TextView tv_product_desc = (TextView) fragmentView.findViewById(R.id.product_desc);
        LinearLayout ingredents = (LinearLayout) fragmentView.findViewById(R.id.ingredients);

        if (prv_product != null) {
            tv_product_name.setText(prv_product.getProductName());

            PriceAndCurrency.setProductPrice(prv_product, tv_product_actual_price, tv_product_offer_price);

            Glide.with(this).load(prv_product.getImagesUrl()).crossFade().fitCenter().into(iv_product_image);

            tv_product_desc.setText(prv_product.getProductDesc());
        }

        Button btn_add_to_cart = (Button) fragmentView.findViewById(R.id.btn_add_to_cart);
        Button btn_buy_now = (Button) fragmentView.findViewById(R.id.btn_buy_now);

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPickerDialog(prv_product);
            }
        });

        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User usr_user = UserStorage.getUser(activity);

                Cart_ProductView cart_product = new Cart_ProductView();
                cart_product.setProduct(prv_product);
                cart_product.setQuantity(1);

                CartFragment.addToCart(activity, usr_user, cart_product);
                CartFragment.createCartFragment(activity.getFragmentManager());
            }
        });

    }

    private void createPickerDialog(final ProductView product) {
        final QuantityPickerDialog pickerDialog = new QuantityPickerDialog(activity);
        pickerDialog.makeDialog();

        pickerDialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User usr_user = UserStorage.getUser(activity);

                Cart_ProductView cart_product = new Cart_ProductView();
                cart_product.setProduct(product);
                cart_product.setQuantity(pickerDialog.getPickerCurrentValue());

                CartFragment.addToCart(activity, usr_user, cart_product);
                pickerDialog.dismiss();
            }
        });
        pickerDialog.showDialog();
    }

    public static void createProductDetailsFragment(FragmentManager manager, ProductView product, String title) {

        if(manager != null && product != null) {

            Fragment fragment = manager.findFragmentByTag(TAG + product.getProductId());

            if (fragment == null) {
                fragment = new ProductDetailsFragment();

                Bundle bundle_fragment_grid = new Bundle();

                bundle_fragment_grid.putString(PARAM_TITLE, title);
                bundle_fragment_grid.putParcelable(PRODUCT, product);

                fragment.setArguments(bundle_fragment_grid);
            }

            if(!fragment.isVisible()) {
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.frame, fragment, TAG + product.getProductId());
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }
}
