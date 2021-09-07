package com.example.project1;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //public static RequestQueue queue;
    Button find_id, find_passwd, join, login;
    EditText ed_id, ed_passwd;
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_id = findViewById(R.id.user_id);
        ed_passwd = findViewById(R.id.user_passwd);

        find_id = (Button)findViewById(R.id.find_id);
        find_passwd = (Button)findViewById(R.id.find_passwd);
        join = (Button)findViewById(R.id.join);
        login = (Button)findViewById(R.id.login);
        Button btn = findViewById(R.id.join);


        //join 버튼 글씨 크기 조정
        String content = btn.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "회원가입";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        btn.setText(spannableString);


        // 아이디, 비밀번호 찾기 버튼 밑줄
        find_id.setPaintFlags(find_id.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        find_passwd.setPaintFlags(find_passwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        // 회원가입(join_first) 엑티비티 띄우기
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),join_first.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_id = ed_id.getText().toString();
                String user_passwd = ed_passwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success){

                                String user_id = jsonObject.getString("user_id");
                                String user_passwd = jsonObject.getString("user_passwd");
                                String user_name = jsonObject.getString("user_name");
                                String user_phonenum = jsonObject.getString("user_phonenum");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(getApplicationContext(), main_screen.class);

                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_passwd", user_passwd);
                                intent.putExtra("user_name", user_name);
                                intent.putExtra("user_phonenum", user_phonenum);
                                intent.putExtra("user_email", user_email);

                                startActivity(intent);

                            } else {
                                Toast.makeText( getApplicationContext(), "아이디나 비밀번호를 잘못입력하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( user_id, user_passwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( MainActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}