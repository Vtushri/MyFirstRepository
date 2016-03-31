package com.zetagile.foodcart.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.zetagile.foodcart.connections.OfferPromoEventConnections;
import com.zetagile.foodcart.dto.Event;

import java.util.List;

public class EventsAsync extends AsyncTask<String, Void, String> {

    List<Event> events;
    Context context;
    RecyclerView.Adapter adapter;

    public EventsAsync(List<Event> events, Context context, RecyclerView.Adapter adapter) {
        this.events = events;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... params) {

        List<Event> eventData = OfferPromoEventConnections.getAllEvents(context, 1, 5);
        if (eventData != null && eventData.size() > 0) {
            events.addAll(eventData);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.notifyDataSetChanged();
    }
}
