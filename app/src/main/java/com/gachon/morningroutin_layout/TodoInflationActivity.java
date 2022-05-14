package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class TodoInflationActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private AlarmManager alarmManager;

    int mYear, mMonth, mDay;
    int mHour_wake = 7;
    int mMinute_wake = 0;
    int mHour_sleep = 23;
    int mMinute_sleep = 30;
    int SELECTED_SCREEN = 0; // init state
    final int TIME_SCREEN = 2;
    final int TODO_SCREEN = 3;

    String wakeTime = "07:00";
    String sleepTime = "23:30";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_inflation);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


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


        Intent alarmIntent = new Intent(getApplicationContext(), Alarm.class);
        Button SAVE = findViewById(R.id.todoSaveButton);
        SAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SELECTED_SCREEN == -1) {
                    Toast.makeText(TodoInflationActivity.this, "Todo 중에서 선택하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    int count = 0;
                    String input = "";
                    EditText chk1 = (EditText) findViewById(R.id.todoList1);
                    if (chk1.getText().toString().length() != 0) {
                        input = input + chk1.getText().toString() + "#";
                        count++;
                    }

                    EditText chk2 = (EditText) findViewById(R.id.todoList2);
                    if (chk2.getText().toString().length() != 0) {
                        input = input + chk2.getText().toString() + "#";
                        count++;
                    }

                    EditText chk3 = (EditText) findViewById(R.id.todoList3);
                    if (chk3.getText().toString().length() != 0) {
                        input = input + chk3.getText().toString() + "#";
                        count++;
                    }

                    if (input.compareTo("") == 0) {
                        Toast.makeText(TodoInflationActivity.this, "항목들을 하나 이상 입력하세요!", Toast.LENGTH_SHORT).show();
                    } else {
                        String stringCount = Integer.toString(count);

                        String wakeTimeDB = wakeTime;
                        String sleepTimeDB = sleepTime;

                        // DB 에 저장하고
                        addPlanToFB("CHECK_LIST", "TODO", stringCount + "#" + input, wakeTimeDB, sleepTimeDB);

                        setMorningAlarm(alarmIntent);
                        setNightAlarm(alarmIntent);

                        // Main activity 띄운다.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
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

        mHour_wake = cal.get(Calendar.HOUR_OF_DAY);
        mMinute_wake = cal.get(Calendar.MINUTE);
        mHour_sleep = cal.get(Calendar.HOUR_OF_DAY);
        mMinute_sleep = cal.get(Calendar.MINUTE);


        wakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(TodoInflationActivity.this, todoWakeTimeSetListener, mHour_wake, mMinute_wake, false).show();
            }
        });

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(TodoInflationActivity.this, todoSleepTimeSetListener, mHour_sleep, mMinute_sleep, false).show();
            }
        });

    }



    TimePickerDialog.OnTimeSetListener todoWakeTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour_wake = hourOfDay;
            mMinute_wake = minute;

            UpdateNow("WAKE");
        }
    };

    TimePickerDialog.OnTimeSetListener todoSleepTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            //사용자가 입력한 값을 가져온뒤
            mHour_sleep = hourOfDay;
            mMinute_sleep = minute;

            UpdateNow("SLEEP");
        }
    };

    @SuppressLint("DefaultLocale")
    void UpdateNow(String id) {

        if (id.compareTo("WAKE") == 0) {
            Button wakeTimeView = findViewById(R.id.todoWakeTime);
            wakeTime = String.format("%02d:%02d", mHour_wake, mMinute_wake);
            wakeTimeView.setText(wakeTime);
        } else {
            Button sleepTimeView = findViewById(R.id.todoSleepTime);
            sleepTime = String.format("%02d:%02d", mHour_sleep, mMinute_sleep);
            sleepTimeView.setText(sleepTime);
        }
    }

    public void addPlanToFB(String TYPE, String specific_type, String USER_INPUT_DATA, String wakeTime, String sleepTime) {
        getTodayPlan todayPlan = new getTodayPlan(TYPE, specific_type, USER_INPUT_DATA, wakeTime, sleepTime);
        database.child("daily").child("12345").setValue(todayPlan);
    }

    void setMorningAlarm(Intent alarmIntent){
        Bundle bundle = new Bundle();
        bundle.putString("state", "morning");
        //bundle.putString("what",Integer.toString(Alarm.justSet_morning - 1));
        alarmIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 20, alarmIntent, PendingIntent.FLAG_MUTABLE);//MUTABLE이라 바꾸면 자동으로 바뀐다.

        long now = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour_wake);
        calendar.set(Calendar.MINUTE, mMinute_wake);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if(calendar.getTimeInMillis() < now){
            Log.d("알람","현재시간보다 이전 - morning");
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] today = sdf.format(date).split("-"); //year,month,day of today

            int year_today = Integer.parseInt(today[0]);
            int month_today = Integer.parseInt(today[1]);
            int day_today = Integer.parseInt(today[2]);

            GregorianCalendar nowcalendar= new GregorianCalendar(year_today, month_today, day_today + 1,mHour_wake, mMinute_wake);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nowcalendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nowcalendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
        }
    }

    void setNightAlarm(Intent alarmIntent){
        //Intent alarmIntent2 = new Intent(getApplicationContext(), Alarm.class);
        Bundle bundle = new Bundle();
        bundle.putString("state", "night");
        //Log.d("무엇", Integer.toString(Alarm.justSet_night - 1));    // 로그 확인용
        //bundle.putString("what",Integer.toString(Alarm.justSet_night - 1));
        alarmIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 30, alarmIntent, PendingIntent.FLAG_MUTABLE);//MUTABLE이라 바꾸면 자동으로 바뀐다.

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, mHour_sleep);
        calendar2.set(Calendar.MINUTE, mMinute_sleep);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        long now = System.currentTimeMillis();

        if(calendar2.getTimeInMillis() < now){
            Log.d("알람","현재시간보다 이전 - night");
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String[] today = sdf.format(date).split("-"); //year,month,day of today

            int year_today = Integer.parseInt(today[0]);
            int month_today = Integer.parseInt(today[1]);
            int day_today = Integer.parseInt(today[2]);

            GregorianCalendar nowcalendar= new GregorianCalendar(year_today, month_today, day_today + 1,mHour_wake, mMinute_wake);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nowcalendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nowcalendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            }
        }
    }

}