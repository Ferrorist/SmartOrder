package com.cookandroid.registerlogin.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class notuse_AddMenuRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    static private String URL = "http://thee153.dothome.co.kr/AddMenu.php/";
    private Map<String, String> map;

    public notuse_AddMenuRequest(String menu, String price, String date, String note, String num, String rest_num, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("menu",menu);
        map.put("price",price);
        map.put("date",date);
        map.put("note",note);
        map.put("num",num);
        map.put("rest_num",rest_num);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}