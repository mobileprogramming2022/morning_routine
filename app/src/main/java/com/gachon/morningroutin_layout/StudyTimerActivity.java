package com.gachon.morningroutin_layout;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class StudyTimerActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private NotificationManager mNotificationManager;
    MediaPlayer mediaPlayer;

    LinearLayout timeCountLV;
    int hourET, minuteET, secondET;
    TextView hourTV, minuteTV, secondTV, finishTV;
    Button startBtn;
    int hour, minute, second;
    boolean isChecked = false;

    // get current date information
    long currentDate = System.currentTimeMillis();
    Date mDate = new Date(currentDate);
    SimpleDateFormat date = new SimpleDateFormat("dd");
    String stringDate = date.format(mDate);
    int day = Integer.parseInt(stringDate);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_timer);

        mediaPlayer = MediaPlayer.create(StudyTimerActivity.this, R.raw.white_noise);
        mediaPlayer.start();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_NONE);

        findViewById(R.id.StudytimerCancel_BUTTON).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when fail, update stats into database
                if(day < 10){
                    database.child("stats").child("day0" + day).setValue(0);
                }
                else{
                    database.child("stats").child("day" + day).setValue(0);
                }

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


                timeCountLV = (LinearLayout)findViewById(R.id.StudytimeCountLV);

                hourTV = (TextView)findViewById(R.id.StudyhourTV);
                minuteTV = (TextView)findViewById(R.id.StudyminuteTV);
                secondTV = (TextView)findViewById(R.id.StudysecondTV);
                finishTV = (TextView)findViewById(R.id.StudyfinishTV);

                startBtn = (Button)findViewById(R.id.StudystartBtn);

                hourET = Integer.parseInt(st.nextToken());
                minuteET = Integer.parseInt(st.nextToken());
                secondET = Integer.parseInt(st.nextToken());

                timeCountLV.setVisibility(View.VISIBLE);
                hourTV.setText(Integer.toString(hourET));
                minuteTV.setText(Integer.toString(minuteET) );
                secondTV.setText(Integer.toString(secondET) );

                // ???????????? ????????? 1??????
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isChecked) {
                            startBtn.setText("?????? ????????????!");


                            hour = hourET;
                            minute = minuteET;
                            second = secondET;

                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    // ??????????????? ??????

                                    // 0??? ????????????
                                    if(second != 0) {
                                        //1?????? ??????
                                        second--;

                                        // 0??? ????????????
                                    } else if(minute != 0) {
                                        // 1??? = 60???
                                        second = 60;
                                        second--;
                                        minute--;

                                        // 0?????? ????????????
                                    } else if(hour != 0) {
                                        // 1?????? = 60???
                                        second = 60;
                                        minute = 60;
                                        second--;
                                        minute--;
                                        hour--;
                                    }

                                    //???, ???, ?????? 10??????(????????????) ??????
                                    // ?????? ?????? 0??? ????????? ( 8 -> 08 )
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
                                        timer.cancel();//????????? ??????
                                        finishTV.setText("????????? ??????????????????.");
                                        timeCountLV.setVisibility(View.INVISIBLE);


                                        // ????????? ???????????? ??????
                                        // tree 0-9 ?????? ????????????, 0-9 ?????? ?????? ???????????????.


                                        double num = Math.random();
                                        int tree_id = ((int)(num * 15) + 1); // 1.00000009-9.xxxxxxxxx

                                        // when success, update stats into database
                                        if(day < 10){
                                            database.child("stats").child("day0" + day).setValue(1);
                                        }
                                        else{
                                            database.child("stats").child("day" + day).setValue(1);
                                        }


                                        // DB ??? ??????????????????.
                                        DatabaseReference rewardRef = database.child("inventory");
                                        rewardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                getInventory inventory = snapshot.getValue(getInventory.class);
                                                if (tree_id == 1) {
                                                    findViewById(R.id.Studytimer_tree01).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree01();
                                                    rewardRef.child("tree01").setValue(remain_tree + 1);
                                                } else if (tree_id == 2) {
                                                    findViewById(R.id.Studytimer_tree02).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree02();
                                                    rewardRef.child("tree02").setValue(remain_tree + 1);
                                                } else if (tree_id == 3) {
                                                    findViewById(R.id.Studytimer_tree03).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree03();
                                                    rewardRef.child("tree03").setValue(remain_tree + 1);
                                                } else if (tree_id == 4) {
                                                    findViewById(R.id.Studytimer_tree04).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree04();
                                                    rewardRef.child("tree04").setValue(remain_tree + 1);
                                                } else if (tree_id == 5) {
                                                    findViewById(R.id.Studytimer_tree05).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree05();
                                                    rewardRef.child("tree05").setValue(remain_tree + 1);
                                                } else if (tree_id == 6) {
                                                    findViewById(R.id.Studytimer_tree06).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree06();
                                                    rewardRef.child("tree06").setValue(remain_tree + 1);
                                                } else if (tree_id == 7) {
                                                    findViewById(R.id.Studytimer_tree07).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree07();
                                                    rewardRef.child("tree07").setValue(remain_tree + 1);
                                                } else if (tree_id == 8) {
                                                    findViewById(R.id.Studytimer_tree08).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree08();
                                                    rewardRef.child("tree08").setValue(remain_tree + 1);
                                                } else if (tree_id == 9) {
                                                    findViewById(R.id.Studytimer_tree09).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree09();
                                                    rewardRef.child("tree09").setValue(remain_tree + 1);
                                                }else if (tree_id == 10) {
                                                    findViewById(R.id.Studytimer_tree10).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree10();
                                                    rewardRef.child("tree10").setValue(remain_tree + 1);
                                                }else if (tree_id == 11) {
                                                    findViewById(R.id.Studytimer_tree11).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree11();
                                                    rewardRef.child("tree11").setValue(remain_tree + 1);
                                                }else if (tree_id == 12) {
                                                    findViewById(R.id.Studytimer_tree12).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree12();
                                                    rewardRef.child("tree12").setValue(remain_tree + 1);
                                                }else if (tree_id == 13) {
                                                    findViewById(R.id.Studytimer_tree13).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree13();
                                                    rewardRef.child("tree13").setValue(remain_tree + 1);
                                                }else if (tree_id == 14) {
                                                    findViewById(R.id.Studytimer_tree14).setVisibility(View.VISIBLE);
                                                    int remain_tree = inventory.getTree14();
                                                    rewardRef.child("tree14").setValue(remain_tree + 1);
                                                }else if (tree_id == 15) {
                                                    findViewById(R.id.Studytimer_tree15).setVisibility(View.VISIBLE);
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

                            //???????????? ??????
                            timer.schedule(timerTask, 0, 1000); //Timer ??????

                            findViewById(R.id.StudytimerCancel_BUTTON).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //?????? ??????
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();

                                    changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_ALL);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        // ???????????????

                        // ???????????? ????????? if ????????? ?????? ??????
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void changeInterruptionFiler(int interruptionFilter){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ // If api level minimum 23
            /*
                boolean isNotificationPolicyAccessGranted ()
                    Checks the ability to read/modify notification policy for the calling package.
                    Returns true if the calling package can read/modify notification policy.
                    Request policy access by sending the user to the activity that matches the
                    system intent action ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS.

                    Use ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED to listen for
                    user grant or denial of this access.

                Returns
                    boolean

            */
            // If notification policy access granted for this package
            if(mNotificationManager.isNotificationPolicyAccessGranted()){
                /*
                    void setInterruptionFilter (int interruptionFilter)
                        Sets the current notification interruption filter.

                        The interruption filter defines which notifications are allowed to interrupt
                        the user (e.g. via sound & vibration) and is applied globally.

                        Only available if policy access is granted to this package.

                    Parameters
                        interruptionFilter : int
                        Value is INTERRUPTION_FILTER_NONE, INTERRUPTION_FILTER_PRIORITY,
                        INTERRUPTION_FILTER_ALARMS, INTERRUPTION_FILTER_ALL
                        or INTERRUPTION_FILTER_UNKNOWN.
                */

                // Set the interruption filter
                mNotificationManager.setInterruptionFilter(interruptionFilter);
            }else {
                /*
                    String ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
                        Activity Action : Show Do Not Disturb access settings.
                        Users can grant and deny access to Do Not Disturb configuration from here.

                    Input : Nothing.
                    Output : Nothing.
                    Constant Value : "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"
                */
                // If notification policy access not granted for this package
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
    }
}