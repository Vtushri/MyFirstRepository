package com.zetagile.foodcart.fragments.aboutus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zetagile.foodcart.R;
import com.zetagile.foodcart.adapters.EventsAdapter;
import com.zetagile.foodcart.asynctasks.EventsAsync;
import com.zetagile.foodcart.dto.Event;

import java.util.ArrayList;
import java.util.List;


public class EventsFragment extends android.support.v4.app.Fragment {

    RecyclerView eventsRecyclerView;
    RecyclerView.Adapter eventsAdapter;
    RecyclerView.LayoutManager eventsLayoutManager;
    List<Event> eventList = new ArrayList<>();
    TextView tv_no_events;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        eventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recycle);
        tv_no_events = (TextView) view.findViewById(R.id.no_events);
        eventsLayoutManager = new LinearLayoutManager(getActivity());
        eventsRecyclerView.setLayoutManager(eventsLayoutManager);
        eventsAdapter = new EventsAdapter(eventList);
        eventsRecyclerView.setAdapter(eventsAdapter);

        EventsAsync eventsAsync = new EventsAsync(eventList, getActivity(), eventsAdapter){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(eventList.size() == 0 || eventList.isEmpty()){
                    tv_no_events.setVisibility(View.VISIBLE);
                }
            }
        };


        eventsAsync.execute();
        return view;
    }
}
