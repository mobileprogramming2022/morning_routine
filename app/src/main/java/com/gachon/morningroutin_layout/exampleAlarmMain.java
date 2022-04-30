package com.gachon.morningroutin_layout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class exampleAlarmMain extends AppCompatActivity {

    int hour,minute; //input from user
    AlarmManager alarmManager;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examplealarm);
        //오늘 date 받아오기 --> 이를 알람설정에 적용.

//알람관리자 소환
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        timePicker = findViewById(R.id.tp_timepicker);
        Button cancelAlarm = findViewById(R.id.cacelAlarm);
        Button setAlarm3btn = findViewById(R.id.setAlarm3);

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtn3(view);
            }
        });
        setAlarm3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBtn_setAlarm(view);
            }
        });

    }

    public void clickBtn3(View view) {
//반복 알람 종료

//알람매니저에 보류되어 있는
//PendingIntent를 cancel하면 됨
        Intent intent=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,20, intent, PendingIntent.FLAG_MUTABLE);

        alarmManager.cancel(pendingIntent);
    }

    public void clickBtn_setAlarm(View view) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }

        Intent intent= new Intent(exampleAlarmMain.this, AlarmActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(exampleAlarmMain.this,30,intent,PendingIntent.FLAG_MUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Log.d("예약시간",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        //AlarmManager.INTERVAL_DAY
       // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                //AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent); //Interval 하루로 변환.
    }
}
