package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.Promo;

import java.util.List;


public class PromosAdapter extends RecyclerView.Adapter<PromosAdapter.PromoDataHolder> {

    List<Promo> promoItem;
    Context context;

    public PromosAdapter(List<Promo> promoItem) {
        this.promoItem = promoItem;
    }


    @Override
    public PromoDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recyclepromo, parent, false);
        PromoDataHolder promoDataHolder = new PromoDataHolder(view);
        return promoDataHolder;
    }

    @Override
    public void onBindViewHolder(PromoDataHolder holder, int position) {
        Glide.with(context).load(promoItem.get(position).getImageURL()).crossFade().fitCenter().into(holder.img_promo);
    }

    @Override
    public int getItemCount() {
        return promoItem.size();
    }

    public class PromoDataHolder extends RecyclerView.ViewHolder {

        ImageView img_promo;

        public PromoDataHolder(View itemView) {
            super(itemView);
            img_promo = (ImageView) itemView.findViewById(R.id.img_promo);
        }
    }
}
