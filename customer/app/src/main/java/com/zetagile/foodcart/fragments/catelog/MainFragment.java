package com.zetagile.foodcart.fragments.catelog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.ObservableScrollView;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.ScrollViewListener;
import com.zetagile.foodcart.adapters.BannerListAdapter;
import com.zetagile.foodcart.asynctasks.GetProductsByCategoryTask;
import com.zetagile.foodcart.asynctasks.GetPromosTask;
import com.zetagile.foodcart.connections.ConnectionURLs;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.Promo;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.recyclerview.CustomLinearLayoutManager;
import com.zetagile.foodcart.recyclerview.CustomRecyclerView;
import com.zetagile.foodcart.storage.CategoryStorage;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.PriceAndCurrency;
import com.zetagile.foodcart.util.qtyutil.QuantityPickerDialog;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private static final String TAG = "MAIN_FRAGMENT";

    BannerListAdapter banner_adapter;
    View fragmentView;

    List<Category> categories;
    ObservableScrollView scrollview;
    ProgressBar progressBar;

    Activity activity;

    LayoutType layoutType;

    int view_index = 1;
    View parentView;
    boolean loading = false;
    int count = 0;

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        layoutType = ConfigUtil.getLayoutType(activity);

        //PromosFragment related recycler view
        List<Promo> promos = new ArrayList<>();
        banner_adapter = new BannerListAdapter(null, promos);
        categories = CategoryStorage.getCategories(activity);

        GetPromosTask promosTask = new GetPromosTask(activity, banner_adapter, promos);
        promosTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_main, null);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        view_index = 0;
        parentView = null;

        CustomRecyclerView lv_banner_list_view = (CustomRecyclerView) fragmentView.findViewById(R.id.banner_list);
        lv_banner_list_view.setLayoutManager(new CustomLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        banner_adapter.setRecyclerView(lv_banner_list_view);
        lv_banner_list_view.setAdapter(banner_adapter);

        //Get all sub categories
        if (parentView == null)
            parentView = (ViewGroup) fragmentView.findViewById(R.id.category_product_list);

        progressBar = (ProgressBar) fragmentView.findViewById(R.id.main_progress);

        scrollview = (ObservableScrollView) fragmentView.findViewById(R.id.main_scroll);

        for (; categories != null && categories.size() > 0 && view_index <= 1; view_index++)
            initializeCategoryProductViews((ViewGroup) parentView, categories.get(view_index));

        scrollview.setScrollViewListener(new ScrollViewListener() {

            @Override
            public void onBottomReached() {

                if (categories.size() <= view_index) {
                    progressBar.setVisibility(View.GONE);
                } else if (!loading) {
                    loading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    initializeCategoryProductViews((ViewGroup) parentView, categories.get(view_index));
                    view_index++;
                }
            }
        });
    }

    public void initializeCategoryProductViews(final ViewGroup parentView, final Category category) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view;
        if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT))
            view = inflater.inflate(R.layout.home_main_list_row, null);
        else
            view = inflater.inflate(R.layout.none_home_mainlistrow, null);

        TextView show_all = (TextView) view.findViewById(R.id.showall);
        TextView tv_categoryName = (TextView) view.findViewById(R.id.home_category_name);

        final LinearLayout products_layout = (LinearLayout) view.findViewById(R.id.products_layout);

        tv_categoryName.setText(category.getName());

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionURLs connections = new ConnectionURLs(activity);
                GridFragment.createGridFragment(activity.getFragmentManager(), category.getName(), connections.getCATEGORY_PRODUCT_URL() + category.getId(), category.getId());
            }
        });


        final List<ProductView> products = new ArrayList<>();


        GetProductsByCategoryTask getProductsTask =
                new GetProductsByCategoryTask(activity, products, category.getId(), 1, 3) {

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        ProductView prv_product;

                        for (int i = 0; i < products.size(); i++) {
                            prv_product = products.get(i);
                            if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT))
                                displayProduct(prv_product, activity, products_layout, i);
                            else
                                displayTextProduct(prv_product, activity, products_layout, i);
                        }
                        loading = false;
                        parentView.addView(view);
                    }
                };

        getProductsTask.execute();


    }

    private void displayProduct(final ProductView prv_product, final Context context, ViewGroup vg_product_list_layout, final int int_index) {

        if (prv_product != null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v_single_product_layout = inflater.inflate(R.layout.home_single_product_layout, vg_product_list_layout, false);

            //Image
            String str_product_image_url = prv_product.getImagesUrl();
            ImageView iv_product = (ImageView) v_single_product_layout.findViewById(R.id.product_image);

            Glide.with(context).load(str_product_image_url).crossFade().fitCenter().into(iv_product);

            TextView tv_price2_view = (TextView) v_single_product_layout.findViewById(R.id.product_price);
            TextView tv_price1_view = (TextView) v_single_product_layout.findViewById(R.id.product_offer_price);
            PriceAndCurrency.setProductPrice(prv_product, tv_price2_view, tv_price1_view);
            TextView tv_product_name = (TextView) v_single_product_layout.findViewById(R.id.product_name);
            String[] arr_str_split_name = prv_product.getProductName().split("\\s+");
            String str_dp_name = "";
            for (int i = 2; i < arr_str_split_name.length - 1; i = i + 2)
                str_dp_name = str_dp_name + arr_str_split_name[i] + " " + arr_str_split_name[i + 1];
            if (arr_str_split_name.length > 2)
                tv_product_name.setText(arr_str_split_name[0] + " " + arr_str_split_name[1] + "...");
            else
                tv_product_name.setText(prv_product.getProductName());
            TextView tv_product_id_view = (TextView) v_single_product_layout.findViewById(R.id.product_id);
            tv_product_id_view.setText(prv_product.getCategoryId());
            ImageView iv_cart_icon = (ImageView) v_single_product_layout.findViewById(R.id.btn_add_to_cart);
            iv_cart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createPickerDialog(prv_product);
                }
            });
            vg_product_list_layout.addView(v_single_product_layout, int_index);
            vg_product_list_layout.getChildAt(int_index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductDetailsFragment.createProductDetailsFragment(activity.getFragmentManager(), prv_product, "");
                }
            });
        }
    }

    private void displayTextProduct(final ProductView prv_product, final Context context, ViewGroup vg_product_list_layout, final int int_index) {
        final ImageView iv_cart_icon;

        if (prv_product != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v_single_product_layout = inflater.inflate(R.layout.none_single_main_item_layout, vg_product_list_layout, false);
            //Price1 and Price2
            TextView tv_price2_view = (TextView) v_single_product_layout.findViewById(R.id.none_main_actual_price);
            TextView tv_price1_view = (TextView) v_single_product_layout.findViewById(R.id.none_main_offer_price);

            PriceAndCurrency.setProductPrice(prv_product, tv_price2_view, tv_price1_view);

            //Product name
            TextView tv_product_name = (TextView) v_single_product_layout.findViewById(R.id.none_main_name);

            /*String[] arr_str_split_name = prv_product.getProductName().split("\\s+");

            String str_dp_name = "";
            for (int i = 2; i < arr_str_split_name.length - 1; i = i + 2)
                str_dp_name = str_dp_name + arr_str_split_name[i] + " " + arr_str_split_name[i + 1];

            if (arr_str_split_name.length > 2)
                tv_product_name.setText(arr_str_split_name[0] + " " + arr_str_split_name[1] + "...");

            else*/
            tv_product_name.setText(prv_product.getProductName());

            //Product id
            /*TextView tv_product_id_view = (TextView) v_single_product_layout.findViewById(R.id.product_id);
            tv_product_id_view.setText(prv_product.getCategoryId());*/

            final TextView tv_product_description_view = (TextView) v_single_product_layout.findViewById(R.id.none_main_description);

            tv_product_description_view.setText(prv_product.getProductDesc().trim());


            //add to cart button onclick listener
            iv_cart_icon = (ImageView) v_single_product_layout.findViewById(R.id.none_main_addtocart);
            iv_cart_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createPickerDialog(prv_product);

                }
            });

            vg_product_list_layout.addView(v_single_product_layout, int_index);

            vg_product_list_layout.getChildAt(int_index).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.blink);

                    iv_cart_icon.startAnimation(animation);
                    if(count % 2 == 0){
                        tv_product_description_view.setSingleLine(true);
                    }else tv_product_description_view.setSingleLine(false);


                }
            });

                    /*ProductDetailsFragment.createProductDetailsFragment(activity.getFragmentManager(), prv_product, "");*/
        }
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


    public static void createFragment(FragmentManager manager) {
        if (manager != null) {
            Fragment fragment = manager.findFragmentByTag(TAG);

            if (fragment == null)
                fragment = new MainFragment();

            if (!fragment.isVisible()) {

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment, TAG);
                transaction.addToBackStack(null);
                transaction.commit();

                manager.executePendingTransactions();
            }
        }
    }
}
