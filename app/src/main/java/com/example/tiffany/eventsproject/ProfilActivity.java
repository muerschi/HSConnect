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


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        session = new SessionManager(getApplicationContext());
        TextView lblName = (TextView) findViewById(R.id.profil_username);
        TextView lblfachschaft = (TextView) findViewById(R.id.profil_fachschaft);
        TextView lblEmail = (TextView) findViewById(R.id.profil_email);
        Button btn_addPic = (Button) findViewById(R.id.btn_addPic);
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

        btn_addPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int check = ContextCompat.checkSelfPermission(ProfilActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(check != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProfilActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return;
                }

                int check2 = ContextCompat.checkSelfPermission(ProfilActivity.this, Manifest.permission.CAMERA);
                if( check2 != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ProfilActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                cameraIntentSend();
            }
        });
        // Add Back Button to ActionBar
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);

    }

   /* @Override
    public void onClick(View v){
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        cameraIntentSend();
    }
*/
    private void cameraIntentSend() {
        try {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            picFile = createPic();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
            startActivityForResult(cameraIntent, 1); //beliebige id
        }catch(Exception e){
            Log.d("HSConnect", "Error while sending Picture-Intent");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                getContentResolver().notifyChange(Uri.fromFile(picFile), null);
                ContentResolver cr = getContentResolver();

                Bitmap bitmap;
                try {

                    bitmap = BitmapFactory.decodeFile(Uri.fromFile(picFile).getPath());

                    //Wenn ihr schon eine ImageView instanziiert habt, könnt ihr hier direkt das Bild reinladen.
                    //Ansonsten empfiehlt sich das speichern des Bildes in einer globalen Variable


                    //Bitmap photo = (Bitmap) data.getExtras().get("data");
                    iv_profilPic.setImageBitmap(bitmap);


                    // Reload Page
                    finish();
                    startActivity(getIntent());

                } catch (Exception e) {
                    //Im Fehlerfall Meldung und LogCat Ausgabe
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                    Log.e("Camera", e.toString());
                }
            }

        }catch(Exception e){}
    }

    private File createPic()throws IOException{
        String fileName = "CameraSimple_" + System.currentTimeMillis() + ".jpg";
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dir, fileName);
    }

    private boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }
    /*@Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }*/
}
