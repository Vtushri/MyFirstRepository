package com.zetagile.foodcart.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.zetagile.foodcart.connections.ProductConnections;
import com.zetagile.foodcart.dto.ProductView;

import java.util.List;

public class GetProductsByCategoryTask extends AsyncTask<Void, Void, Void> {

    List<ProductView> products;
    Context context;
    String category_id;
    int page;
    int size;

    public GetProductsByCategoryTask(Context context, List<ProductView> products, String category_id, int page, int size) {
        this.context = context;
        this.products = products;
        this.category_id = category_id;
        this.page = page;
        this.size = size;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        List<ProductView> products = ProductConnections.getProductsByCategory(context, category_id, page, size);
        if (products != null)
            this.products.addAll(products);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
