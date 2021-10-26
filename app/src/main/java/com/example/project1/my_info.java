package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class my_info extends AppCompatActivity {

    TextView user_getname,user_getid;
    TextView user_getnum, user_getpwd, user_getemail;
    Button change_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        user_getname = findViewById(R.id.user_getname);
        user_getid = findViewById(R.id.user_getid);
        user_getnum = findViewById(R.id.user_getnum);
        user_getpwd = findViewById(R.id.user_getpwd);
        user_getemail = findViewById(R.id.user_getemail);


        SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
        String userid = pref.getString("user_id", "");
        user_getid.setText(userid);

        String id = user_getid.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    boolean success = jsonObject.getBoolean( "success" );

                    if(success){
                        String user_passwd = jsonObject.getString("user_passwd");
                        String user_name = jsonObject.getString("user_name");
                        String user_phonenum = jsonObject.getString("user_phonenum");
                        String user_email = jsonObject.getString("user_email");

                        user_getname.setText(user_name);
                        user_getnum.setText(user_phonenum);
                        user_getpwd.setText(user_passwd);
                        user_getemail.setText(user_email);
                    }
                    else{
                        Toast.makeText(getBaseContext(), "실패.", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "예외", Toast.LENGTH_SHORT).show();
                }

            }
        };

        //서버로 Volley를 이용해서 요청
        SelectUser selectUser = new SelectUser(id , responseListener);
        RequestQueue queue = Volley.newRequestQueue( my_info.this );
        queue.add( selectUser );


        change_info = findViewById(R.id.change_info);
        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), change_info.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}