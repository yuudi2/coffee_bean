package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import Adapter.ViewpagerAdapter;

public class menu_choice extends AppCompatActivity {


    ViewPager viewPager;
    ImageButton shopping_bag;
    Button select_store;
    TextView store_select_name;

    String s_store = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_choice);

        shopping_bag = findViewById(R.id.shopping_bag);
        select_store = findViewById(R.id.select_store);
        store_select_name = findViewById(R.id.store_select_name);

        //viewpager 어댑터 set
        viewPager = findViewById(R.id.viewpager);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("음료");
        tabLayout.getTabAt(1).setText("푸드");
        tabLayout.getTabAt(2).setText("홀케익");

        Intent intent = getIntent();

        SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);

        if (!TextUtils.isEmpty(intent.getStringExtra("name"))) {

            s_store = getIntent().getExtras().getString("name");

            //store_select_name.setText(s_store + "으로 주문합니다.");


            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key", s_store);
            editor.commit();
        }


        String result = pref.getString("key", "");
        if(result.equals("")){
            store_select_name.setText("매장을 선택해 주세요.");
        }else {
            store_select_name.setText(result + "으로 주문합니다.");
        }

        shopping_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), shopping_cart.class);

                view.getContext().startActivity(intent);

            }
        });

        select_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), select_store.class);
                view.getContext().startActivity(intent);

            }
        });
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//        outState.putString("key",s_store);
//
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
        super.onBackPressed();
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