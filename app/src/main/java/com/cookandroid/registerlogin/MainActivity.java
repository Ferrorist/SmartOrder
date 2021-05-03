package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView et_test;
    private Button btn_logout,btn_info;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         btn_logout = findViewById(R.id.btn_logout);
         btn_logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                 startActivity(intent);
             }
         });

         btn_info = findViewById(R.id.btn_info);
         btn_info.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = getIntent();
                 String userID = intent.getStringExtra("userID");
                 String userPass = intent.getStringExtra("userPass");
                 String userName = intent.getStringExtra("userName");
                 String userEmail = intent.getStringExtra("userEmail");
                 String userNum = intent.getStringExtra("userNum");

                 Intent intent1 = new Intent(MainActivity.this, Userinfo.class);
                 intent1.putExtra("userID",userID);
                 intent1.putExtra("userPass",userPass);
                 intent1.putExtra("userName",userName);
                 intent1.putExtra("userNum",userNum);
                 intent1.putExtra("userEmail",userEmail);
                 startActivity(intent1);
             }
         });

         Intent intent = getIntent();
         String userID = intent.getStringExtra("userID");
         String userPass = intent.getStringExtra("userPass");
         String userName = intent.getStringExtra("userName");
         String userEmail = intent.getStringExtra("userEmail");
         String userNum = intent.getStringExtra("userNum");











    }

}
