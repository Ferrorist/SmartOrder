package com.cookandroid.registerlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView et_test;
    private Button btn_logout,btn_info;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private adminFrag1 adminFrag;
    private Frag1 frag1;
    private Frag1_1 frag1_1;
    private Frag2 frag2;
    private Frag3 frag3;
    private action_web_frag frag4;
    String userID, userPass, userName, userEmail, userNum, userTicket, login_mode;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         Intent intent = getIntent();
         userID = intent.getStringExtra("userID");
         userPass = intent.getStringExtra("userPass");
         userName = intent.getStringExtra("userName");
         userEmail = intent.getStringExtra("userEmail");
         userNum = intent.getStringExtra("userNum");
         userTicket = intent.getStringExtra("userTicket");
         login_mode = intent.getStringExtra("loginMode");

         bottomNavigationView =findViewById(R.id.bottomNavi);
         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 switch(menuItem.getItemId()){
                     case R.id.action_home:
                          setFrag(0);
                          break;
                     case R.id.action_barcode:
                         setFrag(1);
                         break;
                     case R.id.action_restaurant:
                         setFrag(2);
                         break;
                 }
                 return true;
             }
         });
         adminFrag = new adminFrag1();
         frag1 = new Frag1();
         frag2 = new Frag2();
         frag3 = new Frag3();
         frag4 = new action_web_frag();
         setFrag(0);//첫프래그먼트 화면 지정

         Bundle bundle = new Bundle();
         bundle.putString("userID",userID);
         bundle.putString("userPass",userPass);
         bundle.putString("userName",userName);
         bundle.putString("userEmail",userEmail);
         bundle.putString("userNum",userNum);
         bundle.putString("userTicket", userTicket);
         frag1.setArguments(bundle);
         frag2.setArguments(bundle);
         frag3.setArguments(bundle);
         adminFrag.setArguments(bundle);

    }
    //프레그먼트 교체가 일어나는 실행문
    private void setFrag(int n){
         fm = getSupportFragmentManager();
         ft = fm.beginTransaction();
         switch(n){
             case 0:
                 switch(login_mode){
                     case "admin":
                         ft.replace(R.id.main_frame, adminFrag);    break;
                     case "null":
                         ft.replace(R.id.main_frame, frag1);    break;
                 }
                 ft.commit();
                 break;
             case 1:
                 ft.replace(R.id.main_frame,frag3);
                 ft.commit();
                 break;
             case 2:
                 ft.replace(R.id.main_frame,frag4);
                 ft.commit();
                 break;
         }
    }
}
