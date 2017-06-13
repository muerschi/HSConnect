package com.example.tiffany.eventsproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiffany.eventsproject.Helper.FachschaftenManager;
import com.example.tiffany.eventsproject.Helper.ServerConnection;
import com.example.tiffany.eventsproject.Model.Fachschaft;

import java.util.ArrayList;
import java.util.List;

public class FachschaftsProfilActivity extends AppCompatActivity {

    ServerConnection connection = null;
    SyncTask mSyncTask = null;
    FachschaftenManager fsManager = null;
    Fachschaft fachschaft = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachschafts_profil);

        final String fachschafts_name = getIntent().getStringExtra("EXTRA_FACHSCHAFTS_ID");


        connection = new ServerConnection();

        fsManager = new FachschaftenManager(getApplicationContext());
        try {
            fachschaft = fsManager.getFachschaft(fachschafts_name);


            TextView lblName = (TextView) findViewById(R.id.fsprofil_name);
            TextView lblLetter = (TextView) findViewById(R.id.fsprofil_letter);
            TextView lblEmail = (TextView) findViewById(R.id.fsprofil_email);
            TextView lblAdress = (TextView) findViewById(R.id.fsprofil_adresse);
            TextView lblMember = (TextView) findViewById(R.id.fsprofil_member);


            FloatingActionButton fltbt_fsWeb = (FloatingActionButton) findViewById(R.id.fltbt_fsWeb);
            FloatingActionButton fltbt_fsFacebook = (FloatingActionButton) findViewById(R.id.fltbt_fsFacebook);
            FloatingActionButton fltbt_fsEmail = (FloatingActionButton) findViewById(R.id.fltbt_fsEmail);


            lblLetter.setText(Html.fromHtml("<b> Fachschaft " + fachschaft.getName() + "</b>"));
            lblName.setText(Html.fromHtml("Institut für <b>" + fachschaft.getFaculty() + "</b>"));
            lblEmail.setText(Html.fromHtml("Fachschaftssprecher: <b>" + fachschaft.getFsSprecher() + "</b>"));
            lblAdress.setText(Html.fromHtml("Adresse: <b>" + fachschaft.getAdress() + "</b>"));
            lblMember.setText(Html.fromHtml("FachschaftsMitglieder: <b>" + fachschaft.getMember() + "</b>"));


            if (fachschaft.getWebsite() != null) {
                fltbt_fsWeb.setVisibility(View.VISIBLE);
            }
            if (fachschaft.getFacebook() != null) {
                fltbt_fsFacebook.setVisibility(View.VISIBLE);
            }
            if (fachschaft.getEmail() != null) {
                fltbt_fsEmail.setVisibility(View.VISIBLE);
            }

            fltbt_fsEmail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:" + fachschaft.getEmail());
                    intent.setData(data);
                    startActivity(intent);
                }
            });
            fltbt_fsWeb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Uri address = Uri.parse(fachschaft.getWebsite());
                    Intent surf = new Intent(Intent.ACTION_VIEW, address);
                    startActivity(surf);
                }
            });



            fltbt_fsFacebook.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    try {
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                        String facebookUrl = getFacebookPageURL(FachschaftsProfilActivity.this, fachschaft.getFacebook());
                        facebookIntent.setData(Uri.parse(facebookUrl));
                        startActivity(facebookIntent);
                    } catch (Exception e) {
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(FachschaftsProfilActivity.this);
                        alt_bld.setMessage("FacebookPage not found!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action for 'Yes' Button
                                    }
                                });
                        AlertDialog alert = alt_bld.create();
                        // Title for AlertDialog
                        alert.setTitle("Open Facebook failed");
                        alert.show();
                    }
                }
            });

        } catch (Exception e) {

            // If login failed, Ask user for rigth username and password
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(FachschaftsProfilActivity.this);
            alt_bld.setMessage("No Data availiable. Please sync first!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action for 'Yes' Button
                        }
                    });
            AlertDialog alert = alt_bld.create();
            // Title for AlertDialog
            alert.setTitle("Datenabfrage");
            alert.show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fs_profil, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_sync) {
            mSyncTask = new SyncTask();
            mSyncTask.execute((Void) null);
        }

        return super.onOptionsItemSelected(item);
    }


    public class SyncTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success = connection.Fachschaften(fsManager);

            return success;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mSyncTask = null;
            //showProgress(false);

            if (success) {

                finish();
                startActivity(getIntent());


            } else {

                // If login failed, Ask user for rigth username and password
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(FachschaftsProfilActivity.this);
                alt_bld.setMessage("Please Enter Valid credentials")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'Yes' Button
                            }
                        });
                AlertDialog alert = alt_bld.create();
                // Title for AlertDialog
                alert.setTitle("Sync Failed");
                alert.show();
            }
        }

        @Override
        protected void onCancelled() {
            mSyncTask= null;
            //showProgress(false);
        }

    }


    public static String FACEBOOK_PAGE_ID = "fsn.hsma";

    //method to get the right URL to use in the intent
    private String getFacebookPageURL(Context context, String facebook_url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + facebook_url;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return facebook_url; //normal web url
        }
    }

}

