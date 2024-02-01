package com.example.neighborhoodtalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button) findViewById(R.id.addbutton);   //this stores addButton as a refrence to the addbutton i have
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstNumEditText = (EditText) findViewById(R.id.FirstNumEditText);
                EditText SecondNumEditText = (EditText) findViewById(R.id.SecondNumEditText);
                TextView resultTextView Result = (TextView);
            }
        });
    }
}