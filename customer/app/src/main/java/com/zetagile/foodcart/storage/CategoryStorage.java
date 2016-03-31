package com.zetagile.foodcart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.util.jsonutils.JavaToJsonString;
import com.zetagile.foodcart.util.jsonutils.JsonStringToJava;

import java.util.ArrayList;
import java.util.List;

public class CategoryStorage {

    public static final String CATEGORY_PREFS = "CATEGORY_PREFS";
    public static final String CATEGORIES = "CATEGORIES";

    public static List<Category> getCategories(Context context) {
        try {
            String str_categories = context.getSharedPreferences(CATEGORY_PREFS, Context.MODE_PRIVATE).getString(CATEGORIES, null);
            if (str_categories != null) {
                return new JsonStringToJava().convert_cat_json(str_categories);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean putCategories(Context context, List<Category> categoryList) {
        try {
            String str_categories = null;

            if (categoryList != null)
                str_categories = new JavaToJsonString().convertCategories(categoryList);

            if(str_categories == null)
                str_categories = "[]";

            SharedPreferences preferences = context.getSharedPreferences(CATEGORY_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();

            editor.putString(CATEGORIES, str_categories);

            editor.apply();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
