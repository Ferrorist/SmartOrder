package com.cookandroid.registerlogin.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TicketRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://thee153.dothome.co.kr/Test.php";
    private Map<String, String> map;


    public TicketRequest( Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}