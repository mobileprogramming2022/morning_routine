package com.gachon.morningroutin_layout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OptionActivity  extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Dialog dialog01;
    Dialog dialog02;
    Dialog dialog03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        double current_version = 1.0;

        TextView need_update = findViewById(R.id.option_update_info_needUpdate);
        TextView already_new = findViewById(R.id.option_update_info_alreadyNew);

        //버전 정보를 받아와서 비교해 보기
        //업데이트가 충분하다면

        already_new.setVisibility(View.VISIBLE);
        need_update.setVisibility(View.INVISIBLE);

        DatabaseReference versionRef = database.child("version");
        versionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double updated_version = snapshot.getValue(Double.class);
                if (current_version == updated_version) {
                    already_new.setVisibility(View.VISIBLE);
                    need_update.setVisibility(View.INVISIBLE);
                } else {
                    already_new.setVisibility(View.INVISIBLE);
                    need_update.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button email_btn = findViewById(R.id.option_email_btn);
        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");

                intent.putExtra(Intent.EXTRA_EMAIL, "mpmorningroutine@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "어플 문의");
                intent.putExtra(Intent.EXTRA_TEXT, "문의 내용");
                startActivity(intent);
            }
        });

        dialog01 = new Dialog(OptionActivity.this);       // Dialog 초기화
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog01.setContentView(R.layout.activity_option_service_info);
        Button service_info_btn = findViewById(R.id.option_service_explain_btn);
        service_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog1();
            }
        });

        dialog02 = new Dialog(OptionActivity.this);       // Dialog 초기화
        dialog02.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog02.setContentView(R.layout.activity_copyright);
        Button legal_notice_btn = findViewById(R.id.option_legal_notice_btn);
        legal_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog2();
            }
        });

        dialog03 = new Dialog(OptionActivity.this);       // Dialog 초기화
        dialog03.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog03.setContentView(R.layout.activity_developers_info);
        Button developer_info_btn = findViewById(R.id.option_developer_info_btn);
        developer_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog3();
            }
        });

        //image button
        ImageButton main = findViewById(R.id.Plan);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
        ImageButton graph = findViewById(R.id.Graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
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
        TextView txtGraph = findViewById(R.id.textGraph);
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
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
    void showDialog1() {
        dialog01.show();
        Button noBtn = dialog01.findViewById(R.id.service_info_noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog01.dismiss(); // 다이얼로그 닫기
            }
        });
    }

    void showDialog2() {
        dialog02.show();
        Button noBtn = dialog02.findViewById(R.id.copyright_noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog02.dismiss(); // 다이얼로그 닫기
            }
        });
    }

    void showDialog3() {
        dialog03.show();
        Button noBtn = dialog03.findViewById(R.id.fromDevToOption);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog03.dismiss(); // 다이얼로그 닫기
            }
        });
    }
}