package com.cookandroid.registerlogin.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://thee153.dothome.co.kr/Buy.php";
    private Map<String, String> map;


    public MenuRequest(String userID, String menu, String price,String restaurant,String buydate,String usedate,String usecheck,String barcode, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("menu",menu);
        map.put("price",price);
        map.put("restaurant",restaurant);
        map.put("buydate",buydate);
        map.put("usedate",usedate);
        map.put("usecheck",usecheck);
        map.put("barcode",barcode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}