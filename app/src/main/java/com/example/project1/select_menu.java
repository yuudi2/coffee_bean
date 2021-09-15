package com.example.project1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Data.CoffeeData;

public class select_menu extends AppCompatActivity {

    ImageView coffee_img;
    TextView coffee_name, coffee_price;
    TextView t_count, order_price;
    ImageButton minus, plus;
    private int count = 1;
    Button small, regular, large, mugcup, oneusecup, cream_yes, cream_no, order_now;

    String size = "";
    String cup = "";
    String cream = "";

    String c_name = "";
    int c_price;
    int c_img;

    int change_price;

    ArrayList<CoffeeData> itemlist = new ArrayList<CoffeeData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        //coffee 정보 불러오기
        coffee_img = findViewById(R.id.coffe_img);
        coffee_name = findViewById(R.id.coffee_name);
        coffee_price = findViewById(R.id.coffee_price);

        c_img = getIntent().getExtras().getInt("img");
        c_name = getIntent().getExtras().getString("name");
        c_price = getIntent().getExtras().getInt("price");

        coffee_img.setImageResource(c_img);
        coffee_name.setText(c_name + "(S)");
        coffee_price.setText(String.valueOf(c_price)+"원");

        change_price = c_price;


        //주문금액
        order_price = findViewById(R.id.order_price);


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
                order_price.setText(String.valueOf(change_price * count)+"원");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count--;
                    t_count.setText(count+"");
                    order_price.setText(String.valueOf(change_price * count)+"원");
                }
            }
        });


        //사이즈 선택
        small = findViewById(R.id.size_small);
        regular = findViewById(R.id.size_regular);
        large = findViewById(R.id.size_large);


        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                small.setBackgroundResource(R.drawable.btn_color3);
                small.setTextColor(Color.parseColor("#754fa0"));
                regular.setBackgroundResource(R.drawable.btn_color5);
                regular.setTextColor(Color.parseColor("#aaaaaa"));
                large.setBackgroundResource(R.drawable.btn_color5);
                large.setTextColor(Color.parseColor("#aaaaaa"));

                regular.setSelected(false);
                large.setSelected(false);

                coffee_name.setText(c_name + "(S)");

                change_price = c_price;
                order_price.setText(String.valueOf(change_price * count)+"원");
                coffee_price.setText(String.valueOf(change_price)+"원");

                size = small.getText().toString();

            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                small.setBackgroundResource(R.drawable.btn_color5);
                small.setTextColor(Color.parseColor("#aaaaaa"));
                regular.setBackgroundResource(R.drawable.btn_color3);
                regular.setTextColor(Color.parseColor("#754fa0"));
                large.setBackgroundResource(R.drawable.btn_color5);
                large.setTextColor(Color.parseColor("#aaaaaa"));

                small.setSelected(false);
                large.setSelected(false);

                coffee_name.setText(c_name + "(R)");

                change_price = c_price + 500;
                order_price.setText(String.valueOf(change_price * count)+"원");
                coffee_price.setText(String.valueOf(change_price)+"원");

                size = regular.getText().toString();
            }
        });

        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regular.setBackgroundResource(R.drawable.btn_color5);
                regular.setTextColor(Color.parseColor("#aaaaaa"));
                small.setBackgroundResource(R.drawable.btn_color5);
                small.setTextColor(Color.parseColor("#aaaaaa"));
                large.setBackgroundResource(R.drawable.btn_color3);
                large.setTextColor(Color.parseColor("#754fa0"));

                regular.setSelected(false);
                small.setSelected(false);

                coffee_name.setText(c_name + "(L)");

                change_price = c_price + 1000;
                order_price.setText(String.valueOf(change_price * count)+"원");
                coffee_price.setText(String.valueOf(change_price)+"원");

                size = large.getText().toString();
            }
        });

        //컵 선택
        mugcup = findViewById(R.id.mugcup);
        oneusecup = findViewById(R.id.oneusecup);

        mugcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mugcup.setBackgroundResource(R.drawable.btn_color3);
                mugcup.setTextColor(Color.parseColor("#754fa0"));
                oneusecup.setBackgroundResource(R.drawable.btn_color5);
                oneusecup.setTextColor(Color.parseColor("#aaaaaa"));

                oneusecup.setSelected(false);

                cup = mugcup.getText().toString();
            }
        });

        oneusecup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneusecup.setBackgroundResource(R.drawable.btn_color3);
                oneusecup.setTextColor(Color.parseColor("#754fa0"));
                mugcup.setBackgroundResource(R.drawable.btn_color5);
                mugcup.setTextColor(Color.parseColor("#aaaaaa"));

                mugcup.setSelected(false);

                cup = oneusecup.getText().toString();
            }
        });




        //휘핑크림 선택
        cream_no = findViewById(R.id.cream_no);
        cream_yes = findViewById(R.id.cream_yes);

        cream_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cream_no.setBackgroundResource(R.drawable.btn_color3);
                cream_no.setTextColor(Color.parseColor("#754fa0"));
                cream_yes.setBackgroundResource(R.drawable.btn_color5);
                cream_yes.setTextColor(Color.parseColor("#aaaaaa"));

                cream_yes.setSelected(false);
                cream_no.getText();
                cream = cream_no.getText().toString();
            }
        });

        cream_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cream_yes.setBackgroundResource(R.drawable.btn_color3);
                cream_yes.setTextColor(Color.parseColor("#754fa0"));
                cream_no.setBackgroundResource(R.drawable.btn_color5);
                cream_no.setTextColor(Color.parseColor("#aaaaaa"));

                cream_no.setSelected(false);

                cream = cream_yes.getText().toString();
            }
        });


        //주문하기 버튼
        order_now = findViewById(R.id.order_now);

        order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), count + "," + size  + "," + cup  + "," + cream, Toast.LENGTH_SHORT ).show();
            }
        });

    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}