package com.gachon.morningroutin_layout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MorningAlarmActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_alarm);

        //activity에 오자마자 알람 재생
        mediaPlayer = MediaPlayer.create(MorningAlarmActivity.this, R.raw.alarm_music_ex);
        mediaPlayer.start();

        getSupportActionBar().setTitle("Alarm Activity");
        Button activity_inflation_BUTTON = (Button)findViewById(R.id.fromMorningAlarmGotoActivity_button);

        activity_inflation_BUTTON.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //노래 끄기
                mediaPlayer.stop();
                mediaPlayer.reset();

                DatabaseReference planRef = database.child("daily").child("12345");
                planRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        getTodayPlan plan = snapshot.getValue(getTodayPlan.class);
                        assert plan != null;

                        if (plan.specificType.compareTo("PEDOMETER") == 0) {
                            Intent intent = new Intent(getApplicationContext(), WalkFlowActivity.class);
                            startActivity(intent);
                        } else if (plan.specificType.compareTo("TIMER") == 0) {
                            if (plan.type.compareTo("STUDY") == 0) {
                                Intent intent = new Intent(getApplicationContext(), StudyTimerActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                                startActivity(intent);
                            }
                        } else if (plan.specificType.compareTo("TODO") == 0) {
                            Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                finish();
            }
        });

        Button alarm_stop_button = (Button)findViewById(R.id.morning_alarm_stop_button);

        alarm_stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

    }
}
