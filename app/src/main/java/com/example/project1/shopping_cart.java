package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.CartViewAdapter;
import Data.CartData;

public class shopping_cart extends AppCompatActivity {

    int cart_img;
    String cart_name = "";
    int cart_price;
    String cart_size = "";
    String cart_cup = "";
    String cart_cream = "";
    int cart_count;
    int cart_total_price;



    private RecyclerView recyclerView = null;
    private CartViewAdapter adapter = null;

    ArrayList<CartData> cartlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        //장바구니에 담은 커피 정보
        cart_img = getIntent().getExtras().getInt("img");
        cart_name = getIntent().getExtras().getString("name");
        cart_price = getIntent().getExtras().getInt("price");
        cart_size = getIntent().getExtras().getString("size");
        cart_cup = getIntent().getExtras().getString("cup");
        cart_cream = getIntent().getExtras().getString("cream");
        cart_count = getIntent().getExtras().getInt("count");
        cart_total_price = getIntent().getExtras().getInt("totalprice");


        recyclerView = findViewById(R.id.recyclerView_cart);
        adapter = new CartViewAdapter(cartlist);
        recyclerView.setAdapter(adapter);


        cartlist.add (new CartData(cart_img,cart_name,cart_price, cart_size,cart_cup, cart_count, cart_total_price));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}