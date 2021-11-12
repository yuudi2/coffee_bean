package com.example.project1;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class select_gift extends AppCompatActivity {
    ImageView getgift_img;
    TextView getgift_name, getgift_price, pay_price;
    EditText phone_num;
    Button send_mms;


    String g_name = "";
    int g_price;
    int g_img;

    int point =0;
    int change_point =0;

    private SQLiteDatabase mDb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gift);

        getgift_img = findViewById(R.id.getgift_img);
        getgift_name = findViewById(R.id.getgift_name);
        getgift_price = findViewById(R.id.getgift_price);

        g_img = getIntent().getExtras().getInt("img");
        g_name = getIntent().getExtras().getString("name");
        g_price = getIntent().getExtras().getInt("price");

        getgift_img.setImageResource(g_img);
        getgift_name.setText(g_name);
        getgift_price.setText(String.valueOf(g_price) + "원");


        phone_num = findViewById(R.id.send_phonenum);
        send_mms = findViewById(R.id.send_mms);


        //인증번호 난수
        Random rannum = new Random();
        int ran = rannum.nextInt(10000000);


        pay_price = findViewById(R.id.pay_price);
        pay_price.setText(String.valueOf(g_price) + "원");


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb5 = dbHelper.getWritableDatabase();

        byte[] img_g = intToByte(g_img);

        SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref.getString("user_id", "");
        //Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();




        send_mms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenum = phone_num.getText().toString();

                SharedPreferences pref = getSharedPreferences("username", MODE_PRIVATE);
                String username = pref.getString("name", "");
                String text = "쿠폰번호 [" + ran + "]\n" + username + "님이 선물을 보냈습니다.";


                if(phonenum.length()>0) {

                    final Dialog dialog = new Dialog(select_gift.this);

                    dialog.setContentView(R.layout.custom_pay);
                    dialog.show();


                    ImageView img = (ImageView)dialog.findViewById( R.id.gift_img);
                    img.setImageResource(g_img);
                    TextView name = (TextView)dialog.findViewById(R.id.gift_name);
                    name.setText(g_name);
                    TextView price = (TextView)dialog.findViewById(R.id.gift_price);
                    price.setText(String.valueOf(g_price) + "원");
                    TextView have_point = (TextView)dialog.findViewById(R.id.have_point);
                    RelativeLayout no_point = (RelativeLayout)dialog.findViewById(R.id.no_point);
                    RelativeLayout yes_point = (RelativeLayout)dialog.findViewById(R.id.yes_point);


                    Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" +id + "'", null);
                    while (c.moveToNext()) {
                        point = c.getInt(0);
                        have_point.setText(String.valueOf(point) + "원");
                        break;
                    }

                    TextView get_price = (TextView)dialog.findViewById(R.id.get_price);
                    get_price.setText(String.valueOf(g_price) + "원");
                    TextView remain_price = (TextView)dialog.findViewById(R.id.remain_price);
                    remain_price.setText(String.valueOf(point - g_price) + "원");

                    change_point = point - g_price;


                    if(change_point>=0){
                        no_point.setVisibility(View.GONE);
                        yes_point.setVisibility(View.VISIBLE);
                    }else{
                        no_point.setVisibility(View.VISIBLE);
                        yes_point.setVisibility(View.GONE);
                        remain_price.setText("잔액부족");
                    }


                    Button no_dialog = (Button)dialog.findViewById(R.id.no_dialog);
                    Button yes_dialog = (Button)dialog.findViewById(R.id.yes_dialog);

                    no_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    yes_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(change_point<0){


                            } else{
                                sendSMS(phonenum, text);
                                addNewCou(img_g, g_name, g_price, ran);
                                Log.d("태그","쿠폰번호는 " + ran);
                                update(id, change_point);
                                addpointuse(id, g_name, change_point, g_price, "선물하기");
                                Intent intent = new Intent(getApplicationContext(), send_gift.class);
                                startActivity(intent);
                            }


                        }
                    });

                    Button no_move = (Button)dialog.findViewById(R.id.no_move);
                    Button yes_move = (Button)dialog.findViewById(R.id.yes_move);

                    no_move.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), main_screen.class);
                            startActivity(intent);
                        }
                    });

                    yes_move.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), money_charge.class);
                            startActivity(intent);
                        }
                    });
                }

                else {
                    Toast.makeText(getBaseContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //이미지 저장을 위해 형 변환
    public static byte[] intToByte(int a) {
        byte[] intToByte = new byte[4];
        intToByte[0] |= (byte) ((a & 0xFF000000) >> 24);
        intToByte[1] |= (byte) ((a & 0xFF0000) >> 16);
        intToByte[2] |= (byte) ((a & 0xFF00) >> 8);
        intToByte[3] |= (byte) (a & 0xFF);
        return intToByte;

    }

    public void addNewCou(byte[] img, String name, int price, int num) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */

        cv.put(CartlistContract.CouponlistEntry.COLUMN_IMG, img);
        cv.put(CartlistContract.CouponlistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.CouponlistEntry.COLUMN_PRICE, price);
        cv.put(CartlistContract.CouponlistEntry.COLUMN_COUPONNUM, num);


        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb5.insert(CartlistContract.CouponlistEntry.TABLE_NAME, null, cv);
    }


    public void update(String id, int point) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb5 = dbHelper.getWritableDatabase();
        mDb5.execSQL("UPDATE mypoint SET point = " + point + " WHERE user = '" + id + "'");

    }


    public void addpointuse(String id, String name, int point, int usepoint, String type) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();

        cv.put(CartlistContract.PointuseEntry.COLUMN_USERID, id);
        cv.put(CartlistContract.PointuseEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.PointuseEntry.COLUMN_POINT, point);
        cv.put(CartlistContract.PointuseEntry.COLUMN_POINTUSE, usepoint);
        cv.put(CartlistContract.PointuseEntry.COLUMN_TYPE, type);

        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb5.insert(CartlistContract.PointuseEntry.TABLE_NAME, null, cv);
    }


    private void sendSMS(String phoneNumber, String message)
    {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), send_gift.class);
        startActivity(intent);
    }
}