package com.cookandroid.registerlogin.fragment;

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

import com.cookandroid.registerlogin.R;
import com.cookandroid.registerlogin.activity.FindIdActivity;
import com.cookandroid.registerlogin.activity.MainActivity;
import com.cookandroid.registerlogin.activity.RegisterActivity;
import com.cookandroid.registerlogin.request.LoginRequest;
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
                btn_register.setVisibility(View.INVISIBLE); // ???????????? ?????? X
                btn_register.setEnabled(false);
                btn_findid.setVisibility(View.INVISIBLE); // ????????? ?????? X
                btn_findid.setEnabled(false);
                break;
            case "null":
                loginlogo.setImageResource(R.mipmap.loginlogo);
                arrow.setVisibility(View.INVISIBLE);
                admin_text.setVisibility(View.INVISIBLE);
                break;
        }
        //???????????? ?????? ????????? ??????
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_id.setText("");  et_pass.setText("");
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("mode", login_mode);
                startActivity(intent);
            }
        });

        //????????? ?????? ??????
        btn_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindIdActivity.class);
                startActivity(intent);
            }
        });

        // ????????? ??????
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edittext??? ?????? ???????????? ?????? ?????? Get?????????
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

                                if(login_mode.equals("admin") && !userTicket.equals("admin")){ // ????????? ?????? ????????? ?????????????????? ????????? ????????? ??????.
                                        Toast.makeText(getContext(),"????????? ????????? ????????????.",Toast.LENGTH_SHORT).show();
                                        return;
                                }

                                    Toast.makeText(getContext(), "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
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

                            }else{ //????????? ??????
                                Toast.makeText(getContext(),"???????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
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

        //???????????? ?????? ????????? ??????
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginFragment.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //????????? ?????? ??????
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
                // edittext??? ?????? ???????????? ?????? ?????? Get?????????
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

                              Toast.makeText(getApplicationContext(),"???????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(UserLoginFragment.this, MainActivity.class);
                              intent.putExtra("userID",userID);
                              intent.putExtra("userPass",userPass);
                              intent.putExtra("userName",userName);
                              intent.putExtra("userNum",userNum);
                              intent.putExtra("userEmail",userEmail);
                              intent.putExtra("userTicket",userTicket);

                              et_pass.setText("");
                              startActivity(intent);
                          }else{ //????????? ??????
                              Toast.makeText(getApplicationContext(),"???????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
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