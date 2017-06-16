package com.example.tiffany.eventsproject;

import com.example.tiffany.eventsproject.Helper.HttpPostEvent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tiffany.eventsproject.Helper.DatePickerFragment;
import com.example.tiffany.eventsproject.Helper.SessionManager;
import com.example.tiffany.eventsproject.Helper.TimePickerFragment;
import com.example.tiffany.eventsproject.Model.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.tiffany.eventsproject.Helper.SessionManager.KEY_FACULTY;
import static java.lang.Long.valueOf;


public class EventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText eventTitle, location, description;
    TextView time, eventDate;

    SessionManager sessionManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        sessionManager = new SessionManager(getApplicationContext());

        eventTitle = (EditText) findViewById(R.id.editText1);
        location = (EditText) findViewById(R.id.editText2);
        eventDate = (TextView) findViewById(R.id.editText3);
        time = (TextView) findViewById(R.id.editText4);
        description = (EditText) findViewById(R.id.editText5);

        final Bundle extras = getIntent().getExtras();

        // if user edits an Event
        if (extras != null) {

            eventTitle.setText(extras.getString("eventTitle"));
            location.setText(extras.getString("eventLocation"));
            eventDate.setText(extras.getString("eventDate"));
            time.setText(extras.getString("eventTime"));
            description.setText(extras.getString("eventDescription"));
        }

        // if user rotates phone
        if (savedInstanceState != null) {

            eventTitle.setText(savedInstanceState.getString("eventTitle"));
            location.setText(savedInstanceState.getString("location"));
            eventDate.setText(savedInstanceState.getString("eventDate"));
            time.setText(savedInstanceState.getString("time"));
            description.setText(savedInstanceState.getString("description"));
        }

        final Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)  {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(EventActivity.this);
                builder1.setMessage("Möchten Sie das Event wirklich speichern?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ja",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Event ev = new Event();
                                // set those to a new Event Object
                                ev.setTitle(eventTitle.getText().toString());
                                ev.setDescription(description.getText().toString());
                                ev.setEventDate(eventDate.getText().toString());
                                ev.setTime(time.getText().toString());

                                HashMap<String, String> user = sessionManager.getUserDetails();

                                ev.setFaculty(user.get(KEY_FACULTY));
                                ev.setLocation(location.getText().toString());
                                if (extras != null)
                                    ev.setId(extras.getInt("evID"));

                                new HttpPostEvent(ev, "add") {

                                    @Override
                                    public void onPostExecute(String result) {

                                        super.onPostExecute(result);

                                        Intent newEventActivity = new Intent(EventActivity.this, MainActivity.class);
                                        newEventActivity.putExtra("result", result);
                                        startActivity(newEventActivity);
                                    }
                                }.execute();


                            }
                        });

                builder1.setNegativeButton(
                        "Nein",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("eventTitle", eventTitle.getText().toString());
        outState.putString("description", description.getText().toString());
        outState.putString("eventDate", eventDate.getText().toString());
        outState.putString("time", time.getText().toString());
        outState.putString("location", location.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog (View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
        // Do something with the time chosen by the user

        String formattedHour = "" + hourOfDay;
        String formattedMinute = "" + minuteOfHour;

        if(hourOfDay < 10){

            formattedHour = "0" + hourOfDay;
        }
        if(minuteOfHour < 10){

            formattedMinute = "0" + minuteOfHour;
        }

        time = (TextView) findViewById(R.id.editText4);
        time.setText("um: "+formattedHour+":"+formattedMinute+" Uhr");
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Do something with the date chosen by the user
        eventDate = (TextView) findViewById(R.id.editText3);

        int month = monthOfYear + 1;
        String formattedMonth = "" + month;
        String formattedDayOfMonth = "" + dayOfMonth;

        if(month < 10){

            formattedMonth = "0" + month;
        }
        if(dayOfMonth < 10){

            formattedDayOfMonth = "0" + dayOfMonth;
        }
        eventDate.setText("am: " + formattedDayOfMonth + "-" + formattedMonth + "-" + year);

       // eventDate.setText(year+"-"+month+"-"+day);
    }

}
