package com.gachon.morningroutin_layout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NightAlarmActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_alarm);

        //activity에 오자마자 알람 재생
        mediaPlayer = MediaPlayer.create(NightAlarmActivity.this, R.raw.alarm_music_ex2);
        mediaPlayer.start();

        getSupportActionBar().setTitle("Alarm Activity2");
        Button back = (Button)findViewById(R.id.fromNightAlarmGotoActivity_button);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        Button alarm_stop_button = (Button)findViewById(R.id.night_alarm_stop_button);

        alarm_stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
    }
}
