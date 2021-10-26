package com.example.project1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SelectUser extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://lyuri203.dothome.co.kr/select.php";
    private Map<String, String> map;

    public SelectUser(String user_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("user_id", user_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}