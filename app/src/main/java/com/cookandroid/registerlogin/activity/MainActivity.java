package com.cookandroid.registerlogin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.cookandroid.registerlogin.*;
import com.cookandroid.registerlogin.fragment.AdminMainFragment;
import com.cookandroid.registerlogin.fragment.ClientMainFragment;
import com.cookandroid.registerlogin.fragment.UserInfoFragment;
import com.cookandroid.registerlogin.fragment.WebFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView et_test;
    private Button btn_logout,btn_info;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private AdminMainFragment AdminFrag;
    private ClientMainFragment MainFrag;
    private UserInfoFragment InfoFrag;
    private WebFragment frag4;
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

         if(login_mode == "logout") finish();

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
         AdminFrag = new AdminMainFragment();
         MainFrag = new ClientMainFragment();
         InfoFrag = new UserInfoFragment();
         frag4 = new WebFragment();
         setFrag(0);//첫프래그먼트 화면 지정

         Bundle bundle = new Bundle();
         bundle.putString("userID",userID);
         bundle.putString("userPass",userPass);
         bundle.putString("userName",userName);
         bundle.putString("userEmail",userEmail);
         bundle.putString("userNum",userNum);
         bundle.putString("userTicket", userTicket);
         bundle.putString("loginmode",login_mode);
         MainFrag.setArguments(bundle);
         InfoFrag.setArguments(bundle);
         AdminFrag.setArguments(bundle);

    }
    //프레그먼트 교체가 일어나는 실행문
    private void setFrag(int n){
         fm = getSupportFragmentManager();
         ft = fm.beginTransaction();
         switch(n){
             case 0:
                 switch(login_mode){
                     case "admin":
                         ft.replace(R.id.main_frame, AdminFrag);    break;
                     default:
                         ft.replace(R.id.main_frame, MainFrag);    break;
                 }
                 ft.commit();
                 break;
             case 1:
                 ft.replace(R.id.main_frame, InfoFrag);
                 ft.commit();
                 break;
             case 2:
                 ft.replace(R.id.main_frame,frag4);
                 ft.commit();
                 break;
             case 3:
                 finish();
                 break;
         }
    }
}
