package com.zetagile.foodcart.connections;

import android.content.Context;

import com.zetagile.foodcart.dto.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeAndCategoryConnections {

//    public static List<Category> getHomePageCategories(Context context) {
//        ConnectionClass cc_connection = new ConnectionClass();
//        String str_response = cc_connection.getRequest(ConnectionURLs.HOME_PAGE_CATEGORY_URL);
//        try {
//            return new JsonStringToJava().convert_cat_json(str_response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public static List<Promo> getHomeAdvertisements() {
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpGet httpget = new HttpGet(ConnectionURLs.HOME_ADVERTISEMENT_URL);
//            HttpResponse http_response = httpclient.execute(httpget);
//            HttpEntity httpEntity = http_response.getEntity();
//
//            String str_result = EntityUtils.toString(httpEntity);
//
//            if (str_result != null)
//                return new JsonStringToJava().convert_adv_json(str_result);
//
//        } catch (Exception ex) {
//            Log.d("InputStream", ex.getLocalizedMessage());
//        }
//        cc_connection.displayError(context, "Response Error");
//        return null;
//    }

//    public static List<Category> getCategoriesArray(Context context, String category_id) {
//
//        ConnectionURLs connections = ConnectionURLs.getInstance(context);
//
//        ConnectionClass cc_connection = new ConnectionClass();
//        String str_response = cc_connection.getRequest(context, connections.getCATEGORY_URL()+category_id);
//
//        try {
//            if(str_response != null)
//                return convert(new JSONArray(str_response));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        cc_connection.displayError(context, "Response Error");
//        return null;
//    }

    public static List<Category> getCategoriesList(Context context, String category_id) {

        ConnectionURLs connections = ConnectionURLs.getInstance(context);

        ConnectionClass cc_connection = new ConnectionClass();

        String str_response = cc_connection.getRequest(context, connections.getCATEGORY_URL() + category_id);
        if (str_response != null)
            try {
                return convert(new JSONArray(str_response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        else
            return new ArrayList<>();

        cc_connection.displayError(context, "Response Error");
        return new ArrayList<>();
    }

    private static List<Category> convert(JSONArray cat_array) throws JSONException {
        List<Category> categories = new ArrayList<>();
        if (cat_array != null)
            for (int i = 0; i < cat_array.length(); i++) {
                JSONObject object = (JSONObject) cat_array.get(i);
                Category category = new Category();

                if (!object.isNull("id"))
                    category.setId(object.getString("id"));

                if (!object.isNull("name"))
                    category.setName(object.getString("name"));

                if (!object.isNull("level"))
                    category.setLevel(object.getString("level"));

                if (!object.isNull("parentId"))
                    category.setParentId(object.getString("parentId"));

                if (!object.isNull("imageUrl"))
                    category.setImageUrl(object.getString("imageUrl"));

                JSONArray array = null;
                try {
                    if (!object.isNull("Category"))
                        array = (JSONArray) object.get("Category");
                } catch (JSONException e) {

                }

                categories.add(category);
                categories.addAll(convert(array));
            }
        return categories;
    }
}
