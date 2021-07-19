package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Userinfo extends AppCompatActivity {

    private TextView tv_id,tv_name,tv_Num,tv_Email,tv_title;
    private Button btn_return,btn_emailch,btn_passch,btn_deleteID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        tv_id = findViewById(R.id. tv_id);
        tv_name = findViewById(R.id.tv_name);
        tv_Num = findViewById(R.id.tv_Num);
        tv_Email = findViewById(R.id.tv_Email);
        tv_title = findViewById(R.id.tv_title);

        btn_return = findViewById(R.id.btn_change);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(Userinfo.this, MainActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_emailch = findViewById(R.id.btn_emailch);
        btn_emailch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(Userinfo.this, ChangeEmailActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_passch = findViewById(R.id.btn_passch);
        btn_passch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(Userinfo.this, ChangePassActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });

        btn_deleteID = findViewById(R.id.btn_deleteID);
        btn_deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(Userinfo.this, DeleteIdActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });






        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userNum = intent.getStringExtra("userNum");
        String userTicket = intent.getStringExtra("userTicket");

        tv_id.setText(userID);
        tv_name.setText(userName);
        tv_Num.setText(userNum);
        tv_Email.setText(userEmail);




    }

}