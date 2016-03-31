package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.OrderProduct;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.util.ArrayList;
import java.util.List;

public class SecondProductListAdapter extends RecyclerView.Adapter<SecondProductListAdapter.InvoiceProductViewHolder> {

    Context context;
    ArrayList<OrderProduct> products;

    public SecondProductListAdapter(Context context, List<OrderProduct> objects) {

        this.context = context;
        this.products = (ArrayList<OrderProduct>) objects;
    }

    @Override
    public InvoiceProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.invoice_product_list_item , parent , false);
        InvoiceProductViewHolder holder = new InvoiceProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InvoiceProductViewHolder holder, int position) {
        holder.tv_name.setText(products.get(position).getProduct().getProductName());

        holder.tv_quantity.setText(products.get(position).getQuantity() + "");
        int qty = products.get(position).getQuantity();

        double price = PriceAndCurrency.getProductPrice(products.get(position).getProduct());
        int  total = (int) (qty * price);
        PriceAndCurrency.setPriceWithPrecision(holder.tv_price, total);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class InvoiceProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_quantity;
        TextView tv_price;

        public InvoiceProductViewHolder(View row) {
            super(row);
          tv_name = (TextView) row.findViewById(R.id.product_name);
          tv_price = (TextView) row.findViewById(R.id.product_price);
          tv_quantity = (TextView) row.findViewById(R.id.product_quantity);

        }
    }
}