package com.example.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Data.CartlistDBHelper;

public class money_charge extends AppCompatActivity {

    int point =0;
    int change_point =0;

    TextView now_money, order_price;
    RadioGroup radioGroup;
    Button order_now;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_charge);


        now_money = findViewById(R.id.now_money);
        order_price = findViewById(R.id.order_price);
        order_now = findViewById(R.id.order_now);

        SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref.getString("user_id", "");
        //Toast.makeText(getBaseContext(), id, Toast.LENGTH_SHORT).show();


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" +id + "'", null);
        while (c.moveToNext()) {
            point = c.getInt(0);
            now_money.setText(point + "원");
            order_price.setText(point + "원");
            break;
        }


        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if(i == R.id.cheon){
                    change_point = point + 1000;
                    order_price.setText(change_point + "원");

                } if(i == R.id.o_cheon){
                    change_point = point + 5000;
                    order_price.setText(change_point + "원");

                } if(i == R.id.man){
                    change_point = point + 10000;
                    order_price.setText(change_point + "원");

                } if(i == R.id.e_man){
                    change_point = point + 20000;
                    order_price.setText(change_point + "원");

                } else if(i == R.id.o_man){
                    change_point = point + 50000;
                    order_price.setText(change_point + "원");
                }
            }
        });


        order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                String charge = radioButton.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(money_charge.this);
                builder.setTitle("포인트 충전");
                builder.setMessage(charge+"을 충전하시겠습니까?");


                //  setPositiveButton -> "OK"버튼
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //포인트 변경
                        update(id, change_point);

                        Intent intent = new Intent(getApplicationContext(), main_screen.class);
                        startActivity(intent);
                    }
                });

                //  setNegativeButton -> "Cancel" 버튼  //
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                builder.show();
            }
        });

    }

    public void update(String id, int point) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mDb.execSQL("UPDATE mypoint SET point = " + point + " WHERE user = '" + id + "'");
        mDb.close();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
        super.onBackPressed();
    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}