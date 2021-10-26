package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class change_info extends AppCompatActivity {

    TextView user_setname,user_setid;
    EditText user_setnum, user_setpwd, user_setemail;
    Button change_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        user_setname = findViewById(R.id.user_setname);
        user_setid = findViewById(R.id.user_setid);
        user_setnum = findViewById(R.id.user_setnum);
        user_setpwd = findViewById(R.id.user_setpwd);
        user_setemail = findViewById(R.id.user_setemail);
        change_info = findViewById(R.id.change_info);


        SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
        String userid = pref.getString("user_id", "");
        user_setid.setText(userid);

        SharedPreferences pref2 = getSharedPreferences("username", MODE_PRIVATE);
        String username = pref2.getString("name", "");
        user_setname.setText(username);



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

                        user_setname.setText(user_name);
                        user_setnum.setText(user_phonenum);
                        user_setpwd.setText(user_passwd);
                        user_setemail.setText(user_email);
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
        SelectUser selectUser = new SelectUser(userid , responseListener);
        RequestQueue queue = Volley.newRequestQueue( change_info.this );
        queue.add( selectUser );



        change_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenum = user_setnum.getText().toString();
                String password = user_setpwd.getText().toString();
                String email = user_setemail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success){

                                Intent intent = new Intent(getApplicationContext(), my_info.class);
                                startActivity(intent);
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
                UpdateUser updateUser = new UpdateUser(userid ,password, phonenum, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue( change_info.this );
                queue.add( updateUser );
            }
        });
    }
}