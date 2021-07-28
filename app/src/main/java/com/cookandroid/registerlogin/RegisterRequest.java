package com.cookandroid.registerlogin;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://thee153.dothome.co.kr/Register.php";
    private Map<String, String> map;


    public RegisterRequest(String userID, String userPassword, String userName,String userEmail, String userTicket, int userNum, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
        map.put("userName",userName);
        map.put("userEmail",userEmail);
        map.put("userNum",userNum + "");
        map.put("userTicket", userTicket);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

