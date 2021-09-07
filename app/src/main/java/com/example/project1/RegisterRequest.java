package com.example.project1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://lyuri203.dothome.co.kr/register.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String user_id, String user_passwd, String user_name, String user_phonenum, String user_email, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("user_passwd", user_passwd);
        map.put("user_name", user_name);
        map.put("user_phonenum", user_phonenum);
        map.put("user_email", user_email);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
