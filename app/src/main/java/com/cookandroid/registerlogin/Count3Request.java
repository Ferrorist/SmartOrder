package com.cookandroid.registerlogin;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Count3Request extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://thee153.dothome.co.kr/Count3.php";
    private Map<String, String> map;


    public Count3Request(String menu, String date, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("menu",menu);
        map.put("date",date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

