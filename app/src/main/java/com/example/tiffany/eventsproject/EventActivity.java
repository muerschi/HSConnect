package com.example.tiffany.eventsproject;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Date;

import static com.example.tiffany.eventsproject.Helper.SessionManager.KEY_FACULTY;
import static java.lang.Long.valueOf;


public class EventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText eventTitle, location, description;
    TextView time, eventDate;
    SessionManager session;
    HttpPost httppost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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
            public void onClick(View v) {
                // Send Data
                // Use SessionManager session to get faculty without having to choose
                Event ev = null;

                // get texts from EditTexts
                eventTitle = (EditText) findViewById(R.id.editText1);
                location = (EditText) findViewById(R.id.editText2);
                //eventDate = (EditText) findViewById(R.id.editText3);
                //time = (EditText) findViewById(R.id.editText4);
                description = (EditText) findViewById(R.id.editText5);

                // set those to a new Event Object
                ev.setTitle(eventTitle.getText().toString());
                ev.setDescription(description.getText().toString());
                ev.getDate();
                ev.setEventDate(new Date(eventDate.getText().toString()));
                ev.setFaculty(session.getUserDetails().get(KEY_FACULTY));
                ev.setLocation(location.getText().toString());

                // create Json of Event Object
                JSONObject js = new JSONObject();
                //js.accumulate("event", ev);

                // send Json to PMAWebServer
                httppost = new HttpPost("http://141.19.176.160:8080/PMAWebServer/login");

                // on success:
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


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        time = (TextView) findViewById(R.id.editText4);
        time.setText(hourOfDay+":"+minute);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        eventDate = (TextView) findViewById(R.id.editText3);
        eventDate.setText(day+"/"+month+"/"+year);
    }

}
