package com.gachon.morningroutin_layout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalkFlowActivity extends AppCompatActivity implements SensorEventListener {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView, progressPercentView;

    // 현재 걸음 수
    int currentSteps = 0, walk_int;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_flow);

        // resetButton = findViewById(R.id.resetButton);
        stepCountView = findViewById(R.id.stepCountView);
        progressPercentView = findViewById(R.id.progress_walk);


        DatabaseReference planRef = database.child("daily").child("12345");
        planRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);
                if (plan != null) {
                    walk_int = Integer.parseInt(plan.input);

                    TextView showArchive = findViewById(R.id.archiveWalk);
                    showArchive.setText("목표 걸음 수: " + plan.input);



                    // 활동 퍼미션 체크
                    if(ContextCompat.checkSelfPermission(WalkFlowActivity.this,
                            Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

                        requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
                    }

                    // 걸음 센서 연결
                    // * 옵션
                    // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
                    // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
                    //
                    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

                    // 디바이스에 걸음 센서의 존재 여부 체크
                    if (stepCountSensor == null) {
                        Toast.makeText(WalkFlowActivity.this, "No Step Sensor", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        findViewById(R.id.walkCancel_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.walkSuccess_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }


    public void onStart() {
        super.onStart();
        if(stepCountSensor != null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));
                String progress = String.format("%.02f", (float)currentSteps / walk_int * 100);
                if (progress.compareTo("100.00") == 0) {
                    findViewById(R.id.walkCancel_BUTTON).setVisibility(View.INVISIBLE);
                    findViewById(R.id.walkSuccess_BUTTON).setVisibility(View.VISIBLE);

                    // 선물을 추가하는 코드
                    // tree 0-9 까지 있으므로, 0-9 랜덤 난수 발생시킨다.

                    double num = Math.random();
                    int tree_id = (int)(num * 10);


                    // DB 에 업데이트한다.
                    DatabaseReference rewardRef = database.child("inventory");
                    rewardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            getInventory inventory = snapshot.getValue(getInventory.class);
                            if (tree_id == 1) {
                                findViewById(R.id.pedometer_tree01).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree01();
                                rewardRef.child("tree01").setValue(remain_tree + 1);
                            } else if (tree_id == 2) {
                                findViewById(R.id.pedometer_tree02).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree02();
                                rewardRef.child("tree02").setValue(remain_tree + 1);
                            } else if (tree_id == 3) {
                                findViewById(R.id.pedometer_tree03).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree03();
                                rewardRef.child("tree03").setValue(remain_tree + 1);
                            } else if (tree_id == 4) {
                                findViewById(R.id.pedometer_tree04).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree04();
                                rewardRef.child("tree04").setValue(remain_tree + 1);
                            } else if (tree_id == 5) {
                                findViewById(R.id.pedometer_tree05).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree05();
                                rewardRef.child("tree05").setValue(remain_tree + 1);
                            } else if (tree_id == 6) {
                                findViewById(R.id.pedometer_tree06).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree06();
                                rewardRef.child("tree06").setValue(remain_tree + 1);
                            } else if (tree_id == 7) {
                                findViewById(R.id.pedometer_tree07).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree07();
                                rewardRef.child("tree07").setValue(remain_tree + 1);
                            } else if (tree_id == 8) {
                                findViewById(R.id.pedometer_tree08).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree08();
                                rewardRef.child("tree08").setValue(remain_tree + 1);
                            } else if (tree_id == 9) {
                                findViewById(R.id.pedometer_tree09).setVisibility(View.VISIBLE);
                                int remain_tree = inventory.getTree09();
                                rewardRef.child("tree09").setValue(remain_tree + 1);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
                progressPercentView.setText("진행도: " + progress + "%");
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}