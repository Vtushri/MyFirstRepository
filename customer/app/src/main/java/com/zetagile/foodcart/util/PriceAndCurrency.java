package com.zetagile.foodcart.util;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.zetagile.foodcart.dto.ProductView;

public class PriceAndCurrency {

    public static final String CURRENCY_PREFIX = "\u20B9 ";

    public static String roundPriceTwoPrecisions(double price) {
        return String.format("%.2f", price);
    }

    public static void setProductPrice(ProductView product, TextView actual_price, TextView offer_price) {

        Double dbl_product_offer_price = getProductPrice(product);
        Double dbl_product_price = getActualPrice(product);


        if (Double.compare(dbl_product_offer_price, dbl_product_price) == 0) {

            offer_price.setText(PriceAndCurrency.CURRENCY_PREFIX + roundPriceTwoPrecisions(dbl_product_offer_price));
            actual_price.setVisibility(View.GONE);
        } else {
            offer_price.setText(PriceAndCurrency.CURRENCY_PREFIX + roundPriceTwoPrecisions(dbl_product_offer_price));
            actual_price.setText(PriceAndCurrency.CURRENCY_PREFIX + roundPriceTwoPrecisions(dbl_product_price));
            actual_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

    public static double getActualPrice(ProductView product) {

        if (Double.compare(product.getProductPrice1(), 0.0) != 0
                && Double.compare(product.getProductPrice2(), 0.0) != 0) {
            return (product.getProductPrice1());

        } else if (Double.compare(product.getProductPrice1(), 0.0) == 0
                && Double.compare(product.getProductPrice2(), 0.0) != 0) {
            return (product.getProductPrice2());

        } else if (Double.compare(product.getProductPrice2(), 0.0) == 0
                && Double.compare(product.getProductPrice1(), 0.0) != 0) {
            return (product.getProductPrice1());
        } else
            return 0;
    }

    public static double getProductPrice(ProductView product) {

        if (Double.compare(product.getProductPrice1(), 0.0) != 0
                && Double.compare(product.getProductPrice2(), 0.0) != 0) {
            return (product.getProductPrice2());

        } else if (Double.compare(product.getProductPrice1(), 0.0) == 0
                && Double.compare(product.getProductPrice2(), 0.0) != 0) {
            return (product.getProductPrice2());

        } else if (Double.compare(product.getProductPrice2(), 0.0) == 0
                && Double.compare(product.getProductPrice1(), 0.0) != 0) {
            return (product.getProductPrice1());
        } else
            return 0;
    }

    public static void setPriceWithPrecision(TextView textView, double price) {

        if(textView != null) {
            String str_price = CURRENCY_PREFIX + String.valueOf(roundPriceTwoPrecisions(price));
            textView.setText(str_price);
        }
    }
}
