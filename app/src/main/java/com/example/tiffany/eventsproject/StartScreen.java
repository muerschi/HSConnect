package com.example.tiffany.eventsproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.tiffany.eventsproject.Helper.SessionManager;
import com.example.tiffany.eventsproject.LoginActivity;
import com.example.tiffany.eventsproject.R;

import java.util.HashMap;

public class StartScreen extends AppCompatActivity {

    SessionManager sessionManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        sessionManager = new SessionManager(getApplicationContext());

        // Fade in and out the HS Connect- Logo
        int fadeInDuration = 2500; // Configure time values here
        int timeBetween = 1000;
        int fadeOutDuration = 1000;

        ImageView img_hsConnect = (ImageView) findViewById(R.id.img_hsConnect);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        img_hsConnect.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                Intent i;

                if(sessionManager != null & (sessionManager.checkLogin() == true)){

                    // Staring MainActivity
                    HashMap<String, String> user = sessionManager.getUserDetails();
                    i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("result", "Willkommen "+ user.get(SessionManager.KEY_NAME).toString()+"!");


                }else{
                    i = new Intent(getApplicationContext(), LoginActivity.class);
                }

                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation.start();


    }
}
