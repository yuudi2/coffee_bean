package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class select_menu_cake extends AppCompatActivity {

    ImageView cake_img;
    TextView cake_name, cake_price;
    TextView t_count, order_price;
    ImageButton minus, plus;
    int count = 1;
    int total_count = count;
    Button shopping_cart, order_now;

    String ca_name = "";
    int ca_price;
    int ca_img;

    int change_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu_cake);

        //coffee 정보 불러오기
        cake_img = findViewById(R.id.coffe_img);
        cake_name = findViewById(R.id.coffee_name);
        cake_price = findViewById(R.id.coffee_price);

        ca_img = getIntent().getExtras().getInt("img");
        ca_name = getIntent().getExtras().getString("name");
        ca_price = getIntent().getExtras().getInt("price");

        cake_img.setImageResource(ca_img);
        cake_name.setText(ca_name);
        cake_price.setText(String.valueOf(ca_price)+"원");

        change_price = ca_price;


        //주문금액
        order_price = findViewById(R.id.order_price);
        order_price.setText(String.valueOf(ca_price)+"원");


        //수량 선택
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        t_count = findViewById(R.id.count);
        t_count.setText(count+"");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                t_count.setText(count+"");
                order_price.setText(String.valueOf(ca_price * count)+"원");
                total_count = count;
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count--;
                    t_count.setText(count+"");
                    order_price.setText(String.valueOf(ca_price * count)+"원");
                    total_count = count;
                }
            }
        });


        //장바구니
        shopping_cart = findViewById(R.id.shopping_cart);

        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), shopping_cart.class);

                intent.putExtra("img", ca_img);
                intent.putExtra("name", cake_name.getText());
                intent.putExtra("price", change_price);
                intent.putExtra("count", total_count);
                intent.putExtra("total_price", count * change_price );
                finish();
                view.getContext().startActivity(intent);

            }
        });

    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}