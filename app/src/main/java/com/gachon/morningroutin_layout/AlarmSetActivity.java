package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class AlarmSetActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    int mYear, mMonth, mDay, mHour, mMinute;
    int SELECTED_SCREEN = 0; // init state
    final int WALK_SCREEN = 1;
    final int TIME_SCREEN = 2;
    final int TODO_SCREEN = 3;

    String wakeTime = "07:00";
    String sleepTime = "12:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);


        Button sleepTimeBtn = findViewById(R.id.sleepTimeView);
        Button wakeTimeBtn = findViewById(R.id.wakeTimeView);
        findViewById(R.id.exerciseTodoText).setVisibility(View.INVISIBLE);


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

        /*--------------------------------------
        For Inflation
         */
        ImageButton studyScrInflation = findViewById(R.id.showStudyScreen);
        studyScrInflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudyInflationActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });

        ImageButton todoScrInflation = findViewById(R.id.showListScreen);
        todoScrInflation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoInflationActivity.class).putExtra("TIME", sleepTime + " " + wakeTime);
                startActivity(intent);
            }
        });

        /*-------------------------------------
        Button click indicator
         */
        Button exerciseWalkButton = findViewById(R.id.exerciseWalkButton);
        Button exerciseTimeButton = findViewById(R.id.exerciseTimeButton);
        Button exerciseTodoButton = findViewById(R.id.exerciseTodoButton);
        exerciseWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = WALK_SCREEN;
                EditText et = (EditText) findViewById(R.id.userTextInput);
                et.setVisibility(View.VISIBLE);
                TextView todoText = findViewById(R.id.exerciseTodoText);
                todoText.setVisibility(View.INVISIBLE);
                et.setHint("목표 걸음 수 입력");
            }
        });

        exerciseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = TIME_SCREEN;
                EditText et = (EditText) findViewById(R.id.userTextInput);
                et.setVisibility(View.VISIBLE);
                TextView todoText = findViewById(R.id.exerciseTodoText);
                todoText.setVisibility(View.INVISIBLE);
                et.setHint("시간:분:초");
            }
        });

        exerciseTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECTED_SCREEN = TODO_SCREEN;
                EditText et = (EditText) findViewById(R.id.userTextInput);
                et.setVisibility(View.INVISIBLE);
                TextView todoText = findViewById(R.id.exerciseTodoText);
                todoText.setVisibility(View.VISIBLE);
            }
        });

        /*-------------------------------------
        화면 전환
        DB 데이터 포멧
        String TYPE, String USER_INPUT_DATA, String wakeTime, String sleepTime
         */
        Button SAVE = findViewById(R.id.SaveBtn);
        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_SCREEN == 0) {
                    Toast.makeText(AlarmSetActivity.this, "걷기 / 시간 / Todo 중에서 선택하세요!", Toast.LENGTH_SHORT).show();
                } else if (SELECTED_SCREEN == WALK_SCREEN) {
                    // 만보기로 보낸다.
                    EditText et = (EditText) findViewById(R.id.userTextInput);
                    boolean isInput = false;
                    if (et.getText().toString().length() != 0) {
                        isInput = true;
                    }

                    if (!isInput) {
                        Toast.makeText(AlarmSetActivity.this, "목표 걸음 수를 입력하세요!", Toast.LENGTH_SHORT).show();
                    } else {


                        String TYPE = "WALK";
                        String USER_INPUT_DATA = et.getText().toString();
                        String wakeTimeDB = wakeTime;
                        String sleepTimeDB = sleepTime;

                        // DB 에 저장하고
                        addPlanToFB(TYPE, USER_INPUT_DATA, wakeTimeDB, sleepTimeDB);

                        // Main activity 띄운다.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                } else if (SELECTED_SCREEN == TIME_SCREEN) {
                    EditText et = (EditText) findViewById(R.id.userTextInput);
                    boolean isInput = false;
                    if (et.getText().toString().length() != 0) {
                        isInput = true;
                    }

                    if (!isInput) {
                        Toast.makeText(AlarmSetActivity.this, "목표 시간을 입력하세요!", Toast.LENGTH_SHORT).show();
                    } else {

                        String TYPE = "TIMER";
                        String USER_INPUT_DATA = et.getText().toString();
                        String wakeTimeDB = wakeTime;
                        String sleepTimeDB = sleepTime;

                        // DB 에 저장하고
                        addPlanToFB(TYPE, USER_INPUT_DATA, wakeTimeDB, sleepTimeDB);

                        // Main activity 띄운다.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);


                    }
                } else {
                    Toast.makeText(AlarmSetActivity.this, "To do 설정 화면으로 이동합니다", Toast.LENGTH_SHORT).show();
                }

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
                new TimePickerDialog(AlarmSetActivity.this, wakeTimeSetListener, mHour, mMinute, false).show();
            }
        });

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AlarmSetActivity.this, sleepTimeSetListener, mHour, mMinute, false).show();
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

            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR,mHour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES,mMinute);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Good Morning");

            ArrayList<Integer> alarmDays = new ArrayList<Integer>();
            alarmDays.add(Calendar.MONDAY);
            alarmDays.add(Calendar.TUESDAY);
            alarmDays.add(Calendar.WEDNESDAY);
            alarmDays.add(Calendar.THURSDAY);
            alarmDays.add(Calendar.FRIDAY);
            alarmDays.add(Calendar.SATURDAY);
            alarmDays.add(Calendar.SUNDAY);
            intent.putExtra(AlarmClock.EXTRA_DAYS,alarmDays);

            intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);

            if(mHour <= 24 && mMinute <= 60) {
                startActivity(intent);
            }
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

            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR,mHour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES,mMinute);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Good Night");

            ArrayList<Integer> alarmDays = new ArrayList<Integer>();
            alarmDays.add(Calendar.MONDAY);
            alarmDays.add(Calendar.TUESDAY);
            alarmDays.add(Calendar.WEDNESDAY);
            alarmDays.add(Calendar.THURSDAY);
            alarmDays.add(Calendar.FRIDAY);
            alarmDays.add(Calendar.SATURDAY);
            alarmDays.add(Calendar.SUNDAY);
            intent.putExtra(AlarmClock.EXTRA_DAYS,alarmDays);

            intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);

            if(mHour <= 24 && mMinute <= 60) {
                startActivity(intent);
            }
            //텍스트뷰의 값을 업데이트함

            UpdateNow("SLEEP");
        }
    };

    @SuppressLint("DefaultLocale")
    void UpdateNow(String id) {

        if (id.compareTo("WAKE") == 0) {
            Button wakeTimeView = findViewById(R.id.wakeTimeView);
            wakeTime = String.format("%02d:%02d", mHour, mMinute);
            wakeTimeView.setText(wakeTime);
        } else {
            Button sleepTimeView = findViewById(R.id.sleepTimeView);
            sleepTime = String.format("%02d:%02d", mHour, mMinute);
            sleepTimeView.setText(sleepTime);
        }
    }

    // 기기 내부 번호 식별값(기기 고유 ID 요청하여 TRUE 부분에 Key 대신 들어갈 예정)
    public void addPlanToFB(String TYPE, String USER_INPUT_DATA, String wakeTime, String sleepTime) {
        getTodayPlan todayPlan = new getTodayPlan(TYPE, USER_INPUT_DATA, wakeTime, sleepTime);
        database.child("daily").child("12345").setValue(todayPlan);
    }
}