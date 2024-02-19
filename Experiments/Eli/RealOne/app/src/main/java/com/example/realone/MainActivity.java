package com.example.realone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.realone.databinding.ActivityMainBinding;

import com.example.realone.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView password = (TextView) findViewById(R.id.Pass);

        MaterialButton loginBtn= (MaterialButton) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin")&& password.getText().toString().equals("admin")){
                    Toast.makeText( MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText( MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );

    }
}