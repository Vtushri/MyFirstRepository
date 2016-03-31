package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.Offer;
import com.zetagile.foodcart.util.PriceAndCurrency;

import java.text.SimpleDateFormat;
import java.util.List;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersDataHolder> {

    List<Offer> content;
    Context context;

    public OffersAdapter(List<Offer> content) {
        this.content = content;
    }

    @Override
    public OffersDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_recycler, parent, false);
        OffersDataHolder offersDataHolder = new OffersDataHolder(view);
        return offersDataHolder;
    }

    @Override
    public void onBindViewHolder(OffersDataHolder holder, int position) {
        holder.offer_name.setText(content.get(position).getCouponCode());
        holder.offer_percentage.setText((int)content.get(position).getDiscount() + "% Off");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mma");
        holder.offer_minamount.setText("Min amount : "+PriceAndCurrency.CURRENCY_PREFIX  + PriceAndCurrency.roundPriceTwoPrecisions(content.get(position).getMinimumAmount()));
        holder.offer_time.setText("Valid till :" + timeFormatter.format(content.get(position).getValidTill()));
        holder.offer_desc.setText(content.get(position).getOfferDescription());
        Glide.with(context).load(content.get(position).getImageUrl()).crossFade().fitCenter().into(holder.offer_img);

        if(content.get(position).getMaxDiscount() == 0){
            holder.offer_maxdiscount.setVisibility(View.GONE);
        }else {
            holder.offer_maxdiscount.setText("Max Discount : " +PriceAndCurrency.CURRENCY_PREFIX + PriceAndCurrency.roundPriceTwoPrecisions(content.get(position).getMaxDiscount()));

        }

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class OffersDataHolder extends RecyclerView.ViewHolder {
        ImageView offer_img;
        TextView offer_name;
        TextView offer_time;
        TextView offer_desc;
        TextView offer_percentage;
        TextView offer_minamount;
        TextView offer_maxdiscount;

        public OffersDataHolder(View itemView) {
            super(itemView);
            offer_img = (ImageView) itemView.findViewById(R.id.offer_img);
            offer_name = (TextView) itemView.findViewById(R.id.offer_name);
            offer_time = (TextView) itemView.findViewById(R.id.offer_time);
            offer_percentage = (TextView) itemView.findViewById(R.id.offer_offer);
            offer_desc = (TextView) itemView.findViewById(R.id.offer_desc);
            offer_minamount = (TextView) itemView.findViewById(R.id.offer_minamount);
            offer_maxdiscount = (TextView) itemView.findViewById(R.id.offer_maxdiscount);
        }
    }
}
