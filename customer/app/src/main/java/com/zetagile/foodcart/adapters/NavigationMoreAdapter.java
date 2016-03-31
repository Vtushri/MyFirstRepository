package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.AboutActivity;
import com.zetagile.foodcart.PolicyActivity;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.StoreListActivity;
import com.zetagile.foodcart.navigation.NavigationField;
import com.zetagile.foodcart.navigation.NavigationItemType;

import java.util.List;


public class NavigationMoreAdapter extends RecyclerView.Adapter<NavigationMoreAdapter.ViewHolder> {

    List<NavigationField> navigationList;
    DrawerLayout drawerLayout;
    Context context;

    public NavigationMoreAdapter(Context context, DrawerLayout drawerLayout, List<NavigationField> navigationList) {
        this.navigationList = navigationList;
        this.drawerLayout = drawerLayout;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View otherView = LayoutInflater.from(context).inflate(R.layout.cat_explist_header, parent, false);
        ViewHolder viewHolder = new ViewHolder(otherView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(navigationList.get(position).getTitle());

        final NavigationField other_item = navigationList.get(holder.getAdapterPosition());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (other_item.getType().equals(NavigationItemType.KNOW_US_MORE)) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    intent.putExtra(AboutActivity.TAB_TYPE, NavigationItemType.KNOW_US_MORE.name());
                    context.startActivity(intent);
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.PROMOTIONS)) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    intent.putExtra(AboutActivity.TAB_TYPE, NavigationItemType.PROMOTIONS.name());
                    context.startActivity(intent);
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.FEEDBACK)) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    intent.putExtra(AboutActivity.TAB_TYPE, NavigationItemType.FEEDBACK.name());
                    context.startActivity(intent);
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.OFFERS)) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    intent.putExtra(AboutActivity.TAB_TYPE, NavigationItemType.OFFERS.name());
                    context.startActivity(intent);
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.EVENTS)) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    intent.putExtra(AboutActivity.TAB_TYPE, NavigationItemType.EVENTS.name());
                    context.startActivity(intent);
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.STORES)) {
                    context.startActivity(new Intent(context, StoreListActivity.class));
                    drawerLayout.closeDrawers();
                } else if (other_item.getType().equals(NavigationItemType.POLICY)) {
                    context.startActivity(new Intent(context, PolicyActivity.class));
                    drawerLayout.closeDrawers();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return navigationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.header_title);
           /* *//*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context , "POsition" + getAdapterPosition() , Toast.LENGTH_SHORT).show();
                    int itemposition = getAdapterPosition();

                    *//**//*if (itemposition == 0)
                        context.startActivity(new Intent(context, AboutActivity.class));

                    else if (itemposition == 1)
                        context.startActivity(new Intent(context, StoreListActivity.class));
                    else if (itemposition == 2)
                        Toast.makeText(context, "Policy", Toast.LENGTH_SHORT).show();
                    else if (itemposition == 3)
                        context.startActivity(new Intent(context, AboutActivity.class));
                    else if (itemposition == 4)
                        context.startActivity(new Intent(context, AboutActivity.class));
                    else if (itemposition == 5) {
                        UserStorage.clearUserDetails(context);
                        context.startActivity(new Intent(context, MainActivity.class));
                    }*//*
                }

            });*/
        }
    }

}








