package com.gachon.morningroutin_layout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Log.d("알람","justSet_morning" + Integer.toString(justSet_morning));
        // Log.d("알람","justSet_night" + Integer.toString(justSet_night));
        //Toast.makeText(context, "알람~!!", Toast.LENGTH_SHORT).show();    // AVD 확인용
        Log.d("알람", "알람입니다.");    // 로그 확인용
        Bundle extras = intent.getExtras();
        if(extras != null){
            Log.d("알람", intent.getExtras().getString("state"));    // 로그 확인용
        }

        String what_msg = intent.getExtras().getString("what");
        //Log.d("무엇", what_msg);    // 로그 확인용
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(intent.getExtras().getString("state").equals("morning")) {
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,20,intent,PendingIntent.FLAG_MUTABLE);

            //알람 설정(20초 후)+20000 -> 8640000(24시간 - 하루) /2분(20000 * 3 * 2 = 120000
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+8640000,pendingIntent);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+8640000,pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+20000,pendingIntent);
            }

            Intent tempIntent = new Intent(context.getApplicationContext(), MorningAlarmActivity.class);
            tempIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(tempIntent);
        }
        if(intent.getExtras().getString("state").equals("night")){
            PendingIntent pendingIntent2=PendingIntent.getBroadcast(context,30,intent,PendingIntent.FLAG_MUTABLE);

            //알람 설정(20초 후)+20000 -> 8640000(24시간 - 하루) /2분(20000 * 3 * 2 = 120000 1분 = 60000 * 60 * 24
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+8640000,pendingIntent2);
                //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,pendingIntent);
            }else{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+8640000,pendingIntent2);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+20000,pendingIntent);
            }

            Intent tempIntent = new Intent(context.getApplicationContext(), NightAlarmActivity.class);
            tempIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(tempIntent);
        }
    }
}
