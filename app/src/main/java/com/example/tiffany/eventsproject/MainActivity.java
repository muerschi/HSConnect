package com.example.tiffany.eventsproject;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffany.eventsproject.Helper.FachschaftenManager;
import com.example.tiffany.eventsproject.Helper.ServerConnection;
import com.example.tiffany.eventsproject.Helper.SessionManager;
import com.example.tiffany.eventsproject.Model.Fachschaft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.tiffany.eventsproject.Helper.HttpGetEvent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventActivity evActivity;
    private SessionManager session;
    private HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Session
        session = new SessionManager(getApplicationContext());

        // check Login will redirect user back to LoginActivity, if he is not logged in.
        session.checkLogin();
        user = session.getUserDetails();

        // coming from EventActivity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Toast.makeText(getApplicationContext(),
                    extras.getString("result"), Toast.LENGTH_SHORT)
                    .show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newEventActivity = new Intent(MainActivity.this, EventActivity.class);
                startActivity(newEventActivity);
               /* Snackbar.make(view, evActivity, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            */
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView lblNavName = (TextView) hView.findViewById(R.id.lblNavName);
        TextView lblNavEmail = (TextView) hView.findViewById(R.id.lblNavEmail);
        lblNavName.setText(user.get(SessionManager.KEY_NAME));
        lblNavEmail.setText(user.get(SessionManager.KEY_EMAIL));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if( id == R.id.action_logout){
            session.logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent newEventActivity = new Intent(MainActivity.this, MainActivity.class);
            startActivity(newEventActivity);
        } else if (id == R.id.nav_gallery) {
            Intent newEventActivity = new Intent(MainActivity.this, MensaplanActivity.class);
            startActivity(newEventActivity);
        } else if (id == R.id.nav_slideshow) {
            Intent newEventActivity = new Intent(MainActivity.this, FachschaftenActivity.class);
            startActivity(newEventActivity);
        } else if (id == R.id.nav_manage) {
            Intent newProfilActivity = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(newProfilActivity);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
