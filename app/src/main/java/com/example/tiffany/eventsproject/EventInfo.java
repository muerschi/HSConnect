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
import com.example.tiffany.eventsproject.Model.Event;

public class EventInfo extends AppCompatActivity {

    TextView eventTitle, eventLocation, eventDate, eventTime, eventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        final Bundle extras = getIntent().getExtras();

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

        Bundle adr = new Bundle();
        adr.putString("evAdr", extras.getString("eventLocation"));
        Fragment mapsFragment = new MapsActivity();
        mapsFragment.setArguments(adr);

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
                //newEventActivity.putExtra("faculty", event.getFaculty());

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
