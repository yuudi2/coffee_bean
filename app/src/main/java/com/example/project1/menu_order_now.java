package com.example.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class menu_order_now extends AppCompatActivity {

    private SQLiteDatabase mDb;

    TextView order_name,order_size,order_cup,order_cream,order_count,order_price;
    TextView getstore_name,getstore_address, pay_order_price;
    Button order_agree;

    String type = "";
    String name = "";
    String size = "";
    String cup = "";
    String cream = "";
    int count;
    int total_price;

    int change_point =0;
    int point =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order_now);

        order_name = findViewById(R.id.order_name);
        order_size = findViewById(R.id.order_size);
        order_cup = findViewById(R.id.order_cup);
        order_cream = findViewById(R.id.order_cream);
        order_count = findViewById(R.id.order_count);
        order_price = findViewById(R.id.order_price);

        getstore_name = findViewById(R.id.getstore_name);
        getstore_address = findViewById(R.id.getstore_address);
        pay_order_price = findViewById(R.id.pay_order_price);

        order_agree = findViewById(R.id.order_agree);

        SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);
        String s_name = pref.getString("key", "");

        getstore_name.setText(s_name);

        CartlistDBHelper dbHelper = new CartlistDBHelper(this);

        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT address FROM storelist WHERE name ='" +s_name + "'", null);
        while (c.moveToNext()) {
            String address = c.getString(0);
            getstore_address.setText(address);
            break;
        }


        type = getIntent().getExtras().getString("type");

        if(type.equals("커피")) {
            name = getIntent().getExtras().getString("name");
            size = getIntent().getExtras().getString("size");
            cup = getIntent().getExtras().getString("cup");
            cream = getIntent().getExtras().getString("cream");
            count = getIntent().getExtras().getInt("count");
            total_price = getIntent().getExtras().getInt("total_price");


            order_name.setText(name);
            order_size.setText(size);
            order_cup.setText(cup);
            order_cream.setText(cream);
            order_count.setText(Integer.toString(count) + "개");
            order_price.setText(Integer.toString(total_price) + "원");

        } else{
            name = getIntent().getExtras().getString("name");
            count = getIntent().getExtras().getInt("count");
            total_price = getIntent().getExtras().getInt("total_price");


            order_name.setText(name);
            order_count.setText(Integer.toString(count) + "개");
            order_price.setText(Integer.toString(total_price) + "원");
        }

        pay_order_price.setText(Integer.toString(total_price) + "원");


        SharedPreferences pref2 = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref2.getString("user_id", "");


        int img = R.drawable.coupon_img;
        byte[] img_g = intToByte(img);

        Random rannum = new Random();
        int ran = rannum.nextInt(10000000);

        order_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" +id + "'", null);
                while (c.moveToNext()) {
                    point = c.getInt(0);
                    break;
                }

                change_point = point - total_price;


                SharedPreferences pref3 = getSharedPreferences("pointcount", MODE_PRIVATE);
                int pointcount = pref3.getInt("count", 0);

                if(change_point<0){
                    Toast.makeText(getBaseContext(), "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();

                } else{
                    update(id, change_point);
                    addpointuse(name, change_point, total_price, "구매");

                    pointcount = pointcount + 1;
                    SharedPreferences.Editor editor2 = pref3.edit();

                    if(pointcount == 12){
                        editor2.putInt("count", 0);
                        editor2.commit();
                        addMyCou(img_g, "무료 교환권", ran);
                        Toast.makeText(getBaseContext(), "무료 쿠폰이 지급되었습니다.", Toast.LENGTH_SHORT).show();


                    }else{
                        editor2.putInt("count", pointcount);
                        editor2.commit();
                    }

                    Intent intent = new Intent(getApplicationContext(), order_complete.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void update(String id, int point) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mDb.execSQL("UPDATE mypoint SET point = " + point + " WHERE user = '" + id + "'");

    }

    public void addpointuse(String name, int point, int usepoint, String type) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();

        cv.put(CartlistContract.PointuseEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.PointuseEntry.COLUMN_POINT, point);
        cv.put(CartlistContract.PointuseEntry.COLUMN_POINTUSE, usepoint);
        cv.put(CartlistContract.PointuseEntry.COLUMN_TYPE, type);

        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb.insert(CartlistContract.PointuseEntry.TABLE_NAME, null, cv);
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

    public void addMyCou(byte[] img, String name, int num) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */

        cv.put(CartlistContract.MycoulistEntry.COLUMN_IMG, img);
        cv.put(CartlistContract.MycoulistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.MycoulistEntry.COLUMN_COUPONNUM, num);


        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb.insert(CartlistContract.MycoulistEntry.TABLE_NAME, null, cv);
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}