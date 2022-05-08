package com.gachon.morningroutin_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    // Changes
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.processView).setVisibility(View.GONE);
        findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        findViewById(R.id.textView).setVisibility(View.VISIBLE);

        TextView txtView = findViewById(R.id.textView);
        ImageButton addAlarm = findViewById(R.id.imageView);

        ImageButton graph = findViewById(R.id.Graph);
        ImageButton option = findViewById(R.id.Options);
        ImageButton reward = findViewById(R.id.Reward);
        TextView txtGraph = findViewById(R.id.textGraph);
        TextView txtOption = findViewById(R.id.textOptions);
        TextView txtReward = findViewById(R.id.textReward);


        // 12345 는 개인 폰 별 고유 번호
        DatabaseReference planRef = database.child("daily").child("12345");

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);
                if (plan == null) {
                    // 할일 없음.
                } else {
                    Toast.makeText(MainActivity.this, "DB 에서 plan 을 가져왔습니다. ", Toast.LENGTH_SHORT).show();

                    findViewById(R.id.processView).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView).setVisibility(View.GONE);
                    findViewById(R.id.textView).setVisibility(View.INVISIBLE);

                    TextView myTYPE = findViewById(R.id.myArchive);
                    TextView myWakeTime = findViewById(R.id.myWakeTime);
                    TextView mySleepTime = findViewById(R.id.mySleepTime);
                    TextView myInput = findViewById(R.id.myInput);

                    TextView myOwnID = findViewById(R.id.showOwnID);

                    myTYPE.setText("나의 목표: " + plan.getType());
                    myWakeTime.setText("기상 시간: " + plan.getWakeTime());
                    mySleepTime.setText("취침 시간: " + plan.getSleepTime());


                    findViewById(R.id.changePlanButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.deletePlanButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            planRef.removeValue();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class);
                startActivity(intent);
            }
        });


        //image button
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        //text for button
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        txtReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
    }

}