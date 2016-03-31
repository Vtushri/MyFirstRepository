package com.zetagile.foodcart.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zetagile.foodcart.R;
import com.zetagile.foodcart.dto.Event;

import java.util.List;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsDataHolder> {

    List<Event> eventsData;
    Context context;

    public EventsAdapter(List<Event> eventsData) {
        this.eventsData = eventsData;
    }

    @Override
    public EventsDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_events_recycler, parent, false);
        EventsDataHolder eventsDataHolder = new EventsDataHolder(view);
        return eventsDataHolder;
    }

    @Override
    public void onBindViewHolder(EventsDataHolder holder, int position) {

//        holder.tv_eventname.setText(eventsData.get(position).getEventName());
//        holder.tv_event_desc.setText(eventsData.get(position).getEventDescription());
        holder.tv_event_time.setText(eventsData.get(position).getEventId());
        if(eventsData.get(position).getImageUrl() == null){
            holder.img_evnt.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(eventsData.get(position).getImageUrl()).crossFade().fitCenter().into(holder.img_evnt);
        }
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class EventsDataHolder extends RecyclerView.ViewHolder {
        ImageView img_evnt;
        TextView tv_eventname, tv_event_desc, tv_event_time;

        public EventsDataHolder(View itemView) {
            super(itemView);
            img_evnt = (ImageView) itemView.findViewById(R.id.img_evnt);
//            tv_eventname = (TextView) itemView.findViewById(R.id.tv_eventname);
//            tv_event_desc = (TextView) itemView.findViewById(R.id.tv_event_desc);
            tv_event_time = (TextView) itemView.findViewById(R.id.tv_event_time);
        }
    }
}
