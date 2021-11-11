package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class order_details extends AppCompatActivity {

    Button order_pickup;
    TextView order_num,order_store,menu_name,menu_price;
    RelativeLayout view1, view2;

    int nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        order_pickup = findViewById(R.id.order_pickup);
        order_num = findViewById(R.id.order_num);
        order_store = findViewById(R.id.order_store);
        menu_name = findViewById(R.id.menu_name);
        menu_price = findViewById(R.id.menu_price);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);

        //주문 매뉴, 가격
        SharedPreferences pref = getSharedPreferences("details", MODE_PRIVATE);
        String name = pref.getString("name", "");
        int price = pref.getInt("price", 0);


        if(name.equals("")){
            view2.setVisibility(View.VISIBLE);
            view1.setVisibility(View.GONE);
        }else{
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            menu_name.setText(name);
            menu_price.setText(Integer.toString(price) + "원");
        }


        //가게 이름
        SharedPreferences pref2 = getSharedPreferences("store", MODE_PRIVATE);
        SharedPreferences pref5 = getSharedPreferences("store2", MODE_PRIVATE);
        String store = pref2.getString("key", "");
        Log.d("태그", store + "는");


        //재접속 했을때 intent = null 이라서 추가로 이름 저장
        if(store.equals("")) {
            String orderstore = pref5.getString("store2", "");
            order_store.setText(orderstore);

        }else{
            SharedPreferences.Editor editor3 = pref5.edit();
            editor3.putString("store2", store);
            editor3.commit();
            order_store.setText(store);
        }

        //주문번호
        SharedPreferences pref3 = getSharedPreferences("ordernums", MODE_PRIVATE);

        nums = pref3.getInt("nums", 0);
        order_num.setText(Integer.toString(nums));



        order_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref4 = getSharedPreferences("details", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref4.edit();
                editor.clear();
                editor.commit();

                SharedPreferences.Editor editor2 = pref3.edit();
                nums = pref3.getInt("nums", 0);
                nums = nums + 1;
                editor2.putInt("nums", nums);
                editor2.commit();

                Intent intent = new Intent(getApplicationContext(), main_screen.class);
                startActivity(intent);

//                view2.setVisibility(View.VISIBLE);
//                view1.setVisibility(View.GONE);
            }
        });
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}