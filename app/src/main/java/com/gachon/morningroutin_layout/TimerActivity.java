package com.gachon.morningroutin_layout;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    LinearLayout timeCountLV;
    int hourET, minuteET, secondET;
    TextView hourTV, minuteTV, secondTV, finishTV;
    Button startBtn;
    int hour, minute, second;
    boolean isChecked = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        findViewById(R.id.timerCancel_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        DatabaseReference planRef = database.child("daily").child("12345");
        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);

                String timeData = plan.getInput();
                StringTokenizer st = new StringTokenizer(timeData, ":");


                timeCountLV = (LinearLayout)findViewById(R.id.timeCountLV);

                hourTV = (TextView)findViewById(R.id.hourTV);
                minuteTV = (TextView)findViewById(R.id.minuteTV);
                secondTV = (TextView)findViewById(R.id.secondTV);
                finishTV = (TextView)findViewById(R.id.finishTV);

                startBtn = (Button)findViewById(R.id.startBtn);

                hourET = Integer.parseInt(st.nextToken());
                minuteET = Integer.parseInt(st.nextToken());
                secondET = Integer.parseInt(st.nextToken());

                timeCountLV.setVisibility(View.VISIBLE);
                hourTV.setText(Integer.toString(hourET));
                minuteTV.setText(Integer.toString(minuteET) );
                secondTV.setText(Integer.toString(secondET) );

                // 시작버튼 이벤트 1처리
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isChecked) {
                            startBtn.setText("실행 중입니다!");


                            hour = hourET;
                            minute = minuteET;
                            second = secondET;

                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    // 반복실행할 구문

                                    // 0초 이상이면
                                    if(second != 0) {
                                        //1초씩 감소
                                        second--;

                                        // 0분 이상이면
                                    } else if(minute != 0) {
                                        // 1분 = 60초
                                        second = 60;
                                        second--;
                                        minute--;

                                        // 0시간 이상이면
                                    } else if(hour != 0) {
                                        // 1시간 = 60분
                                        second = 60;
                                        minute = 60;
                                        second--;
                                        minute--;
                                        hour--;
                                    }

                                    //시, 분, 초가 10이하(한자리수) 라면
                                    // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
                                    if(second <= 9){
                                        secondTV.setText("0" + second);
                                    } else {
                                        secondTV.setText(Integer.toString(second));
                                    }

                                    if(minute <= 9){
                                        minuteTV.setText("0" + minute);
                                    } else {
                                        minuteTV.setText(Integer.toString(minute));
                                    }

                                    if(hour <= 9){
                                        hourTV.setText("0" + hour);
                                    } else {
                                        hourTV.setText(Integer.toString(hour));
                                    }

                                    if(hour == 0 && minute == 0 && second == 0) {
                                        isChecked = true;
                                        timer.cancel();//타이머 종료
                                        finishTV.setText("목표를 완료했습니다.");
                                        timeCountLV.setVisibility(View.INVISIBLE);


                                        // 선물을 추가하는 코드
                                        // tree 0-9 까지 있으므로, 0-9 랜덤 난수 발생시킨다.


                                        double num = Math.random();   // 0.00000001-0.999999999
                                        int tree_id = ((int)(num * 15) + 1); // 1.00000009-15.xxxxxxxxx



                                        // DB 에 업데이트한다.
                                        DatabaseReference rewardRef = database.child("inventory");
                                        rewardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                getInventory inventory = snapshot.getValue(getInventory.class);
                                                if (tree_id == 1) {
                                                    findViewById(R.id.timer_tree01).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree01();
                                                    rewardRef.child("tree01").setValue(remain_tree + 1);
                                                } else if (tree_id == 2) {
                                                    findViewById(R.id.timer_tree02).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree02();
                                                    rewardRef.child("tree02").setValue(remain_tree + 1);
                                                } else if (tree_id == 3) {
                                                    findViewById(R.id.timer_tree03).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree03();
                                                    rewardRef.child("tree03").setValue(remain_tree + 1);
                                                } else if (tree_id == 4) {
                                                    findViewById(R.id.timer_tree04).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree04();
                                                    rewardRef.child("tree04").setValue(remain_tree + 1);
                                                } else if (tree_id == 5) {
                                                    findViewById(R.id.timer_tree05).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree05();
                                                    rewardRef.child("tree05").setValue(remain_tree + 1);
                                                } else if (tree_id == 6) {
                                                    findViewById(R.id.timer_tree06).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree06();
                                                    rewardRef.child("tree06").setValue(remain_tree + 1);
                                                } else if (tree_id == 7) {
                                                    findViewById(R.id.timer_tree07).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree07();
                                                    rewardRef.child("tree07").setValue(remain_tree + 1);
                                                } else if (tree_id == 8) {
                                                    findViewById(R.id.timer_tree08).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree08();
                                                    rewardRef.child("tree08").setValue(remain_tree + 1);
                                                } else if (tree_id == 9) {
                                                    findViewById(R.id.timer_tree09).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree09();
                                                    rewardRef.child("tree09").setValue(remain_tree + 1);
                                                }else if (tree_id == 10) {
                                                    findViewById(R.id.timer_tree10).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree10();
                                                    rewardRef.child("tree10").setValue(remain_tree + 1);
                                                }else if (tree_id == 11) {
                                                    findViewById(R.id.timer_tree11).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree11();
                                                    rewardRef.child("tree11").setValue(remain_tree + 1);
                                                }else if (tree_id == 12) {
                                                    findViewById(R.id.timer_tree12).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree12();
                                                    rewardRef.child("tree12").setValue(remain_tree + 1);
                                                }else if (tree_id == 13) {
                                                    findViewById(R.id.timer_tree13).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree13();
                                                    rewardRef.child("tree13").setValue(remain_tree + 1);
                                                }else if (tree_id == 14) {
                                                    findViewById(R.id.timer_tree14).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree14();
                                                    rewardRef.child("tree14").setValue(remain_tree + 1);
                                                }else if (tree_id == 15) {
                                                    findViewById(R.id.timer_tree15).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree15();
                                                    rewardRef.child("tree15").setValue(remain_tree + 1);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });





                                    }
                                }
                            };

                            //타이머를 실행
                            timer.schedule(timerTask, 0, 1000); //Timer 실행

                            findViewById(R.id.timerCancel_BUTTON).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        // 여기서부터

                        // 여기까지 상단의 if 내부에 있는 코드
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}