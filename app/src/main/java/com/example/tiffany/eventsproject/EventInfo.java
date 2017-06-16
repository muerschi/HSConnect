package com.example.tiffany.eventsproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffany.eventsproject.Helper.HttpPostEvent;
import com.example.tiffany.eventsproject.Helper.SessionManager;
import com.example.tiffany.eventsproject.Model.Event;

import java.util.HashMap;
import java.util.Map;

public class EventInfo extends AppCompatActivity {

    TextView eventTitle, eventLocation, eventDate, eventTime, eventDescription;
    SessionManager sessionManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*

        Bundle adr = new Bundle();
        adr.putString("evAdr", extras.getString("eventLocation"));
        Fragment mapsFragment = new MapsActivity();
        mapsFragment.setArguments(adr);
        */
        final Bundle extras = getIntent().getExtras();

        // Constructor for fragment --> Pass location to fragment
        MapsActivity mapsFragment = MapsActivity.newInstance(extras.getString("eventLocation"));
        setContentView(R.layout.activity_event_info);

        sessionManager = new SessionManager(getApplicationContext());

        eventTitle = (TextView) findViewById(R.id.titleEvent);
        eventLocation = (TextView) findViewById(R.id.locationEvent);
        eventDate = (TextView) findViewById(R.id.dateEvent);
        eventTime = (TextView) findViewById(R.id.timeEvent);
        eventDescription = (TextView) findViewById(R.id.descriptionEvent);

        eventTitle.setText(extras.getString("eventTitle"));
        eventLocation.setText(extras.getString("eventLocation"));
        eventDate.setText(extras.getString("eventDate"));
        eventTime.setText(extras.getString("eventTime"));
        eventDescription.setText(extras.getString("eventDescription"));

        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        Button editBtn = (Button) findViewById(R.id.editBtn);

        final HashMap<String,String> user = sessionManager.getUserDetails();

        if(user.get(SessionManager.KEY_ROLE).equals("admin")){
            deleteBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);

            editBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)  {

                    Intent newEventActivity = new Intent(EventInfo.this, EventActivity.class);

                    // Pass data of event to new activity
                    newEventActivity.putExtra("eventTitle", extras.getString("eventTitle"));
                    newEventActivity.putExtra("eventLocation", extras.getString("eventLocation"));
                    newEventActivity.putExtra("eventDate", extras.getString("eventDate"));
                    newEventActivity.putExtra("eventTime", extras.getString("eventTime"));
                    newEventActivity.putExtra("eventDescription", extras.getString("eventDescription"));
                    newEventActivity.putExtra("evID", extras.getInt("evID"));
                    newEventActivity.putExtra("faculty", user.get(SessionManager.KEY_ROLE));

                    startActivity(newEventActivity);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)  {
                    Event ev = new Event();

                    ev.setId(extras.getInt("evID"));
                    new HttpPostEvent(ev, "delete") {

                        @Override
                        public void onPostExecute(String result) {

                            super.onPostExecute(result);

                            Intent newEventActivity = new Intent(EventInfo.this, MainActivity.class);
                            newEventActivity.putExtra("result", result);
                            startActivity(newEventActivity);
                        }
                    }.execute();

                }
            });

        }


    }

}
