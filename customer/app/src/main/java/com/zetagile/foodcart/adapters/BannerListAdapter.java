package com.zetagile.foodcart.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.dto.Promo;
import com.zetagile.foodcart.recyclerview.CustomRecyclerView;

import java.util.List;

public class BannerListAdapter extends RecyclerView.Adapter<BannerListAdapter.BannerHolder> {

    List<Promo> list_promos;
    CustomRecyclerView recyclerView;

    public BannerListAdapter(CustomRecyclerView recyclerView, List<Promo> promos) {

        this.list_promos = promos;
        this.recyclerView = recyclerView;
    }

    @Override
    public BannerListAdapter.BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ImageView imageView = new ImageView(parent.getContext());

        return new BannerHolder(imageView);
    }

    @Override
    public void onBindViewHolder(BannerListAdapter.BannerHolder holder, int position) {

        Glide.with(holder.bannerImage.getContext()).load(list_promos.get(position).getImageURL()).crossFade().fitCenter().into(holder.bannerImage);

        if(recyclerView != null)
            recyclerView.setCurrentPosition(position);
    }

    @Override
    public int getItemCount() {
        return list_promos.size();
    }

    public static class BannerHolder extends RecyclerView.ViewHolder {

        ImageView bannerImage;

        public BannerHolder(ImageView itemView) {
            super(itemView);

            int widthPixels = itemView.getContext().getResources().getDisplayMetrics().widthPixels;
            float density = itemView.getContext().getResources().getDisplayMetrics().density;

            int margin_bottom = (int) (5 * density);
            int margin = (int) (15 * density);
            int width = widthPixels - 2 * margin;

            bannerImage = itemView;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width / 2);
            params.setMargins(margin, margin_bottom, margin, margin_bottom);

            bannerImage.setLayoutParams(params);
            bannerImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    public void setRecyclerView(CustomRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
