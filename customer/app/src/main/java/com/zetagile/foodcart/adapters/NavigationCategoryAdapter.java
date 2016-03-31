package com.zetagile.foodcart.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.connections.ConnectionURLs;
import com.zetagile.foodcart.dto.Category;
import com.zetagile.foodcart.fragments.catelog.GridFragment;

import java.util.List;

public class NavigationCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 1;
    public static final int CHILD = 2;
    List<Category> data;
    Activity context;
    DrawerLayout drawerLayout;


    public NavigationCategoryAdapter(Activity context, DrawerLayout drawerLayout, List<Category> data) {
        this.data = data;
        this.drawerLayout = drawerLayout;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (25 * dp);
        int subItemPaddingTopAndBottom = (int) (12 * dp);

        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.cat_explist_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);

                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
        }
        return null;
    }


    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final Category category = data.get(holder.getAdapterPosition());
        int ch = Integer.parseInt(category.getLevel());

        switch (ch) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = category;

                itemController.header_title.setText(category.getName());
                itemController.header_title.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        drawerLayout.closeDrawers();
                        final ConnectionURLs connections = ConnectionURLs.getInstance(context);
                        GridFragment.createGridFragment(context.getFragmentManager(), category.getName(), connections.getCATEGORY_PRODUCT_URL() + category.getId(), category.getId());
                        //                        Intent intent_product_list = new Intent(context, ProductListActivity.class);
//                        intent_product_list.putExtra(ProductListActivity.TITLE, category.getName());
//                        intent_product_list.putExtra(ProductListActivity.PARAM_URL, connections.getCATEGORY_PRODUCT_URL() + category.getId());

//                        context.startActivity(intent_product_list);
                    }
                });

                break;
            case CHILD:
                final TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).getName());
                /*itemTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_product_list = new Intent(context, ProductListActivity.class);
                        final ConnectionURLs connections = ConnectionURLs.getInstance(context);
                        intent_product_list.putExtra(ProductListActivity.TITLE, str_title);
                        intent_product_list.putExtra(ProductListActivity.PARAM_URL, connections.getCATEGORY_PRODUCT_URL() + str_category_id);

                        context.startActivity(intent_product_list);
                        Toast.makeText(context, data.get(position).getName(), Toast.LENGTH_SHORT).show();


                    }
                });*/
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(data.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        /* public ImageView btn_expand_toggle;*/
        public Category refferalItem;


        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
//           // btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast
                }
            });*/
        }

    }
}
