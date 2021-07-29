package com.cookandroid.registerlogin;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register,btn_findid;
    private static String mode;
    private String login_mode;
    private ImageView loginlogo, arrow;
    private TextView admin_text;
    public LoginFragment() {}

    public static LoginFragment newInstance(String param1) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(mode, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            login_mode = getArguments().getString(mode);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginlogo = view.findViewById(R.id.login_logo);
        arrow = view.findViewById(R.id.arrow_client);
        admin_text = view.findViewById(R.id.admin_textview);
        et_id = view.findViewById(R.id.et_id);
        et_pass = view.findViewById(R.id.et_pass1);
        btn_login = view.findViewById(R.id.btn_login);
        btn_register = view.findViewById(R.id.btn_register);
        btn_findid = view.findViewById(R.id.btn_findid);

        switch(login_mode){
            case "admin":
                loginlogo.setImageResource(R.mipmap.loginlogo_admin);
                arrow.setVisibility(View.VISIBLE);
                admin_text.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.INVISIBLE);
                btn_register.setEnabled(false);
                btn_findid.setVisibility(View.INVISIBLE);
                btn_findid.setEnabled(false);
                break;
            case "null":
                loginlogo.setImageResource(R.mipmap.loginlogo);
                arrow.setVisibility(View.INVISIBLE);
                admin_text.setVisibility(View.INVISIBLE);
                break;
        }
        //회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_id.setText("");  et_pass.setText("");
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("mode", login_mode);
                startActivity(intent);
            }
        });

        //아이디 찾기 버튼
        btn_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindIdActivity.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener =new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jasonObject = new JSONObject(response);
                            boolean success = jasonObject.getBoolean("success");
                            if(success){
                                String userID = jasonObject.getString("userID");
                                String userPass = jasonObject.getString("userPassword");
                                String userName = jasonObject.getString("userName");
                                String userNum = jasonObject.getString("userNum");
                                String userEmail = jasonObject.getString("userEmail");
                                String userTicket = jasonObject.getString("userTicket");

                                if(login_mode.equals("admin") && !userTicket.equals("admin")){ // 관리자 모드 로그인 시도하였으나 관리자 계정이 아님.
                                        Toast.makeText(getContext(),"관리자 계정이 아닙니다.",Toast.LENGTH_SHORT).show();
                                        return;
                                }

                                    Toast.makeText(getContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPass", userPass);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userNum", userNum);
                                    intent.putExtra("userEmail", userEmail);
                                    intent.putExtra("userTicket", userTicket);
                                    intent.putExtra("loginMode", login_mode);
                                    et_pass.setText("");
                                    startActivity(intent);

                            }else{ //실패한 경우
                                Toast.makeText(getContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);
            }
        });
        return view;
    }
}

/*
package com.cookandroid.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginFragment extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login,btn_register,btn_findid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass1);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_findid = findViewById(R.id.btn_findid);

        //회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginFragment.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //아이디 찾기 버튼
        btn_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginFragment.this, FindIdActivity.class);
                startActivity(intent);
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext에 현재 입력되어 있는 값을 Get해준다
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

              Response.Listener<String> responseListener =new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      try {
                          JSONObject jasonObject = new JSONObject(response);
                          boolean success = jasonObject.getBoolean("success");
                          if(success){
                              String userID = jasonObject.getString("userID");
                              String userPass = jasonObject.getString("userPassword");
                              String userName = jasonObject.getString("userName");
                              String userNum = jasonObject.getString("userNum");
                              String userEmail = jasonObject.getString("userEmail");
                              String userTicket = jasonObject.getString("userTicket");

                              Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(UserLoginFragment.this, MainActivity.class);
                              intent.putExtra("userID",userID);
                              intent.putExtra("userPass",userPass);
                              intent.putExtra("userName",userName);
                              intent.putExtra("userNum",userNum);
                              intent.putExtra("userEmail",userEmail);
                              intent.putExtra("userTicket",userTicket);

                              et_pass.setText("");
                              startActivity(intent);
                          }else{ //실패한 경우
                              Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                              return;
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }


                  }
              };
              LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
              RequestQueue queue = Volley.newRequestQueue(UserLoginFragment.this);
              queue.add(loginRequest);
            }
        });
    }
}
*/