package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmSetActivity extends AppCompatActivity {

    int mYear, mMonth, mDay, mHour, mMinute;
    int SELECTED_SCREEN = 0; // init state
    final int EXERCISE_SCREEN = 1;
    final int STUDY_SCREEN = 2;
    final int TODO_SCREEN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ConstraintLayout container = findViewById(R.id.layout_select_inflation_screen);

        /*-------------------------------------
        만보기 화면 전환
         */
        Button SAVE = findViewById(R.id.SaveBtn);
        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_SCREEN == EXERCISE_SCREEN) {
                    EditText et = (EditText) findViewById(R.id.userTextInput);

                    boolean isInput = false;
                    if (et.getText().toString().length() != 0) {
                        isInput = true;
                    }

                    if (!isInput) {
                        Toast.makeText(AlarmSetActivity.this, "목표 걸음 수를 입력하세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), WalkFlowActivity.class).putExtra("WALK_ARCHIVE_COUNT", et.getText().toString());
                        startActivity(intent);
                    }
                } else if (SELECTED_SCREEN == STUDY_SCREEN) {
                    // Do something
                } else if (SELECTED_SCREEN == TODO_SCREEN) {
                    // Do Something
                }
            }
        });



        /*--------------------------------------
        For Calendar
         */

        Button sleepTimeBtn = findViewById(R.id.sleepTimeView);
        Button wakeTimeBtn = findViewById(R.id.wakeTimeView);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);


        wakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AlarmSetActivity.this, wakeTimeSetListener, mHour, mMinute, false).show();
            }
        });

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AlarmSetActivity.this, sleepTimeSetListener, mHour, mMinute, false).show();
            }
        });

        /*--------------------------------------
        For Inflation
         */
        ImageButton studyScrInflation = findViewById(R.id.showStudyScreen);
        studyScrInflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = STUDY_SCREEN;
                inflater.inflate(R.layout.activity_study_inflation, container, true);
            }
        });

        ImageButton walkScrInflation = findViewById(R.id.showActivityScreen);
        walkScrInflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = EXERCISE_SCREEN;
                inflater.inflate(R.layout.activity_exercise_inflation, container, true);
            }
        });

        ImageButton todoScrInflation = findViewById(R.id.showListScreen);
        todoScrInflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = TODO_SCREEN;
                inflater.inflate(R.layout.activity_todo_inflation, container, true);
            }
        });



        //image button
        ImageButton graph = findViewById(R.id.Graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        ImageButton option = findViewById(R.id.Options);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        ImageButton reward = findViewById(R.id.Reward);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        //text for button
        TextView txtGraph = findViewById(R.id.textGraph);
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        TextView txtOption = findViewById(R.id.textOptions);
        txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        TextView txtReward = findViewById(R.id.textReward);
        txtReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
    }


    TimePickerDialog.OnTimeSetListener wakeTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour = hourOfDay;
            mMinute = minute;

            //텍스트뷰의 값을 업데이트함

            UpdateNow("WAKE");
        }
    };

    TimePickerDialog.OnTimeSetListener sleepTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour = hourOfDay;
            mMinute = minute;

            //텍스트뷰의 값을 업데이트함

            UpdateNow("SLEEP");
        }
    };

    @SuppressLint("DefaultLocale")
    void UpdateNow(String id) {
        if (id.compareTo("WAKE") == 0) {
            Button wakeTimeView = findViewById(R.id.wakeTimeView);
            wakeTimeView.setText(String.format("%02d:%02d", mHour, mMinute));
        } else {
            Button sleepTimeView = findViewById(R.id.sleepTimeView);
            sleepTimeView.setText(String.format("%02d:%02d", mHour, mMinute));
        }
    }
}