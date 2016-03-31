package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.zetagile.foodcart.connections.HomeAndCategoryConnections;
import com.zetagile.foodcart.dto.Category;

import java.util.List;

public class GetSubCategoriesTask extends AsyncTask<Void, Void, Void> {

    Context context;
    String category_id;
    List<Category> categories;

    public GetSubCategoriesTask(Context context, List<Category> categories, String category_id) {
        this.context = context;
        this.category_id = category_id;
        this.categories = categories;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<Category> categories = HomeAndCategoryConnections.getCategoriesList(context, category_id);
        if (categories != null && categories.size() > 0) {
            this.categories.addAll(categories);
        }

        return null;
    }

}
