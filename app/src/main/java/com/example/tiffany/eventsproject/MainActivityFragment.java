package com.example.tiffany.eventsproject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tiffany.eventsproject.Helper.AsyncGetResponse;
import com.example.tiffany.eventsproject.Helper.HttpGetEvent;
import com.example.tiffany.eventsproject.Model.Event;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityFragment extends Fragment {

   private  ArrayList<Event> arrayOfEvents = new ArrayList<Event>();
    View rootView;

    public void getEvents () {
        new HttpGetEvent() {

            @Override
            public void onPostExecute( ArrayList<Event> eventsList) {

                super.onPostExecute( eventsList );
                arrayOfEvents = eventsList;


                EventAdapter adapter = new EventAdapter(getContext(), arrayOfEvents);
                // Attach the adapter to a ListView

                 try {
                    ListView listView = (ListView) rootView.findViewById(R.id.customEventView);
                    listView.setAdapter(adapter);
                 } catch (NullPointerException e) {
                    // TODO Auto-generated catch block
                     e.printStackTrace();
                 }

                // Do something with result here
            }
        }.execute();

    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getEvents();

        // Construct the data source

        /*
        Event e1 = new Event();
        Event e2 = new Event();
        Event e3 = new Event();
        Event e4 = new Event();
        Event e5 = new Event();
        Event e6 = new Event();
        Event e7 = new Event();
        Event e8 = new Event();

        e1.setTitle("Huhu");
        e1.setLocation("Kuckuck");
        e1.setTime("Kurz vor Naggüsch");

        e2.setTitle("Huhu2");
        e2.setLocation("Kuckuck2");
        e2.setTime("Kurz vor Naggüsch2");

        e3.setTitle("Huhu2");
        e3.setLocation("Kuckuck2");
        e3.setTime("Kurz vor Naggüsch2");

        e4.setTitle("Huhu2");
        e4.setLocation("Kuckuck2");
        e4.setTime("Kurz vor Naggüsch2");

        e5.setTitle("Huhu2");
        e5.setLocation("Kuckuck2");
        e5.setTime("Kurz vor Naggüsch2");

        e6.setTitle("Huhu2");
        e6.setLocation("Kuckuck2");
        e6.setTime("Kurz vor Naggüsch2");

        e7.setTitle("Huhu2");
        e7.setLocation("Kuckuck2");
        e7.setTime("Kurz vor Naggüsch2");

        e8.setTitle("Huhu2");
        e8.setLocation("Kuckuck2");
        e8.setTime("Kurz vor Naggüsch2");

        arrayOfEvents.add(e1);
        arrayOfEvents.add(e2);
        arrayOfEvents.add(e3);
        arrayOfEvents.add(e4);
        arrayOfEvents.add(e5);
        arrayOfEvents.add(e6);
        arrayOfEvents.add(e7);
        arrayOfEvents.add(e8);

*/

        rootView = inflater.inflate(R.layout.activity_main_fragment, container, false);

        /*
        EventAdapter adapter = new EventAdapter(getContext(), arrayOfEvents);
        // Attach the adapter to a ListView


        try {
            ListView listView = (ListView) rootView.findViewById(R.id.customEventView);
            listView.setAdapter(adapter);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        return rootView;

    }

}
