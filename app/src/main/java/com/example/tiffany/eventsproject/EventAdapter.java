package com.example.tiffany.eventsproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tiffany.eventsproject.Helper.HttpGetEvent;
import com.example.tiffany.eventsproject.Model.Event;

import java.util.ArrayList;

/**
 * Created by Tiffany on 11.06.2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {


    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        // Get data itme for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_eventlist, parent, false);
        }
        // Lookup view for data population
        TextView evTitle = (TextView) convertView.findViewById(R.id.evevTitle);
        TextView evLocation = (TextView) convertView.findViewById(R.id.evLocation);
        TextView evTime = (TextView) convertView.findViewById(R.id.evTime);
        // Populate the data into the template view using the data object
        evTitle.setText("Titel des Events: " + event.getTitle());
        evLocation.setText("Ort: " + event.getLocation());
        evTime.setText("Zeit: " + event.getTime());

        CardView evCard = (CardView) convertView.findViewById(R.id.eventCard);
        evCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                //User user = getItem(position);
                // Do what you want here...

                Event event = getItem(position);
                Intent newEventActivity = new Intent(getContext(), EventInfo.class);

                // Pass data of event to new activity

                newEventActivity.putExtra("eventTitle", event.getTitle());
                newEventActivity.putExtra("eventLocation", event.getLocation());
                newEventActivity.putExtra("eventDate", event.getEventDate());
                newEventActivity.putExtra("eventTime", event.getTime());
                newEventActivity.putExtra("eventDescription", event.getDescription());
                //newEventActivity.putExtra("faculty", event.getFaculty());

                getContext().startActivity(newEventActivity);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
