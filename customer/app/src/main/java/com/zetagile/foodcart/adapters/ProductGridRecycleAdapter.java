package com.zetagile.foodcart.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.fragments.catelog.ProductDetailsFragment;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.PriceAndCurrency;
import com.zetagile.foodcart.util.qtyutil.QuantityPickerDialog;

import java.util.List;

public class ProductGridRecycleAdapter extends RecyclerView.Adapter<ProductGridRecycleAdapter.ViewHolder>  {

    Activity context;
    List<ProductView> list_prv_products;
    LayoutType layoutType;


    public ProductGridRecycleAdapter(Activity context, List<ProductView> list_prv_products) {
        this.context = context;
        this.list_prv_products = list_prv_products;
        layoutType = ConfigUtil.getLayoutType(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT)) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_single_product_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.none_single_main_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder_grid_item, final int int_position) {

        if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT)) {
        Glide.with(context).load(list_prv_products.get(int_position).getImagesUrl()).crossFade().fitCenter().into(holder_grid_item.iv_image_view);
            holder_grid_item.tv_id.setText(list_prv_products.get(int_position).getProductId());

        } else
            holder_grid_item.tv_description.setText(list_prv_products.get(int_position).getProductDesc().trim());

            holder_grid_item.tv_title.setText(list_prv_products.get(int_position).getProductName());



        PriceAndCurrency.setProductPrice(list_prv_products.get(int_position), holder_grid_item.tv_price, holder_grid_item.tv_offer_price);

        holder_grid_item.iv_cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
               animation.cancel();

                createPickerDialog(list_prv_products.get(int_position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_prv_products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_offer_price;
        public TextView tv_price;
        public TextView tv_id;
        public TextView tv_description;
        public ImageView iv_image_view;
        public ImageView iv_cart_icon;



        public ViewHolder(View itemView) {
            super(itemView);
            if (layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT)) {

            tv_title = (TextView) itemView.findViewById(R.id.product_name);
            tv_id = (TextView) itemView.findViewById(R.id.product_id);
            tv_offer_price = (TextView) itemView.findViewById(R.id.product_offer_price);
            tv_price = (TextView) itemView.findViewById(R.id.product_price);
            iv_image_view = (ImageView) itemView.findViewById(R.id.product_image);
            iv_cart_icon = (ImageView) itemView.findViewById(R.id.btn_add_to_cart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductDetailsFragment.createProductDetailsFragment(context.getFragmentManager(), list_prv_products.get(getAdapterPosition()), "");
                }
            });

            } else {
                tv_title = (TextView) itemView.findViewById(R.id.none_main_name);
                tv_description = (TextView) itemView.findViewById(R.id.none_main_description);
                tv_offer_price = (TextView) itemView.findViewById(R.id.none_main_offer_price);
                tv_price = (TextView) itemView.findViewById(R.id.none_main_actual_price);
                iv_cart_icon = (ImageView) itemView.findViewById(R.id.none_main_addtocart);
                tv_description.setSingleLine(false);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
                        iv_cart_icon.startAnimation(animation);


                    }

                });



            }
        }
    }


    private void createPickerDialog(final ProductView product) {
        final QuantityPickerDialog pickerDialog = new QuantityPickerDialog(context);
        pickerDialog.makeDialog();

        pickerDialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usr_user = UserStorage.getUser(context);

                Cart_ProductView cart_product = new Cart_ProductView();
                cart_product.setProduct(product);
                cart_product.setQuantity(pickerDialog.getPickerCurrentValue());

                CartFragment.addToCart(context, usr_user, cart_product);
                pickerDialog.dismiss();
            }
        });
        pickerDialog.showDialog();
    }

}
