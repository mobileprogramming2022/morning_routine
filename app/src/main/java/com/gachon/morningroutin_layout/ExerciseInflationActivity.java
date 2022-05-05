package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseInflationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_inflation);

        Button walkButton = findViewById(R.id.exerciseWalkButton);
        Button timeButton = findViewById(R.id.exerciseTimeButton);
        Button todoButton = findViewById(R.id.exerciseTodoButton);

        EditText userInput = findViewById(R.id.userTextInput);


        walkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setHint("목표 걸음 수 입력");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setHint("목표 시간 입력");
            }
        });

    }
}
