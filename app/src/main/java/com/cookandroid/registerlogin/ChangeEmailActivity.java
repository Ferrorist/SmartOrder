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

public class ChangeEmailActivity extends AppCompatActivity {

    private TextView tv_current,tv_new,tv_email,textView10;
    private EditText et_new;
    private Button btn_verify,btn_change,btn_gohome;
    private boolean validate2 = false;
    private AlertDialog dialog2;
    private boolean validate = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemail);

        tv_email = findViewById(R.id.tv_email);
        et_new = findViewById(R.id.et_new);
        textView10 = findViewById(R.id.textView10);

        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userName = intent.getStringExtra("userName");
                String userNum = intent.getStringExtra("userNum");
                String userEmail = et_new.getText().toString();

                if(validate2 != true)
                {
                    Toast.makeText(getApplicationContext(),"이메일 중복체크를 해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmailActivity.this);

                                    validate = true; //검증 완료

                                    Intent intent = getIntent();
                                    String userID = intent.getStringExtra("userID");
                                    String userPass = intent.getStringExtra("userPass");
                                    String userName = intent.getStringExtra("userName");
                                    String userEmail = et_new.getText().toString();
                                    String userNum = intent.getStringExtra("userNum");
                                    String userTicket = intent.getStringExtra("userTicket");

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = getIntent();
                                            String userID = intent.getStringExtra("userID");
                                            String userPass = intent.getStringExtra("userPass");
                                            String userName = intent.getStringExtra("userName");
                                            String userEmail = et_new.getText().toString();
                                            String userNum = intent.getStringExtra("userNum");
                                            String userTicket = intent.getStringExtra("userTicket");

                                            Intent intent1 = new Intent(ChangeEmailActivity.this, MainActivity.class);
                                            intent1.putExtra("userID",userID);
                                            intent1.putExtra("userPass",userPass);
                                            intent1.putExtra("userName",userName);
                                            intent1.putExtra("userNum",userNum);
                                            intent1.putExtra("userEmail",userEmail);
                                            intent1.putExtra("userTicket", userTicket);
                                            startActivity(intent1);
                                        }
                                    }, 1500);

                                    dialog = builder.setMessage("변경 성공!").setPositiveButton("확인", null).create();
                                    dialog.show();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmailActivity.this);
                                    dialog = builder.setMessage("변경 실패..").setNegativeButton("확인", null).create();
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ChangeEmailRequest changeuserinfo = new ChangeEmailRequest(userID, userEmail, userName, Integer.parseInt(userNum), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangeEmailActivity.this);
                    queue.add(changeuserinfo);
                }
            }
        });

        btn_gohome = findViewById(R.id.btn_gohome);
        btn_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPass");
                String userName = intent.getStringExtra("userName");
                String userEmail = intent.getStringExtra("userEmail");
                String userNum = intent.getStringExtra("userNum");
                String userTicket = intent.getStringExtra("userTicket");

                Intent intent1 = new Intent(ChangeEmailActivity.this, MainActivity.class);
                intent1.putExtra("userID",userID);
                intent1.putExtra("userPass",userPass);
                intent1.putExtra("userName",userName);
                intent1.putExtra("userNum",userNum);
                intent1.putExtra("userEmail",userEmail);
                intent1.putExtra("userTicket", userTicket);
                startActivity(intent1);
            }
        });


        btn_verify = findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String UserEmail = et_new.getText().toString();
                if (validate2) {
                    return; //검증 완료
                }
                if (UserEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmailActivity.this);
                    dialog2 = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog2.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmailActivity.this);
                                dialog2 = builder.setMessage("사용할 수 있는 이메일입니다.").setPositiveButton("확인", null).create();
                                dialog2.show();
                                et_new.setEnabled(false); //아이디값 고정
                                validate2 = true; //검증 완료

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmailActivity.this);
                                dialog2 = builder.setMessage("이미 존재하는 이메일입니다.").setNegativeButton("확인", null).create();
                                dialog2.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequestEmail validateRequest2 = new ValidateRequestEmail(UserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangeEmailActivity.this);
                queue.add(validateRequest2);
            }
        });

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userNum = intent.getStringExtra("userNum");
        String userTicket = intent.getStringExtra("userTicket");

        tv_email.setText(userEmail);
    }

}