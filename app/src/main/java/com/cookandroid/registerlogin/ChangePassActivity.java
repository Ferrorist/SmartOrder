package com.cookandroid.registerlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassActivity extends AppCompatActivity {

    private TextView textView8;
    private EditText et_ctpass,et_newpass1,et_newpass2;
    private Button btn_change,btn_gohome;
    private boolean validate = false;
    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        textView8 = findViewById(R.id.textView8);
        et_ctpass = findViewById(R.id.et_ctpass);
        et_newpass1 = findViewById(R.id.et_newpass1);
        et_newpass2 = findViewById(R.id.et_newpass2);

        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                String userNum = intent.getStringExtra("userNum");
                String userEmail = intent.getStringExtra("userEmail");
                String userTicket = intent.getStringExtra("userTicket");
                String Loginmode = intent.getStringExtra("loginmode");
                String ctpass = et_ctpass.getText().toString();
                String newpass1 = et_newpass1.getText().toString();
                String newpass2 = et_newpass2.getText().toString();
                if (ctpass.equals("")||newpass1.equals("")||newpass2.equals("")) {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (newpass1.length()<5 || newpass2.length()<5){
                    Toast.makeText(getApplicationContext(),"새 비밀번호 길이가 짧습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(newpass1.equals(newpass2)) {
                    Response.Listener<String> responseListener =new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jasonObject = new JSONObject(response);
                                boolean success = jasonObject.getBoolean("success");
                                Intent intent = getIntent();
                                String userID = intent.getStringExtra("userID");
                                String userName = intent.getStringExtra("userName");
                                String userNum = intent.getStringExtra("userNum");
                                String userEmail = intent.getStringExtra("userEmail");
                                String userTicket = intent.getStringExtra("userTicket");
                                String Loginmode = intent.getStringExtra("loginmode");
                                String ctpass = et_ctpass.getText().toString();
                                String newpass1 = et_newpass1.getText().toString();
                                String newpass2 = et_newpass2.getText().toString();
                                if(success){
                                    String userPass = jasonObject.getString("userPassword");
                                    if(ctpass.equals(userPass))
                                    {
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");
                                                    if (success) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassActivity.this);
                                                        validate = true; //검증 완료

                                                        dialog = builder.setMessage("변경 성공!").setPositiveButton("확인", null).create();
                                                        dialog.show();

                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                dialog.dismiss();
                                                                finish();
                                                            }
                                                        }, 1500);
                                                    } else {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassActivity.this);
                                                        dialog = builder.setMessage("변경 실패..").setNegativeButton("확인", null).create();
                                                        dialog.show();
                                                        dialog.dismiss();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        ChangePassRequest changeuserinfo = new ChangePassRequest(userID, newpass1, userName, Integer.parseInt(userNum), responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(ChangePassActivity.this);
                                        queue.add(changeuserinfo);

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"기존 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }else{ //실패한 경우
                                    Toast.makeText(getApplicationContext(),"일치하는 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    };
                    FindIdRequest findIdrequest = new FindIdRequest(userName, userEmail,Integer.parseInt(userNum), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangePassActivity.this);
                    queue.add(findIdrequest);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"새 비밀번호가 다릅니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_gohome = findViewById(R.id.btn_gohome);
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userNum = intent.getStringExtra("userNum");
        String userTicket = intent.getStringExtra("userTicket");
        String Loginmode = intent.getStringExtra("loginmode");

    }
}