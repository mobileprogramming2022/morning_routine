package com.gachon.morningroutin_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;

public class TodoActivity extends AppCompatActivity {

    private final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        CheckBox chk1 = findViewById(R.id.todoActivityChk1);
        CheckBox chk2 = findViewById(R.id.todoActivityChk2);
        CheckBox chk3 = findViewById(R.id.todoActivityChk3);

        TextView big_picture = findViewById(R.id.todoShowBigPicture);

        Button fail = findViewById(R.id.todoFailureButton);
        Button successChk = findViewById(R.id.todoSuccessButton);

        DatabaseReference planRef = database.child("daily").child("12345");
        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getTodayPlan plan = snapshot.getValue(getTodayPlan.class);

                if (plan != null) {

                    CheckBox chk1 = findViewById(R.id.todoActivityChk1);
                    CheckBox chk2 = findViewById(R.id.todoActivityChk2);
                    CheckBox chk3 = findViewById(R.id.todoActivityChk3);

                    // 운동/공부 인지 혹은 list 인지 확인
                    if (plan.getType().compareTo("CHECK_LIST") == 0) {
                        // 체크리스트라면
                        // 우선 몇갠지 가져온다.
                        big_picture.setText("할일 목록 완성하기");
                        chk2.setVisibility(View.INVISIBLE);
                        chk3.setVisibility(View.INVISIBLE);
                        String input = plan.getInput();
                        StringTokenizer st = new StringTokenizer(input, "#");
                        int number_of_list = Integer.parseInt(st.nextToken());

                        if (st.hasMoreTokens()) {
                            chk1.setText(st.nextToken());
                        } if (st.hasMoreTokens()) {
                            chk2.setText(st.nextToken());
                            chk2.setVisibility(View.VISIBLE);
                        } if (st.hasMoreTokens()) {
                            chk3.setText(st.nextToken());
                            chk3.setVisibility(View.VISIBLE);
                        }



                    } else {
                        // 공부나 운동이라면.. 간단해진다.
                        chk2.setVisibility(View.INVISIBLE);
                        chk3.setVisibility(View.INVISIBLE);
                        if (plan.getType().compareTo("EXERCISE") == 0) {
                            big_picture.setText("열심히 운동하세요!");
                            chk1.setText("오늘의 운동");
                        } else {
                            big_picture.setText("열심히 공부하세요!");
                            chk1.setText("오늘의 공부");
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TodoActivity.this, "취소합니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        successChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk1.getVisibility() == View.INVISIBLE || chk1.isChecked()) {
                    if (chk2.getVisibility() == View.INVISIBLE || chk2.isChecked()) {
                        if (chk3.getVisibility() == View.INVISIBLE || chk3.isChecked()) {
                            Toast.makeText(TodoActivity.this, "모든 작업 완료!", Toast.LENGTH_SHORT).show();

                            // 선물을 추가하는 코드
                            // tree 0-9 까지 있으므로, 0-9 랜덤 난수 발생시킨다.
                            double num = Math.random();
                            int tree_id = (int)(num * 10);

                            // DB 에 업데이트한다.
                            DatabaseReference rewardRef = database.child("inventory");
                            rewardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    getInventory inventory = snapshot.getValue(getInventory.class);
                                    if (tree_id == 1) {
                                        findViewById(R.id.todo_tree01).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree01();
                                        rewardRef.child("tree01").setValue(remain_tree + 1);
                                    } else if (tree_id == 2) {
                                        findViewById(R.id.todo_tree02).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree02();
                                        rewardRef.child("tree02").setValue(remain_tree + 1);
                                    } else if (tree_id == 3) {
                                        findViewById(R.id.todo_tree03).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree03();
                                        rewardRef.child("tree03").setValue(remain_tree + 1);
                                    } else if (tree_id == 4) {
                                        findViewById(R.id.todo_tree04).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree04();
                                        rewardRef.child("tree04").setValue(remain_tree + 1);
                                    } else if (tree_id == 5) {
                                        findViewById(R.id.todo_tree05).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree05();
                                        rewardRef.child("tree05").setValue(remain_tree + 1);
                                    } else if (tree_id == 6) {
                                        findViewById(R.id.todo_tree06).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree06();
                                        rewardRef.child("tree06").setValue(remain_tree + 1);
                                    } else if (tree_id == 7) {
                                        findViewById(R.id.todo_tree07).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree07();
                                        rewardRef.child("tree07").setValue(remain_tree + 1);
                                    } else if (tree_id == 8) {
                                        findViewById(R.id.todo_tree08).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree08();
                                        rewardRef.child("tree08").setValue(remain_tree + 1);
                                    } else if (tree_id == 9) {
                                        findViewById(R.id.todo_tree09).setVisibility(View.VISIBLE);
                                        int remain_tree = inventory.getTree09();
                                        rewardRef.child("tree09").setValue(remain_tree + 1);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            fail.setVisibility(View.INVISIBLE);
                            successChk.setVisibility(View.INVISIBLE);

                            findViewById(R.id.fromTodoGotoMainButton).setVisibility(View.VISIBLE);
                            findViewById(R.id.fromTodoGotoMainButton).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(TodoActivity.this, "보상을 DB에 저장합니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toast.makeText(TodoActivity.this, "모든 작업을 완수하세요!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TodoActivity.this, "모든 작업을 완수하세요!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TodoActivity.this, "모든 작업을 완수하세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}