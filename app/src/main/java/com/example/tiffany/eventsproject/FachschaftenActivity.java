package com.example.tiffany.eventsproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FachschaftenActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachschaften);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), FachschaftsProfilActivity.class);

        Button button = (Button) findViewById(v.getId());

        intent.putExtra("EXTRA_FACHSCHAFTS_ID", button.getText().toString());
        startActivity(intent);

    }


}
