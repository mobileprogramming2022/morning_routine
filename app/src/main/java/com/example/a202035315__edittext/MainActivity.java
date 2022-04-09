package com.example.a202035315__edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView labelUserName = findViewById(R.id.textView);
        EditText txtUsername = findViewById(R.id.txtUserName);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userName = txtUsername.getText().toString();
                if(userName.compareTo("bugyeong Kim") == 0){
                    labelUserName.setText("OK. Please wait...");
                    Toast.makeText(MainActivity.this, "Hi!" + userName, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, userName + " is not a valid user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}