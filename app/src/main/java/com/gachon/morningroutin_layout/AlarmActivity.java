package com.gachon.morningroutin_layout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("알람시작","알람시작");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examplealarm_activity_alarm);

        getSupportActionBar().setTitle("Alarm Activity");

        Button buttonToback = findViewById(R.id.buttonToback);

        buttonToback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
