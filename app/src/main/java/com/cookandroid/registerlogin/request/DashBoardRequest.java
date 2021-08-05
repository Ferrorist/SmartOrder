package com.cookandroid.registerlogin.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DashBoardRequest extends StringRequest {
    final static private String URL = "http://thee153.dothome.co.kr/Dash.php";
    private java.util.Map<String, String> map;

    public DashBoardRequest(String restaurant_number, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("rest_num",restaurant_number);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
