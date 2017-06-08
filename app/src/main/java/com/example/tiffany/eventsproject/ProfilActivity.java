package com.example.tiffany.eventsproject;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.tiffany.eventsproject.Helper.SessionManager;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    SessionManager session =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        session = new SessionManager(getApplicationContext());
        TextView lblName = (TextView) findViewById(R.id.profil_username);
        TextView lblfachschaft = (TextView) findViewById(R.id.profil_fachschaft);
        TextView lblEmail = (TextView) findViewById(R.id.profil_email);

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        // fachschaft
        String faculty = user.get(SessionManager.KEY_FACULTY);

        // displaying user data
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
        lblfachschaft.setText(Html.fromHtml("Facultät: <b>" + faculty + "</b>"));


        // Add Back Button to ActionBar
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);

    }

    /*@Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }*/
}
