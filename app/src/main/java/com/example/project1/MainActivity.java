package com.example.project1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;

    Button find_id, find_passwd, join, login;
    static EditText ed_id, ed_passwd;
    static CheckBox auto_login;
    View.OnClickListener cl;
    String user_id = "";
    String user_passwd = "";
    static SharedPreferences.Editor editor;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        ed_id = findViewById(R.id.user_id);
        ed_passwd = findViewById(R.id.user_passwd);

        find_id = (Button) findViewById(R.id.find_id);
        find_passwd = (Button) findViewById(R.id.find_passwd);
        join = (Button) findViewById(R.id.join);
        login = (Button) findViewById(R.id.login);
        Button btn = findViewById(R.id.join);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();


        //join ?????? ?????? ?????? ??????
        String content = btn.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "????????????";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        btn.setText(spannableString);


        // ?????????, ???????????? ?????? ?????? ??????
        find_id.setPaintFlags(find_id.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        find_passwd.setPaintFlags(find_passwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        // ????????????(join_first) ???????????? ?????????
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), join_first.class);
                startActivity(intent);

            }
        });


        //?????? ?????? ??????
        SharedPreferences pref2 = getSharedPreferences("store", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.clear();
        editor2.commit();



        //????????? & ???????????????

        SharedPreferences autologin = getSharedPreferences("autologin", Activity.MODE_PRIVATE);
        editor = autologin.edit();

        auto_login = findViewById(R.id.auto_login);


        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    String ID = ed_id.getText().toString();
                    String PW = ed_passwd.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();

                } else {

                    editor.clear();
                    editor.commit();

                }
            }
        });

        if (autologin.getBoolean("Auto_Login_enabled", false)) {
            ed_id.setText(autologin.getString("ID", ""));
            ed_passwd.setText(autologin.getString("PW", ""));
            auto_login.setChecked(true);

            this.AutoLoginCheck();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = ed_id.getText().toString();
                String user_passwd = ed_passwd.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                String user_id = jsonObject.getString("user_id");
                                String user_passwd = jsonObject.getString("user_passwd");
                                String user_name = jsonObject.getString("user_name");
                                String user_phonenum = jsonObject.getString("user_phonenum");
                                String user_email = jsonObject.getString("user_email");

                                Intent intent = new Intent(getApplicationContext(), main_screen.class);

                                SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("user_id", user_id);
                                editor.commit();


                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_passwd", user_passwd);
                                intent.putExtra("user_name", user_name);
                                intent.putExtra("user_phonenum", user_phonenum);
                                intent.putExtra("user_email", user_email);

                                have_point(user_id);
                                have_count(user_id);

                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "???????????? ??????????????? ???????????????????????????.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_passwd, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

        getHashKey();
    }




    //?????????????????? ?????? ??????
    public void AutoLoginCheck() {

        String user_id = ed_id.getText().toString();
        String user_passwd = ed_passwd.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {

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


                        have_point(user_id);
                        have_count(user_id);

                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "???????????? ??????????????? ???????????????????????????.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(user_id, user_passwd, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);


    }



    //??????????????? ???????????? ??????, ???????????? ?????????
    public static void uncheck(){
        auto_login.setChecked(false);
        ed_passwd.setText("");
    }


    //????????? ?????? ??????
    public void have_point(String id){
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT user FROM mypoint", null);
        boolean have = false;
        while (c.moveToNext()) {
            if((c.getString(0)).equals(id)){
                have = true;
                break;
            }
        }

        if(have == false){
            addMypoint(id, 0);
            Log.d("??????","?????????");
        }
    }

    //?????? ????????? ?????? ??????
    public void have_count(String id){
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT user FROM mycount", null);
        boolean have = false;
        while (c.moveToNext()) {
            if((c.getString(0)).equals(id)){
                have = true;
                break;
            }
        }

        if(have == false){
            addMycount(id, 0);
            Log.d("??????","?????????");
        }
    }

    public void addMypoint(String id, int point) {
        // DB??? ???????????? ????????? ?????? ????????? ContentValue ????????? ???????????? ??????.
        ContentValues cv = new ContentValues();

        cv.put(CartlistContract.PointEntry.COLUMN_USERID, id);
        cv.put(CartlistContract.PointEntry.COLUMN_POINT, point);


        // cv??? ????????? ?????? ???????????? ????????? ?????? ????????????.
        mDb.insert(CartlistContract.PointEntry.TABLE_NAME, null, cv);
    }

    public void addMycount(String id, int count) {
        // DB??? ???????????? ????????? ?????? ????????? ContentValue ????????? ???????????? ??????.
        ContentValues cv = new ContentValues();

        cv.put(CartlistContract.CountEntry.COLUMN_USERID, id);
        cv.put(CartlistContract.CountEntry.COLUMN_COUNT, count);


        // cv??? ????????? ?????? ???????????? ????????? ?????? ????????????.
        mDb.insert(CartlistContract.CountEntry.TABLE_NAME, null, cv);
    }


    //????????? api hashkey
    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}