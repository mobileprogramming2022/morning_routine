package com.gachon.morningroutin_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
    // get current date information
    long currentDate = System.currentTimeMillis();
    Date mDate = new Date(currentDate);
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
    String stringDate = date.format(mDate);
    String array[] = stringDate.split("-");
    int year = Integer.parseInt(array[0]);
    int month = Integer.parseInt(array[1]);

    // Changes
    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private AlarmManager alarmManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        findViewById(R.id.processView).setVisibility(View.GONE);
        findViewById(R.id.imageView).setVisibility(View.VISIBLE);
        findViewById(R.id.textView).setVisibility(View.VISIBLE);

        ImageButton addAlarm = findViewById(R.id.imageView);

        ImageButton graph = findViewById(R.id.Graph);
        ImageButton option = findViewById(R.id.Options);
        ImageButton reward = findViewById(R.id.Reward);
        TextView txtGraph = findViewById(R.id.textGraph);
        TextView txtOption = findViewById(R.id.textOptions);
        TextView txtReward = findViewById(R.id.textReward);


        // if one month or more have passed from lastly visited date, wipe stats database
        database.child("lastTimeVisited").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getLastTimeVisited lastTimeVisited = snapshot.getValue(getLastTimeVisited.class);
                int last_year = lastTimeVisited.getYear();
                int last_month = lastTimeVisited.getMonth();

                if(year > last_year || month > last_month) {
                    for (int i = 0; i < 31; i++) {
                        if (i < 10) {
                            database.child("stats").child("day0" + i).setValue(2);
                        } else {
                            database.child("stats").child("day" + i).setValue(2);
                        }
                    }
                    // update last time visited
                    database.child("lastTimeVisited").child("month").setValue(month);
                    database.child("lastTimeVisited").child("year").setValue(year);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // 12345 ??? ?????? ??? ??? ?????? ??????
        DatabaseReference planRef = database.child("daily").child("12345");

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);
                if (plan != null) {

                    findViewById(R.id.processView).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView).setVisibility(View.GONE);
                    findViewById(R.id.textView).setVisibility(View.INVISIBLE);

                    TextView myTYPE = findViewById(R.id.myArchive);
                    TextView myWakeTime = findViewById(R.id.myWakeTime);
                    TextView mySleepTime = findViewById(R.id.mySleepTime);

                    myTYPE.setText("?????? ??????: " + plan.getType());
                    myWakeTime.setText("?????? ??????: " + plan.getWakeTime());
                    mySleepTime.setText("?????? ??????: " + plan.getSleepTime());



                    String specific_type = plan.getSpecificType();
                    String input_data = plan.getInput();
                    if (specific_type.compareTo("PEDOMETER") == 0) {
                        myTYPE.setText("????????? ?????? ????????? " + input_data + "??? ?????????!");
                    } else if (specific_type.compareTo("TIMER") == 0) {
                        // TYPE ??? STUDY ?????? EXERCISE ?????? ??????
                        String type_db = plan.getType();
                        StringTokenizer st = new StringTokenizer(input_data, ":");
                        String hour_db = st.nextToken();
                        String minute_db = st.nextToken();
                        String second_db = st.nextToken();

                        if (type_db.compareTo("STUDY") == 0) {
                            myTYPE.setText("????????? ????????? " );
                        } else {
                            myTYPE.setText("????????? ????????? " );
                        }

                        if (hour_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + hour_db + "??????");
                        } if (minute_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + " " + minute_db + "???");
                        } if  (second_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + " " + second_db + "???");
                        }

                        myTYPE.setText(myTYPE.getText() + " ?????? ?????????!");
                    } else if (specific_type.compareTo("TODO") == 0) {
                        String type_db = plan.getType();
                        if (type_db.compareTo("EXERCISE") == 0) {
                            myTYPE.setText("????????? ???????????? ???????????????!");
                        } else if (type_db.compareTo("STUDY") == 0) {
                            myTYPE.setText("????????? ???????????? ???????????????!");
                        } else {
                            StringTokenizer st = new StringTokenizer(plan.getInput(), "#");
                            int today_list = Integer.parseInt(st.nextToken());
                            myTYPE.setText("????????? ??? ?????? " + today_list + "??? ??????!");
                        }
                    }

                    findViewById(R.id.changePlanButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class);
                            startActivity(intent);
                        }
                    });

                    findViewById(R.id.deletePlanButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            planRef.removeValue();
                            Intent intent2 = new Intent(getApplicationContext() ,Alarm.class);
                            PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),20, intent2, PendingIntent.FLAG_MUTABLE);

                            alarmManager.cancel(pendingIntent);

                            PendingIntent pendingIntent2= PendingIntent.getBroadcast(getApplicationContext(),30, intent2, PendingIntent.FLAG_MUTABLE);
                            alarmManager.cancel(pendingIntent2);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class);
                startActivity(intent);
            }
        });


        //image button
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        //text for button
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        txtOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        txtReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
    }

}