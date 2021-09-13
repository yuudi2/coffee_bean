package com.example.project1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import Adapter.CoffeeViewAdaper;
//import Data.CoffeeData;

public class menu_choice extends AppCompatActivity {

//    private RecyclerView mRecyclerView = null;
//    private CoffeeViewAdaper mAdapter2 = null;

//    ArrayList<CoffeeData> coffeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice);

//        CoffeeViewAdaper adapter = new CoffeeViewAdaper(list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(adapter);


//        mAdapter2 = new CoffeeViewAdaper(coffeeList);
//        CoffeeViewAdaper.setAdapter(mAdapter2);
    }

    public void additem(int image, String mainText, String subText,int point){
//        CoffeeData item = new CoffeeData(image,mainText,subText,point);
//
//        item.setImage(image);
//        item.setMainText(mainText);
//        item.setSubText(subText);
//
//        coffeeList.add(item);
    }
}