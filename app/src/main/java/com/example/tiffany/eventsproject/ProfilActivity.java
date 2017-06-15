package com.example.tiffany.eventsproject;

import android.Manifest;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiffany.eventsproject.Helper.SessionManager;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    SessionManager session =null;
    ImageView iv_profilPic = null;
    private File picFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        session = new SessionManager(getApplicationContext());
        TextView lblName = (TextView) findViewById(R.id.profil_username);
        TextView lblfachschaft = (TextView) findViewById(R.id.profil_fachschaft);
        TextView lblEmail = (TextView) findViewById(R.id.profil_email);
        iv_profilPic = (ImageView) findViewById(R.id.iv_profilPic);


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

    }
}
