package com.gachon.morningroutin_layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GraphActivity extends AppCompatActivity {
    // create material calendar
    MaterialCalendarView calendarView;
    // get instances for firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    // date arraylist
    ArrayList<CalendarDay> failDates;
    ArrayList<CalendarDay> successDates;
    // get current year, month, day
    LocalDate currentDate = LocalDate.now();
    int year = currentDate.getYear();
    int month = currentDate.getMonthValue();
    int day = currentDate.getDayOfMonth();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        calendarView = findViewById(R.id.calendar);

        calendarView.state()
                .edit()
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        DatabaseReference statsRef = firebaseDatabase.getReference();
        statsRef.child("stats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int statsArray[] = new int[30];
                getStats stats = snapshot.getValue(getStats.class);
                statsArray = stats.getStatsArray();

                for(int i = 0; i < 30; i++){
                    // if user failed to do routine
                    if(statsArray[i] == 0){
                        CalendarDay day = CalendarDay.from(year, month, statsArray[i]);
                        failDates.add(day);
                    }
                    // if user succeed routine
                    else if(statsArray[i] == 1){
                        CalendarDay day = CalendarDay.from(year, month, statsArray[i]);
                        successDates.add(day);
                    }
                }

                for(int i = 0; i < failDates.size(); i++){
                    calendarView.addDecorator(FailDecorator());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // image button
        ImageButton main = findViewById(R.id.Plan);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        TextView txtMain = findViewById(R.id.textPlan);
        txtMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    public class FailDecorator implements DayViewDecorator{
        Drawable fail = getDrawable(R.drawable.fail);
        private ArrayList<CalendarDay> dates;

        public FailDecorator(ArrayList<CalendarDay> dates){
            this.dates = dates;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(fail);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }
    }

    public class SuccessDecorator implements DayViewDecorator{
        Drawable success = getDrawable(R.drawable.success);
        private ArrayList<CalendarDay> dates;

        public SuccessDecorator(ArrayList<CalendarDay> dates){
            this.dates = dates;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(success);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }
    }
}