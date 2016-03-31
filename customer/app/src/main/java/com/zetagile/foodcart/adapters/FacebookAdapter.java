package com.zetagile.foodcart.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.facebook.FacebookFeeds;

import java.util.List;

public class FacebookAdapter extends RecyclerView.Adapter<FacebookAdapter.FacebookDataObjectHolder> {

    private List<FacebookFeeds> comments;

    public FacebookAdapter(List<FacebookFeeds> comments) {
        this.comments = comments;
    }

    @Override
    public FacebookDataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facebook_feedback, parent, false);
        FacebookDataObjectHolder facebookDataObjectHolder = new FacebookDataObjectHolder(view);
        return facebookDataObjectHolder;
    }

    @Override
    public void onBindViewHolder(FacebookDataObjectHolder holder, int position) {
        holder.post_by.setText(comments.get(position).getCommenter() + " says");
//        holder.id.setText(comments.get(position).getCommentId());
//        holder.time.setText((CharSequence) comments.get(position).getCreatedTime());
        holder.message.setText(comments.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        System.out.println("Item Count " + comments.size());
        return comments.size();
    }

    public class FacebookDataObjectHolder extends RecyclerView.ViewHolder {
        TextView id, message, post_by;

        public FacebookDataObjectHolder(View itemView) {
            super(itemView);
//            id = (TextView) itemView.findViewById(R.id.post_id);
            message = (TextView) itemView.findViewById(R.id.post_message);
            post_by = (TextView) itemView.findViewById(R.id.post_by);

        }

    }

}
