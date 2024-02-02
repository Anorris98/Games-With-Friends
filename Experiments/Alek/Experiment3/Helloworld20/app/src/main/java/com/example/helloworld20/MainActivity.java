package com.example.helloworld20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make button and set on click listenr
        Button secondActivityBtn = (Button) findViewById(R.id.secondActivityBtn);
        secondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set intent, then have it launch the activity
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(startIntent);
            }
        });

        //find and pair google button and set on click listener.
        Button googleBtn = (Button) findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String google = "http://www.google.com";
                Uri webaddress = Uri.parse(google);
                //set intent
            Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webaddress);

            //if intent doesnt come back null, we want to start whatever activity comes up
            if (gotoGoogle.resolveActivity(getPackageManager())!= null);
                startActivity(gotoGoogle);
            }
        });
    }
}