package com.cookandroid.registerlogin;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangePassRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static private String URL = "http://thee153.dothome.co.kr/CHuserPass.php";
    private Map<String, String> map;


    public ChangePassRequest(String userID, String userPassword, String userName, int userNum, Response .Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userName",userName);
        map.put("userPassword",userPassword);
        map.put("userNum",userNum + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}