package com.example.tiffany.eventsproject;

import com.example.tiffany.eventsproject.Helper.HttpPostEvent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
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
import java.util.Date;

import static com.example.tiffany.eventsproject.Helper.SessionManager.KEY_FACULTY;
import static java.lang.Long.valueOf;


public class EventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText eventTitle, location, description;
    TextView time, eventDate;
    /*
    SessionManager session;
    HttpPost httpPost = null;
    HttpClient httpClient = new DefaultHttpClient();
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventTitle = (EditText) findViewById(R.id.editText1);
        location = (EditText) findViewById(R.id.editText2);
        eventDate = (TextView) findViewById(R.id.editText3);
        time = (TextView) findViewById(R.id.editText4);
        description = (EditText) findViewById(R.id.editText5);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String evTitle = "";
            String evLocation = "";
            String evDate = "";
            String evDescription = "";
            String evTime = "";

            evTitle = extras.getString("eventTitle");
            evLocation = extras.getString("eventLocation");
            evDate = extras.getString("eventDate");
            evTime = extras.getString("eventTime");
            evDescription = extras.getString("eventDescription");

            eventTitle.setText(evTitle);
            location.setText(evLocation);
            eventDate.setText(evDate);
            time.setText(evTime);
            description.setText(evDescription);

        }
        /*
        Spinner spinner = (Spinner) findViewById(R.id.faculty_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.faculty_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        */

        final Button button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                // Send Data
                // Use SessionManager session to get faculty without having to choose
                Event ev = new Event();
                // get texts from EditTexts & TextViews (for Date & Time)
                /*
                eventTitle = (EditText) findViewById(R.id.editText1);
                location = (EditText) findViewById(R.id.editText2);
                eventDate = (TextView) findViewById(R.id.editText3);
                time = (TextView) findViewById(R.id.editText4);
                description = (EditText) findViewById(R.id.editText5);
                */

                // set those to a new Event Object
                ev.setTitle(eventTitle.getText().toString());
                ev.setDescription(description.getText().toString());
                ev.setEventDate(eventDate.getText().toString());
                ev.setTime(time.getText().toString());
                // ev.setFaculty(session.getUserDetails().get(KEY_FACULTY));
                ev.setLocation(location.getText().toString());

                new HttpPostEvent(ev).execute();

            }
        });

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
        time.setText(formattedHour+":"+formattedMinute);
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
        eventDate.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);

       // eventDate.setText(year+"-"+month+"-"+day);
    }

}
