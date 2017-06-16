package com.example.tiffany.eventsproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tiffany.eventsproject.Helper.HttpGetEvent;
import com.example.tiffany.eventsproject.Model.Event;


import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

   private  ArrayList<Event> arrayOfEvents = new ArrayList<Event>();
    View rootView;

    public void getEvents () {
        new HttpGetEvent() {

            @Override
            public void onPostExecute( ArrayList<Event> eventsList) {

                super.onPostExecute(eventsList);
                arrayOfEvents = eventsList;
                EventAdapter adapter = new EventAdapter(getContext(), arrayOfEvents);

                 try {
                    ListView listView = (ListView) rootView.findViewById(R.id.customEventView);
                    listView.setAdapter(adapter);
                 } catch (NullPointerException e) {

                     e.printStackTrace();
                 }

            }
        }.execute();

    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getEvents();
        rootView = inflater.inflate(R.layout.activity_main_fragment, container, false);
        return rootView;

    }

}
