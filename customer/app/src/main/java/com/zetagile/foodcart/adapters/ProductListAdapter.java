package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.NavigationDrawer;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.dto.ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.fragments.catelog.CartFragment;
import com.zetagile.foodcart.util.PriceAndCurrency;
import com.zetagile.foodcart.util.qtyutil.QuantityPickerDialog;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> {

    List<ProductView> products;
    Context context;
    User usr_user;
    Category category;

    public ProductListAdapter(Context context, List<ProductView> products, Category category, User usr_user) {
        this.context = context;
        this.usr_user = usr_user;
        this.products = products;
        this.category = category;

        if (this.products == null)
            this.products = new ArrayList<>();
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.home_single_product_layout, null);

        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {

        final ProductView product = products.get(holder.getAdapterPosition());

        Glide.with(holder.iv_productImage.getContext()).load(product.getImagesUrl()).crossFade().fitCenter().into(holder.iv_productImage);

        holder.tv_productName.setText(product.getProductName());
        holder.tv_productId.setText(product.getProductId());
        PriceAndCurrency.setProductPrice(product, holder.tv_productPrice, holder.tv_productOfferPrice);

        holder.iv_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPickerDialog(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void createPickerDialog(final ProductView product) {
        final QuantityPickerDialog pickerDialog = new QuantityPickerDialog(context);
        pickerDialog.makeDialog();

        pickerDialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart_ProductView cart_product = new Cart_ProductView();
                cart_product.setProduct(product);
                cart_product.setQuantity(pickerDialog.getPickerCurrentValue());

                CartFragment.addToCart((NavigationDrawer) context, usr_user, cart_product);
                pickerDialog.dismiss();
            }
        });
        pickerDialog.showDialog();
    }

    public List<ProductView> getProducts() {
        return products;
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {

        TextView tv_productName;
        TextView tv_productOfferPrice;
        TextView tv_productPrice;
        TextView tv_productId;
        ImageView iv_productImage;
        ImageView iv_addToCart;

        public ProductHolder(View itemView) {
            super(itemView);

            tv_productName = (TextView) itemView.findViewById(R.id.product_name);
            tv_productId = (TextView) itemView.findViewById(R.id.product_id);
            tv_productOfferPrice = (TextView) itemView.findViewById(R.id.product_offer_price);
            tv_productPrice = (TextView) itemView.findViewById(R.id.product_price);

            iv_productImage = (ImageView) itemView.findViewById(R.id.product_image);
            iv_addToCart = (ImageView) itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
