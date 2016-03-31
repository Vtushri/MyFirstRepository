package com.zetagile.foodcart.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.asynctasks.UpdateCartItemTask;
import com.zetagile.foodcart.connections.CartConnections;
import com.zetagile.foodcart.constants.LayoutType;
import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.dto.User;
import com.zetagile.foodcart.storage.CartStorage;
import com.zetagile.foodcart.storage.UserStorage;
import com.zetagile.foodcart.util.ConfigUtil;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.util.List;

public class CartProductListAdapter extends ArrayAdapter<Cart_ProductView> {

    Activity activity;
    Button btn_checkout_button;
    TextView tv_total_price;
    TextView tv_cart_empty;
    TextView tv_no_of_items;

    Cart cart;

    List<Cart_ProductView> cart_products;

    LayoutType layoutType;

    public CartProductListAdapter(
            Activity context,
            int resource,
            Button btn_checkout_button,
            TextView tv_no_of_items,
            TextView tv_total_price,
            TextView tv_empty_cart,
            List<Cart_ProductView> cart_products) {

        super(context, resource, cart_products);

        this.cart_products = cart_products;
        this.activity = context;
        this.btn_checkout_button = btn_checkout_button;
        this.tv_total_price = tv_total_price;
        this.tv_cart_empty = tv_empty_cart;
        this.tv_no_of_items = tv_no_of_items;

        layoutType = ConfigUtil.getLayoutType(activity);

        if(cart == null)
            cart = new Cart();
    }

    @Override
    public void notifyDataSetChanged() {
        dataChangeActions();
        super.notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View item_view = convertView;
        CartItemViewHolder holder;

        if (item_view == null) {

            holder = new CartItemViewHolder();
            item_view = getItemView(holder);

        } else
            holder = (CartItemViewHolder) item_view.getTag();

        Cart_ProductView cart_product = cart_products.get(position);

        bindData(cart_product, holder, position);

        return item_view;
    }

    private void bindData(final Cart_ProductView cart_product, final CartItemViewHolder holder, final int position) {

        if(layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT)) {
            Glide.with(activity).load(cart_product.getProduct().getImagesUrl()).crossFade().fitCenter().into(holder.image_view);
        }

        holder.tv_title.setText(cart_product.getProduct().getProductName());
        holder.ed_qty.setText("" + cart_product.getQuantity());

        PriceAndCurrency.setProductPrice(cart_product.getProduct(), holder.tv_item_price, holder.tv_offer_price);

        holder.remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cart_product.setQuantity(0);
                updateCartItem(activity, cart_product, position);
            }
        });

        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_qty = holder.ed_qty.getText().toString();

                if (str_qty != null && !str_qty.isEmpty()) {

                    int qty = Integer.parseInt(str_qty);

                    if (qty > 0) {
                        holder.ed_qty.setText(String.valueOf(qty - 1));
                        cart_product.setQuantity(qty - 1);

                        updateCartItem(activity, cart_product, position);
                    }
                }
            }
        });


        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_qty = holder.ed_qty.getText().toString();

                if (str_qty != null && !str_qty.isEmpty()) {

                    int qty = Integer.parseInt(str_qty);

                    if (qty > 0) {
                        holder.ed_qty.setText(String.valueOf(qty + 1));

                        cart_product.setQuantity(qty + 1);

                        updateCartItem(activity, cart_product, position);
                    }
                }
            }
        });
    }

    private View getItemView(CartItemViewHolder holder) {

        View item_view;

        if(layoutType != null && layoutType.equals(LayoutType.IMAGES_LAYOUT)) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item_view = inflater.inflate(R.layout.cart_item_layout, null);

            holder.image_view = (ImageView) item_view.findViewById(R.id.cart_item_image);

        } else {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item_view = inflater.inflate(R.layout.non_cart_item_layout, null);

        }

        holder.tv_item_price = (TextView) item_view.findViewById(R.id.cart_item_price);
        holder.tv_offer_price = (TextView) item_view.findViewById(R.id.cart_offer_price);

        holder.tv_title = (TextView) item_view.findViewById(R.id.cart_item_title);
        holder.remove_button = (TextView) item_view.findViewById(R.id.btn_remove_item);
        holder.tv_minus = (Button) item_view.findViewById(R.id.button_minus);
        holder.tv_plus = (Button) item_view.findViewById(R.id.button_plus);
        holder.ed_qty = (EditText) item_view.findViewById(R.id.item_qty);

        item_view.setTag(holder);

        return item_view;
    }


    private void dataChangeActions() {
        if (cart_products.size() > 0) {

            User user = UserStorage.getUser(activity);
            if (user != null) {
                PriceAndCurrency.setPriceWithPrecision(tv_total_price, cart.getSubTotal());
            } else {
                int total_price = 0;
                for (int i = 0; i < cart_products.size(); i++) {

                    total_price = (int) (total_price +
                            (cart_products.get(i).getQuantity() * PriceAndCurrency.getProductPrice(cart_products.get(i).getProduct())));
                }

                cart.setSubTotal(total_price);
                PriceAndCurrency.setPriceWithPrecision(tv_total_price, total_price);
            }

            tv_no_of_items.setText(String.valueOf(cart_products.size()));
            btn_checkout_button.setEnabled(true);
            tv_cart_empty.setVisibility(View.INVISIBLE);

        } else {
            PriceAndCurrency.setPriceWithPrecision(tv_total_price, 0);

            tv_no_of_items.setText(String.valueOf(cart_products.size()));
            btn_checkout_button.setEnabled(false);
            tv_cart_empty.setVisibility(View.VISIBLE);
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        CartProductListAdapter.this.notifyDataSetChanged();
    }

    private class CartItemViewHolder {

        public TextView tv_title;
        public TextView tv_item_price;
        public TextView tv_offer_price;
        public ImageView image_view;
        public TextView remove_button;
        public Button tv_plus;
        public Button tv_minus;
        public EditText ed_qty;
    }

    private void updateCartItem(Activity activity, final Cart_ProductView cart_product, final int position){

        User user = UserStorage.getUser(activity);

        UpdateCartItemTask task = new UpdateCartItemTask(activity, cart_product, user) {

            @Override
            protected void onPostExecute(Cart cart_result) {
                super.onPostExecute(cart_result);
                onPostUpdate(cart_result);
            }
        };
        task.execute();
    }

    private void onPostUpdate(Cart cart_result) {

        if (cart_result != null) {
            cart_products.clear();

            if (cart_result.getProducts() != null) {
                cart_products.addAll(cart_result.getProducts());
                cart = cart_result;
            }

            CartProductListAdapter.this.notifyDataSetChanged();

        } else
            Toast.makeText(activity, "Some problem occurred in updating cart", Toast.LENGTH_SHORT).show();

    }

    public void getDataAndInitCartList() {

        User user = UserStorage.getUser(activity);
        GetCartAsyncTask async_task_http_cart = new GetCartAsyncTask(activity, user, cart);
        async_task_http_cart.execute();

    }

    private class GetCartAsyncTask extends AsyncTask<String, Void, Cart> {
        ProgressDialog dialog_waiting;

        Cart cart;
        CartProductListAdapter adapter;

        User user;
        Activity activity;

        public GetCartAsyncTask(Activity activity, User user, Cart cart) {
            this.user = user;
            this.activity = activity;
            this.cart = cart;
        }


        @Override
        protected void onPreExecute() {
            dialog_waiting = ProgressDialog.show(activity, "", "Please wait for a moment....");
            dialog_waiting.setCancelable(true);
        }

        @Override
        protected Cart doInBackground(String... params) {

            if (user != null) {
                cart = CartConnections.getCart(activity, user.getUserAccountId());
            } else {
                cart = CartStorage.getCart(activity);
            }

            return cart;
        }

        @Override
        protected void onPostExecute(Cart cart) {
            super.onPostExecute(cart);

            if(cart != null) {
                if(cart.getProducts() != null) {
                    onPostUpdate(cart);
                }
            } else
                Toast.makeText(activity, "Some problem occurred getting cart! Please Try Again!!!", Toast.LENGTH_LONG).show();

            if (dialog_waiting.isShowing())
                dialog_waiting.dismiss();
        }
    }
}
