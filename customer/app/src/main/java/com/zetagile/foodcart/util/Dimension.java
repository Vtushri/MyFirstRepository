package com.zetagile.foodcart.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Dimension {

    static int width, height;

    public static int getWidth(Activity c) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        width = displaymetrics.widthPixels;
        return width;
    }

    public static int getHeight(Activity c) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        return height;
    }
}
