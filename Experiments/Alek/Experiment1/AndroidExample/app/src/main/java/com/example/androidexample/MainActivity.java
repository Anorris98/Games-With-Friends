package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML


        Button goodbyeBtn = findViewById(R.id.goodbyeBtn);
        goodbyeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intentionally crash app
                throw new RuntimeException("Goodbye World - This crash was intentional");
            }
        });
    }
}