package com.cookandroid.registerlogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
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

public class DeleteIdActivity extends AppCompatActivity {

    private TextView textView5;
    private EditText et_pass1,et_pass2;
    private Button btn_delete,btn_gohome;
    private boolean validate = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteid);

        textView5 = findViewById(R.id.textView5);
        et_pass1 = findViewById(R.id.et_pass1);
        et_pass2 = findViewById(R.id.et_pass2);

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                String userPass = intent.getStringExtra("userPassword");
                String userName = intent.getStringExtra("userName");
                String userNum = intent.getStringExtra("userNum");
                String userEmail = intent.getStringExtra("userEmail");
                String userTicket = intent.getStringExtra("userTicket");
                String pass1 = et_pass1.getText().toString();
                String pass2 = et_pass2.getText().toString();
                if (pass1.equals("")||pass2.equals("")) {
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pass1.equals(pass2)) {
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
                                String pass1 = et_pass1.getText().toString();
                                String pass2 = et_pass2.getText().toString();

                                if(success){
                                    String userPass = jasonObject.getString("userPassword");
                                    if(pass1.equals(userPass))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteIdActivity.this);
                                        builder.setTitle("회원탈퇴 메세지");
                                        builder.setMessage("회원탈퇴를 하시겠습니까?");

                                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override


                                            public void onClick(DialogInterface dialog, int which) {
                                                //"YES" Button Click
                                                Intent intent = getIntent();
                                                String userID = intent.getStringExtra("userID");
                                                String userName = intent.getStringExtra("userName");

                                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if (success) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteIdActivity.this);

                                                                validate = true;

                                                                Intent intent = getIntent();
                                                                String userID = intent.getStringExtra("userID");
                                                                String userPass = intent.getStringExtra("userPass");
                                                                String userName = intent.getStringExtra("userName");

                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = getIntent();
                                                                        String userID = intent.getStringExtra("userID");
                                                                        String userPass = intent.getStringExtra("userPass");
                                                                        String userName = intent.getStringExtra("userName");

                                                                        Intent intent1 = new Intent(DeleteIdActivity.this, LoginActivity.class);
                                                                        startActivity(intent1);
                                                                    }
                                                                }, 1500);

                                                                Toast.makeText(getApplicationContext(),"회원탈퇴에 성공하였습니다.",Toast.LENGTH_SHORT).show();


                                                            } else {
                                                                Toast.makeText(getApplicationContext(),"회원탈퇴에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                DeleteIdRequest deleteid = new DeleteIdRequest(userID, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(DeleteIdActivity.this);
                                                queue.add(deleteid);


                                            }
                                        });

                                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {       //취소 버튼을 생성하고 클릭시 동작을 구현합니다.
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = getIntent();
                                                String userID = intent.getStringExtra("userID");
                                                String userPass = intent.getStringExtra("userPass");
                                                String userName = intent.getStringExtra("userName");
                                                String userEmail = intent.getStringExtra("userEmail");
                                                String userNum = intent.getStringExtra("userNum");
                                                String userTicket = intent.getStringExtra("userTicket");

                                                Intent intent1 = new Intent(DeleteIdActivity.this, DeleteIdActivity.class);
                                                intent1.putExtra("userID",userID);
                                                intent1.putExtra("userPass",userPass);
                                                intent1.putExtra("userName",userName);
                                                intent1.putExtra("userNum",userNum);
                                                intent1.putExtra("userEmail",userEmail);
                                                intent1.putExtra("userTicket", userTicket);
                                                startActivity(intent1);
                                            }
                                        });
                                        AlertDialog alert = builder.create(); //빌더를 이용하여 AlertDialog객체를 생성합니다.
                                        alert.show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }else{ //실패한 경우
                                    Toast.makeText(getApplicationContext(),"일치하는 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                            }


                        }
                    };
                    FindIdRequest findIdrequest = new FindIdRequest(userName, userEmail,Integer.parseInt(userNum), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DeleteIdActivity.this);
                    queue.add(findIdrequest);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"입력한 두 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
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
    }
}