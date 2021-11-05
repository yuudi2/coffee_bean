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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.OrderViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class menu_order extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private RecyclerView recyclerView;
    private OrderViewAdapter adapter;

    Cursor cur;
    Button order_agree;

    TextView getstore_name,getstore_address, pay_order_price;

    int change_point =0;
    int point =0;

    String f_name = "";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_order);

        recyclerView = findViewById(R.id.recyclerView_order);

        getstore_name = findViewById(R.id.getstore_name);
        getstore_address = findViewById(R.id.getstore_address);
        pay_order_price = findViewById(R.id.pay_order_price);

        order_agree = findViewById(R.id.order_agree);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllGuests();

        adapter = new OrderViewAdapter(this, cursor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();

        SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);
        String name = pref.getString("key", "");

        getstore_name.setText(name);

        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT address FROM storelist WHERE name ='" +name + "'", null);
        while (c.moveToNext()) {
            String address = c.getString(0);
            getstore_address.setText(address);
            break;
        }

        Cursor cc = dbHelper.getReadableDatabase().rawQuery("SELECT name FROM cartlist", null);
        while (cc.moveToNext()) {
            f_name = cc.getString(0);
            count = cc.getCount()-1;
            break;
        }


        //전체 금액
        String totalsum = "SELECT SUM(total_price) FROM cartlist";
        cur = mDb.rawQuery(totalsum, null);
        cur.moveToNext();
        String sum = String.valueOf(cur.getInt(0));
        pay_order_price.setText(sum + " 원");

        int total_price = Integer.parseInt(sum);


        SharedPreferences pref2 = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref2.getString("user_id", "");

        order_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" +id + "'", null);
                while (c.moveToNext()) {
                    point = c.getInt(0);
                    break;
                }

                change_point = point - total_price;

                if(change_point<0){


                } else{
                    update(id, change_point);
                    addpointuse(f_name +"외 " + count+"개", change_point, total_price, "구매");
                    Intent intent = new Intent(getApplicationContext(), order_complete.class);
                    startActivity(intent);
                }

            }
        });
    }

    public Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb.query(
                CartlistContract.CartlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CartlistContract.CartlistEntry.COLUMN_TIMESTAMP
        );
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

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}