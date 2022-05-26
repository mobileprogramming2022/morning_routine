package com.gachon.morningroutin_layout;

import static java.util.Calendar.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class GraphActivity extends AppCompatActivity {
    // get motivational quote textview
    TextView motivationalText;
    // create material calendar
    MaterialCalendarView calendarView;
    // get instances for firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    // get current date information
    long currentDate = System.currentTimeMillis();
    Date mDate = new Date(currentDate);
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM");
    String stringDate = date.format(mDate);
    int year = Integer.parseInt(stringDate.substring(0, stringDate.indexOf("-")));
    int month = Integer.parseInt(stringDate.substring(stringDate.indexOf("-") + 1));

    // random integer generator for printing motivational quotes
    // list of motivational quotes
    Random rnd = new Random();
    String quotes[] = {
            "간단함이 훌륭함의 열쇠다\n-이소룡-",
            "말만 하고 행동하지 않는 사람은 잡초로 가득 찬 정원과 같다\n-하우얼-",
            "바람이 불지 않으면 노를 저어라\n-윈스턴 처칠-",
            "낭비한 시간에 대한 후회는 더 큰 시간 낭비이다\n-메이슨 쿨리-",
            "산을 움직이려 하는 이는 작은 돌을 들어내는 일로 시작한다\n-공자-",
            "다리를 움직이지 않고는 작은 도랑도 건널 수 없다\n-알랭-",
            "휴식은 게으름도, 멈춤도 아니다\n-헨리 포드-",
            "열정을 잃지 않고 실패에서 실패로 걸어가는 것이 성공이다\n-윈스턴 처칠-",
            "나는 내가 더 노력할수록 운이 더 좋아진다는 걸 발견했다\n-토마스 제퍼슨-",
            "미래는 현재 우리가 무엇을 하는가에 달려 있다\n-마하트마 간디-",
            "미래를 예측하는 최선의 방법은 미래를 창조하는 것이다\n-앨런 케이-",
            "어제로 돌아갈 수 없다, 왜냐하면 나는 어제와는 다른 사람이 되었기 때문이다\n-루이스 캐럴-",
            "기운과 끈기는 모든 것을 이겨낸다\n-벤자민 프랭클린-",
            "꿈은 곧 목표가 되고, 목표를 나누면 계획이 되며, 계획을 실행할때 꿈은 실현되는 것이다\n-그렉.S 리드-",
            "자기 신뢰는 성공의 첫번째 비결이다\n-랄프 왈도 에머슨-",
            "인생이란 자신을 찾는 것이 아니라 자신을 만드는 것이다\n-롤리 다스칼-",
            "성공은 매일 반복한 작은 노력들의 합이다\n-로버트 콜리어-",
            "동기 부여가 당신을 시작하게 한다. 습관이 당신을 계속 움직이게 한다\n-짐 륜-",
            "과정에서 재미를 느끼지 못하는데 성공하는 일은 거의 없다\n-데일 카네기-",
            "만족스럽게 잠자리에 들려면 매일 아침 투지를 가지고 일어나야 한다\n-조지 로리머-",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        // get textview
        motivationalText = findViewById(R.id.motivationalQuotes);

        // date arraylist
        ArrayList<CalendarDay> failDates = new ArrayList<>();
        ArrayList<CalendarDay> successDates = new ArrayList<>();


        Log.d("year", Integer.toString(year));
        Log.d("month", Integer.toString(month));

        // instantiate calendar view and set
        // change locale, set calendar unmovable
        // user cannot manipulate calendar
        calendarView = findViewById(R.id.calendar);
        calendarView.state()
                .edit()
                .commit();
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.month_kor)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.day_kor)));
        calendarView.setTopbarVisible(false);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        calendarView.setPagingEnabled(false);

        // get stats data from DB
        DatabaseReference statsRef = firebaseDatabase.getReference();
        statsRef.child("stats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // array for stats from database
                int statsArray[] = new int[31];
                getStats stats = snapshot.getValue(getStats.class);
                statsArray = stats.getStatsArray();

                for(int i = 0; i < 31; i++){
                    // if user failed to do routine
                    if(statsArray[i] == 0){
                        int failed_day = i + 1;
                        failDates.add(CalendarDay.from(year, month - 1, failed_day));
                    }
                    // if user succeed routine
                    else if(statsArray[i] == 1){
                        int success_day = i + 1;
                        successDates.add(CalendarDay.from(year, month - 1, success_day));
                    }
                    calendarView.addDecorator(new FailDecorator(failDates));
                    calendarView.addDecorator(new SuccessDecorator(successDates));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // print motivational quotes randomly on quotes textview
        int randQuote = rnd.nextInt(quotes.length);
        motivationalText.setText(quotes[randQuote]);


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

    // decorate date tile of calendar
    public class FailDecorator implements DayViewDecorator{
        private final Drawable fail = getDrawable(R.drawable.fail);
        private CalendarDay mDay;
        private HashSet<CalendarDay> dates;

        public FailDecorator(Collection<CalendarDay> date){
            dates = new HashSet<>(date);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(fail);
        }
    }

    public class SuccessDecorator implements DayViewDecorator{
        private final Drawable success = getDrawable(R.drawable.success);
        private CalendarDay mDay;
        private HashSet<CalendarDay> dates;

        public SuccessDecorator(Collection<CalendarDay> date){
            dates = new HashSet<>(date);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(success);
        }
    }
}