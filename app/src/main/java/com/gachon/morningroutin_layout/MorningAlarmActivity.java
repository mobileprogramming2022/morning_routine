package com.gachon.morningroutin_layout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MorningAlarmActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_alarm);

        //activity에 오자마자 알람 재생
        mediaPlayer = MediaPlayer.create(MorningAlarmActivity.this, R.raw.alarm_music_ex);
        mediaPlayer.start();

        getSupportActionBar().setTitle("Alarm Activity");
        Button back = (Button)findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button alarm_stop_button = (Button)findViewById(R.id.alarm_stop_button);

        alarm_stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
    }
}
