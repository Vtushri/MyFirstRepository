package com.zetagile.foodcart.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zetagile.foodcart.dto.Cart;
import com.zetagile.foodcart.dto.Cart_ProductView;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.util.ArrayList;
import java.util.List;

public class CartStorage {
    public static final String CART_PREFS = "CART_PREFS";
    public static final String CART = "CART";
    private static final String CART_PRODUCTS_COUNT = "CART_PRODUCTS_COUNT";
    private static final String NO_CART = "NO_CART";
    private static final String TAG = "CART_STORAGE";

    public static Cart getCart(Context context) {
        Cart cart;
        SharedPreferences prefs_cart = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String str_result = prefs_cart.getString(CART, NO_CART);

        Log.d(TAG, "Cart string from shared prefs: " + str_result);

        if (!str_result.equals(NO_CART)) {
            Log.d(TAG, "Cart previously in shared prefs");
            cart = new JsonStringToJava().convertCartfromJson(str_result);
        } else {
            cart = new Cart();
            Log.d(TAG, "Cart previously not in shared prefs");
        }

        return cart;
    }

    public static Cart updateCart(Cart_ProductView cart_product, Context context) {

        Cart cart;

        Log.d(TAG, "Adding/Updating/Removing product(" + cart_product.getProduct().getProductId() + ")in cart to QTY: " + cart_product.getQuantity());

        SharedPreferences cart_prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor cart_preference_editor = cart_prefs.edit();
        String str_cart = cart_prefs.getString(CART, NO_CART);

        if (str_cart.equals(NO_CART)) {
            Log.d(TAG, "No cart stored in the shared prefs before");
            cart = new Cart();
        } else {
            Log.d(TAG, "Cart existed in the shared prefs before");
            cart = new JsonStringToJava().convertCartfromJson(str_cart);
        }

        if (cart.getProducts() == null) {
            Log.d(TAG, "Cart products list is null in cart");
            cart.setProducts(new ArrayList<Cart_ProductView>());
        }

        try {
            if (cart_product.getQuantity() == 0) {
                Log.d(TAG, "Removing product (" + cart_product.getProduct().getProductId() + ") from cart");
                List<Cart_ProductView> cart_products = cart.getProducts();

                for (int i = 0; i < cart_products.size(); i++) {
                    if (cart_products.get(i).getProduct().equals(cart_product.getProduct())) {
                        cart_products.remove(i);
                        Log.d(TAG, "Product (" + cart_product.getProduct().getProductId() + ") found to remove from the cart");
                        break;
                    }
                }

                cart_preference_editor.clear();
                putCartProductCount(cart_preference_editor, cart_products.size());

                String updated_cart = new JavaToJsonString().convertCartToString(cart);
                Log.d(TAG, "Cart after removal: " + updated_cart);

                cart_preference_editor.putString(CART, updated_cart);
                cart_preference_editor.apply();
                return cart;

            } else {
                Log.d(TAG, "Updating product (" + cart_product.getProduct().getProductId() + ") in the cart");
                List<Cart_ProductView> cart_products = cart.getProducts();

                int i = 0;
                for (; i < cart_products.size(); i++) {
                    if (cart_products.get(i).getProduct().equals(cart_product.getProduct())) {
                        cart_products.get(i).setQuantity(cart_product.getQuantity());
                        Log.d(TAG, "Product (" + cart_product.getProduct().getProductId() + ") found to update the qty in the cart");
                        break;
                    }
                }

                if (i == cart_products.size()) {
                    Log.d(TAG, "Product (" + cart_product.getProduct().getProductId() + ") adding in the cart");
                    cart_products.add(cart_product);
                }

                cart_preference_editor.clear();
                putCartProductCount(cart_preference_editor, cart_products.size());

                String updated_cart = new JavaToJsonString().convertCartToString(cart);
                Log.d(TAG, "Cart after updating: " + updated_cart);

                cart_preference_editor.putString(CART, updated_cart);
                cart_preference_editor.apply();

                return cart;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Can not update cart : " + e.getLocalizedMessage());
            return null;
        }
    }

    public static boolean clearCart(Context context) {
        try {
            Log.d(TAG, "Clearing the cart from memory");
            SharedPreferences.Editor editor_cart_prefs = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE).edit();
            putCartProductCount(editor_cart_prefs, 0);
            editor_cart_prefs.clear();
            editor_cart_prefs.apply();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error in clearing the cart from memory :" + e.getLocalizedMessage());
            return false;
        }
        return true;
    }


    public static int getCartProductCount(Context context) {

        SharedPreferences prefs_cart = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String str_result = prefs_cart.getString(CART_PRODUCTS_COUNT, "0");

        Log.d(TAG, "Product count in the cart : " + str_result);
        return Integer.parseInt(str_result);
    }

    private static boolean putCartProductCount(SharedPreferences.Editor cartEditor, int count) {

        try {
            Log.d(TAG, "Putting product count in memory");
            cartEditor.putString(CART_PRODUCTS_COUNT, String.valueOf(count));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error in putting product count in memory :" + e.getLocalizedMessage());
        }
        return false;
    }
}
