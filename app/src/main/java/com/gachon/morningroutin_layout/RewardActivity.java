package com.gachon.morningroutin_layout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardActivity  extends AppCompatActivity {
    private static final String TAG = "rewardTag";
    // get instances for firebase database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = firebaseDatabase.getReference();
    int tree01, tree02, tree03, tree04, tree05, tree06, tree07, tree08, tree09;
    TextView inv01, inv02, inv03, inv04, inv05, inv06, inv07, inv08, inv09;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        inv01 = (TextView)findViewById(R.id.inventory01);
        inv02 = (TextView)findViewById(R.id.inventory02);
        inv03 = (TextView)findViewById(R.id.inventory03);
        inv04 = (TextView)findViewById(R.id.inventory04);
        inv05 = (TextView)findViewById(R.id.inventory05);
        inv06 = (TextView)findViewById(R.id.inventory06);
        inv07 = (TextView)findViewById(R.id.inventory07);
        inv08 = (TextView)findViewById(R.id.inventory08);
        inv09 = (TextView)findViewById(R.id.inventory09);



        databaseRef.child("user").child("user_inventory").child("tree01").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tree01 = (int)snapshot.getValue(Integer.class);
                inv01.setText(tree01);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error connecting DB", Toast.LENGTH_SHORT).show();
            }
        });




        // bottom menu
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
        ImageButton option = findViewById(R.id.Options);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
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
        TextView txtGraph = findViewById(R.id.textGraph);
        txtGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                startActivityForResult(intent, 101);
                overridePendingTransition(0, 0);
            }
        });
    }
}