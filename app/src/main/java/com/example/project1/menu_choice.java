package com.example.project1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.CoffeeViewAdapter;
import Data.CoffeeData;

public class menu_choice extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private CoffeeViewAdapter adapter = null;


   ArrayList<CoffeeData> coffeeList = new ArrayList<>();

    int [] coffee_img = {R.drawable.coffee1, R.drawable.coffee2, R.drawable.coffee3,
            R.drawable.coffee4, R.drawable.coffee5, R.drawable.coffee6};
    String [] coffee_name = {"헤이즐넛아메리카노IB","단팥IB","인절미IB","블랙다이몬 아이스커피","블랙다이몬 카페라떼(R)","블랙다이몬 카페수아(R)"};
    int [] coffee_price = {6000,6900,6800,5800,6300,7000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice);

        //Adapter adapter = new Adapter();
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new CoffeeViewAdapter(coffeeList);
        recyclerView.setAdapter(adapter);

        for (int i=0;i<6;i++){
            coffeeList.add (new CoffeeData(coffee_img[i], coffee_name[i], coffee_price[i]));
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    public void additem(int img, String name, int price){
        CoffeeData item = new CoffeeData(img,name,price);

        item.setImg(img);
        item.setName(name);
        item.setprice(price);

        coffeeList.add(item);
    }
}