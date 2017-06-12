package com.example.tiffany.eventsproject.Helper;

import com.example.tiffany.eventsproject.Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiffany on 11.06.2017.
 */

public interface AsyncGetResponse {
        void processFinish( ArrayList<Event> eventList);
}
