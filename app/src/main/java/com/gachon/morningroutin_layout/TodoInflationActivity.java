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

public class TodoInflationActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    int mYear, mMonth, mDay, mHour, mMinute;
    int SELECTED_SCREEN = 0; // init state
    final int TODO_SCREEN = 3;

    String wakeTime = "07:00";
    String sleepTime = "23:30";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_inflation);



        Button sleepTimeBtn = findViewById(R.id.todoSleepTime);
        Button wakeTimeBtn = findViewById(R.id.todoWakeTime);


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

        ImageButton gotoExerciseActivity = findViewById(R.id.fromToDoGotoExerciseButton);
        gotoExerciseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });

        ImageButton gotoStudyActivity = findViewById(R.id.fromToDoGotoStudyImgButton);
        gotoStudyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudyInflationActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });



        @SuppressLint("CutPasteId") Button SAVE = findViewById(R.id.todoSaveButton);
        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_SCREEN == -1) {
                    Toast.makeText(TodoInflationActivity.this, "Todo 중에서 선택하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TodoInflationActivity.this, "Todo 화면으로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.backToHomeButton3).setOnClickListener(new View.OnClickListener() {
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
                new TimePickerDialog(TodoInflationActivity.this, todoWakeTimeSetListener, mHour, mMinute, false).show();
            }
        });

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(TodoInflationActivity.this, todoSleepTimeSetListener, mHour, mMinute, false).show();
            }
        });

    }



    TimePickerDialog.OnTimeSetListener todoWakeTimeSetListener
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

    TimePickerDialog.OnTimeSetListener todoSleepTimeSetListener
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