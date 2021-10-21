package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.CartlistDBHelper;

public class register_coupon extends AppCompatActivity {

    Button regis_coupon;
    EditText coupon_num;
    private SQLiteDatabase mDb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_coupon);


        regis_coupon = findViewById(R.id.regis_coupon);
        coupon_num = findViewById(R.id.coupon_num);


        regis_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number =  Integer.parseInt(coupon_num.getText().toString());
                couponcheck(number);
            }
        });

    }


    //해당 쿠폰이 있는지 확인
    private void couponcheck(int couponnum) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT coupon FROM couponlist", null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(0)==couponnum) {

                coupon_info(couponnum);

                //Toast.makeText(getBaseContext(), "쿠폰있음", Toast.LENGTH_SHORT).show();
                break;
            }else{
                //Toast.makeText(getBaseContext(), "해당 쿠폰번호가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void coupon_info(int couponnum) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT img,name FROM couponlist WHERE coupon =" + couponnum, null);
        while (c.moveToNext()) {
            int i = byte2Int(c.getBlob(0));
            String n = c.getString(1);
            Toast.makeText(getBaseContext(), "쿠폰있음 이름은 "+ n + " 이미지는 " + i, Toast.LENGTH_SHORT).show();
            break;
        }
    }



    public static int byte2Int(byte[] src) {
        int s1 = src[0] & 0xFF;
        int s2 = src[1] & 0xFF;
        int s3 = src[2] & 0xFF;
        int s4 = src[3] & 0xFF;

        return ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), send_gift.class);
        startActivity(intent);
    }
}