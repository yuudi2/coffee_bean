package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Data.CartlistDBHelper;

public class menu_order_now extends AppCompatActivity {

    private SQLiteDatabase mDb;

    TextView order_name,order_size,order_cup,order_cream,order_count,order_price;
    TextView getstore_name,getstore_address, pay_order_price;

    String type = "";
    String name = "";
    String size = "";
    String cup = "";
    String cream = "";
    int count;
    int total_price;

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

    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}