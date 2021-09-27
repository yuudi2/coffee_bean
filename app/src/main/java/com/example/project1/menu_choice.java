package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import Adapter.ViewpagerAdapter;

public class menu_choice extends AppCompatActivity {


    ViewPager viewPager;
    ImageButton shopping_bag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice);

        shopping_bag = findViewById(R.id.shopping_bag);

        //viewpager 어댑터 set
        viewPager = findViewById(R.id.viewpager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("음료");
        tabLayout.getTabAt(1).setText("푸드");
        tabLayout.getTabAt(2).setText("홀케익");


        shopping_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), shopping_cart.class);
                view.getContext().startActivity(intent);

            }
        });

    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void go_shopping_cart(View view) {
        Intent intent = new Intent(getApplicationContext(), shopping_cart.class);
        startActivity(intent);
    }

}