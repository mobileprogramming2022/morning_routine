package com.gachon.morningroutin_layout;

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
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WalkFlowActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView, progressPercentView;
    ImageView successImage;
    Button resetButton;

    // 현재 걸음 수
    int currentSteps = 0, walk_int = 0;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_flow);

        stepCountView = findViewById(R.id.stepCountView);
        resetButton = findViewById(R.id.resetButton);
        progressPercentView = findViewById(R.id.progress_walk);
        successImage = findViewById(R.id.okIndicate);
        successImage.setVisibility(View.INVISIBLE);


        Intent getWalkCounter = getIntent();
        String walkArchive = getWalkCounter.getStringExtra("WALK_ARCHIVE_COUNT");
        TextView archiveWalkCount = findViewById(R.id.archiveWalk);
        archiveWalkCount.setText("목표 걸음 수: " + walkArchive);

        walk_int = Integer.parseInt(walkArchive);



        // 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
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
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        // 리셋 버튼 추가 - 리셋 기능
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));
                progressPercentView.setText("진행도: 0.00%");
                successImage.setVisibility(View.INVISIBLE);
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
                    successImage.setVisibility(View.VISIBLE);
                }
                progressPercentView.setText("진행도: " + progress + "%");
            }

        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}