package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class StudyInflationActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    int mYear, mMonth, mDay, mHour, mMinute;
    int SELECTED_SCREEN = 0; // init state
    final int TIME_SCREEN = 2;
    final int TODO_SCREEN = 3;

    String wakeTime = "07:00";
    String sleepTime = "23:30";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_inflation);



        Button sleepTimeBtn = findViewById(R.id.studySleepTime);
        Button wakeTimeBtn = findViewById(R.id.studyWakeTime);

        Button studyTimeButton = findViewById(R.id.studyTimeButton);
        Button studyTodoButton = findViewById(R.id.studyTodoButton);
        EditText studyUserInput = findViewById(R.id.studyUserInput);


        findViewById(R.id.studyTodoText).setVisibility(View.INVISIBLE);

        // Intent part
        Intent timeIntent = getIntent();
        String savedTime = timeIntent.getStringExtra("TIME");
        if (savedTime != null) {
            StringTokenizer st = new StringTokenizer(savedTime, " ");
            String SLEEP_TIME_FROM = st.nextToken();
            String WAKE_TIME_FROM = st.nextToken();

            sleepTimeBtn.setText(SLEEP_TIME_FROM);
            wakeTimeBtn.setText(WAKE_TIME_FROM);
            sleepTime = SLEEP_TIME_FROM;
            wakeTime = WAKE_TIME_FROM;
        }


        ImageButton gotoWalkActivity = findViewById(R.id.gotoWalkImgButton);
        gotoWalkActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });

        ImageButton gotoTodoActivity = findViewById(R.id.fromStudygotoTodoImgButton);
        gotoTodoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoInflationActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });

        studyTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = TIME_SCREEN;
                studyUserInput.setVisibility(View.VISIBLE);
                findViewById(R.id.studyTodoText).setVisibility(View.INVISIBLE);
                studyUserInput.setHint("시간:분:초");
            }
        });

        studyTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = TODO_SCREEN;
                studyUserInput.setVisibility(View.INVISIBLE);
                findViewById(R.id.studyTodoText).setVisibility(View.VISIBLE);
            }
        });



        @SuppressLint("CutPasteId") Button SAVE = findViewById(R.id.studySaveBtn);
        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_SCREEN == 0) {
                    Toast.makeText(StudyInflationActivity.this, "시간 / Todo 중에서 선택하세요!", Toast.LENGTH_SHORT).show();
                } else if (SELECTED_SCREEN == TIME_SCREEN) {

                    EditText et = (EditText) findViewById(R.id.studyUserInput);
                    boolean isInput = false;
                    if (et.getText().toString().length() != 0) {
                        isInput = true;
                    }

                    if (!isInput) {
                        Toast.makeText(StudyInflationActivity.this, "목표 시간을 입력하세요!", Toast.LENGTH_SHORT).show();
                    } else {

                        String USER_INPUT_DATA = et.getText().toString();
                        String wakeTimeDB = wakeTime;
                        String sleepTimeDB = sleepTime;

                        // DB 에 저장하고
                        addPlanToFB("STUDY", "TIMER", USER_INPUT_DATA, wakeTimeDB, sleepTimeDB);

                        // Main activity 띄운다.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);


                    }

                } else {
                    Toast.makeText(StudyInflationActivity.this, "To do 설정 화면으로 이동합니다", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.backToHomeButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        /*--------------------------------------
        For Calendar
         */

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);


        wakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(StudyInflationActivity.this, studyWakeTimeSetListener, mHour, mMinute, false).show();
            }
        });

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(StudyInflationActivity.this, studySleepTimeSetListener, mHour, mMinute, false).show();
            }
        });

    }



    TimePickerDialog.OnTimeSetListener studyWakeTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour = hourOfDay;
            mMinute = minute;

            UpdateNow("WAKE");
        }
    };

    TimePickerDialog.OnTimeSetListener studySleepTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour = hourOfDay;
            mMinute = minute;

            UpdateNow("SLEEP");
        }
    };

    void UpdateNow(String id) {

        if (id.compareTo("WAKE") == 0) {
            Button wakeTimeView = findViewById(R.id.studyWakeTime);
            wakeTime = String.format("%02d:%02d", mHour, mMinute);
            wakeTimeView.setText(wakeTime);
        } else {
            Button sleepTimeView = findViewById(R.id.studySleepTime);
            sleepTime = String.format("%02d:%02d", mHour, mMinute);
            sleepTimeView.setText(sleepTime);
        }
    }

    public void addPlanToFB(String TYPE, String specific_type, String USER_INPUT_DATA, String wakeTime, String sleepTime) {
        getTodayPlan todayPlan = new getTodayPlan(TYPE, specific_type, USER_INPUT_DATA, wakeTime, sleepTime);
        database.child("daily").child("12345").setValue(todayPlan);
    }


}