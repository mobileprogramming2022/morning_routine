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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

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


        // 12345 는 개인 폰 별 고유 번호
        DatabaseReference planRef = database.child("daily").child("12345");

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);
                if (plan != null) {
                    Toast.makeText(MainActivity.this, "DB 에서 plan 을 가져왔습니다. ", Toast.LENGTH_SHORT).show();

                    findViewById(R.id.processView).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView).setVisibility(View.GONE);
                    findViewById(R.id.textView).setVisibility(View.INVISIBLE);
                    findViewById(R.id.startActivityImmediately).setVisibility(View.VISIBLE);

                    TextView myTYPE = findViewById(R.id.myArchive);
                    TextView myWakeTime = findViewById(R.id.myWakeTime);
                    TextView mySleepTime = findViewById(R.id.mySleepTime);

                    myTYPE.setText("나의 목표: " + plan.getType());
                    myWakeTime.setText("기상 시간: " + plan.getWakeTime());
                    mySleepTime.setText("취침 시간: " + plan.getSleepTime());



                    String specific_type = plan.getSpecificType();
                    String input_data = plan.getInput();
                    if (specific_type.compareTo("PEDOMETER") == 0) {
                        myTYPE.setText("오늘은 걷기 운동을 " + input_data + "보 해봐요!");
                    } else if (specific_type.compareTo("TIMER") == 0) {
                        // TYPE 이 STUDY 인지 EXERCISE 인지 확인
                        String type_db = plan.getType();
                        StringTokenizer st = new StringTokenizer(input_data, ":");
                        String hour_db = st.nextToken();
                        String minute_db = st.nextToken();
                        String second_db = st.nextToken();

                        if (type_db.compareTo("STUDY") == 0) {
                            myTYPE.setText("오늘은 공부를 " );
                        } else {
                            myTYPE.setText("오늘은 운동을 " );
                        }

                        if (hour_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + hour_db + "시간");
                        } if (minute_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + " " + minute_db + "분");
                        } if  (second_db.compareTo("0") != 0) {
                            myTYPE.setText(myTYPE.getText() + " " + second_db + "초");
                        }

                        myTYPE.setText(myTYPE.getText() + " 동안 해봐요!");
                    } else if (specific_type.compareTo("TODO") == 0) {
                        String type_db = plan.getType();
                        if (type_db.compareTo("EXERCISE") == 0) {
                            myTYPE.setText("오늘은 자유롭게 운동해봐요!");
                        } else if (type_db.compareTo("STUDY") == 0) {
                            myTYPE.setText("오늘은 자유롭게 공부해봐요!");
                        } else {
                            StringTokenizer st = new StringTokenizer(plan.getInput(), "#");
                            int today_list = Integer.parseInt(st.nextToken());
                            myTYPE.setText("오늘의 할 일은 " + today_list + "개 에요!");
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

                    findViewById(R.id.startActivityImmediately).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (plan.specificType.compareTo("PEDOMETER") == 0) {
                                Intent intent = new Intent(getApplicationContext(), WalkFlowActivity.class);
                                startActivity(intent);
                            } else if (plan.specificType.compareTo("TIMER") == 0) {
                                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                                startActivity(intent);
                            } else if (plan.specificType.compareTo("TODO") == 0) {
                                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                                startActivity(intent);
                            }
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