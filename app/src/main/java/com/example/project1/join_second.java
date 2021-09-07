package com.example.project1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class join_second extends AppCompatActivity {

    private EditText email, id, name, phonenum, passwd, passwd_correct;
    private Button id_overlap, agree;
    private boolean validate = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_second);

        email = findViewById(R.id.user_email);
        id = findViewById(R.id.user_id);
        name = findViewById(R.id.user_name);
        phonenum = findViewById(R.id.user_phonenum);
        passwd = findViewById(R.id.user_passwd);
        passwd_correct = findViewById(R.id.user_passwd_correct);

        //아이디 중복 체크
        id_overlap = (Button) findViewById(R.id.id_overlap);
        id_overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString();
                if(validate){
                    return; //검증완료
                }

                if (user_id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                id_overlap.setBackgroundColor(Color.LTGRAY);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(join_second.this);
                queue.add(validateRequest);
            }
        });


        agree = findViewById( R.id.agree );
        agree.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_id = id.getText().toString();
                final String user_passwd = passwd.getText().toString();
                final String user_name = name.getText().toString();
                final String user_phonenum = phonenum.getText().toString();
                final String user_email = email.getText().toString();
                final String pass_correct = passwd_correct.getText().toString();


                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (user_id.equals("") || user_passwd.equals("") || user_name.equals("") || user_phonenum.equals("") || user_email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            //회원가입 성공시
                            if(user_passwd.equals(pass_correct)) {
                                if (success) {

                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", user_name), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(join_second.this, MainActivity.class);
                                    startActivity(intent);

                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(join_second.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest( user_id, user_passwd , user_name, user_phonenum, user_email, responseListener);
                RequestQueue queue = Volley.newRequestQueue( join_second.this );
                queue.add( registerRequest );
            }
        });
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), join_first.class);
        startActivity(intent);
    }
}