package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button nofifyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nofifyBtn =findViewById(R.id.notification);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notifcation", "My Notifcation", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        nofifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void OnClick(View v) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notifcation");
                builder.setContentTitle("My Title");
                builder.setContentText("Hello From Eli");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat manageerCompat= NotificationManagerCompat.from(MainActivity.this);
                manageerCompat.notify(9,builder.build());

            }

        });


    }
}