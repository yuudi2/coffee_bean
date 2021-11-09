package com.example.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class register_coupon extends AppCompatActivity {

    Button regis_coupon;
    EditText coupon_num;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_coupon);


        regis_coupon = findViewById(R.id.regis_coupon);
        coupon_num = findViewById(R.id.coupon_num);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();


        regis_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number =  Integer.parseInt(coupon_num.getText().toString());
                couponcheck(number);


                Intent intent = new Intent(getApplicationContext(), receive_gift.class);
                startActivity(intent);
            }
        });
    }



    //해당 쿠폰이 있는지 확인
    private void couponcheck(int couponnum) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT coupon FROM couponlist", null);
        int count = 0;
        int use = 0;
        while (cursor.moveToNext()) {
            if (cursor.getInt(0)==couponnum) {

                Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT coupon FROM mycoulist", null);
                while (c.moveToNext()) {
                    if (c.getInt(0)==couponnum) {
                        Toast.makeText(getBaseContext(), "이미 등록 완료된 쿠폰입니다.", Toast.LENGTH_SHORT).show();
                        use++;
                        break;
                    }
                }

                if(use == 0) {
                    coupon_info(couponnum);
                    count++;
                }
                break;
            }
        }

        if(count == 0 && use == 0){
            Toast.makeText(getBaseContext(), "해당 쿠폰번호가 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }


    //쿠폰 정보
    private void coupon_info(int couponnum) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT img,name FROM couponlist WHERE coupon =" + couponnum, null);
        while (c.moveToNext()) {
            int i = byte2Int(c.getBlob(0));
            byte[] img = c.getBlob(0);
            String n = c.getString(1);
            addMyCou(img, n, couponnum);
            Toast.makeText(getBaseContext(), "쿠폰있음 이름은 "+ n + " 이미지는 " + i, Toast.LENGTH_SHORT).show();
            break;
        }
    }


    //쿠폰 이미 등록 여부
    private void coupon_use(int couponnum) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT coupon FROM mycoulist", null);
        while (c.moveToNext()) {
            if (c.getInt(0)==couponnum) {
                Toast.makeText(getBaseContext(), "이미 등록 완료된 쿠폰입니다.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }



    public void addMyCou(byte[] img, String name, int num) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();


        cv.put(CartlistContract.MycoulistEntry.COLUMN_IMG, img);
        cv.put(CartlistContract.MycoulistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.MycoulistEntry.COLUMN_COUPONNUM, num);


        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb.insert(CartlistContract.MycoulistEntry.TABLE_NAME, null, cv);
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