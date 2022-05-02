package com.gachon.morningroutin_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton addAlarm = findViewById(R.id.imageView);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmSetActivity.class);
                startActivity(intent);
            }
        });


        //image button
        ImageButton graph = findViewById(R.id.Graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
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
        TextView txtGraph = findViewById(R.id.textGraph);
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
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
}