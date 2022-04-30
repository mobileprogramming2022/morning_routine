package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmMainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examplealarm);

        timePicker=findViewById(R.id.tp_timepicker);

    }// onCreate()..

    public void regist(View view) {

        Intent intent = new Intent(this, AlarmExample.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour=timePicker.getHour();
            minute=timePicker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 지정한 시간에 매일 알림
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);

    }// regist()..

    public void unregist(View view) {
        Intent intent = new Intent(this, AlarmExample.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }// unregist()..

}// MainActivity class..


