package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.SendGiftViewAdapter;
import Data.GiftData;

public class send_gift extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private SendGiftViewAdapter adapter = null;


    ImageButton gift_bag;

    ArrayList<GiftData> giftlist = new ArrayList<>();

    int [] gift_img = {R.drawable.coffee1, R.drawable.coffee2, R.drawable.coffee3,
            R.drawable.coffee4, R.drawable.coffee5, R.drawable.coffee6, R.drawable.cake1, R.drawable.cake2, R.drawable.cake3,
            R.drawable.cake4, R.drawable.cake5, R.drawable.cake6, R.drawable.cake7, R.drawable.cake8};
    String [] gift_name = {"헤이즐넛아메리카노IB","단팥IB","인절미IB","블랙다이몬 아이스커피","블랙다이몬 카페라떼","블랙다이몬 카페수아","바닐라슈크림케익","소보로얼그레이커스터드롤","크런치헤이즐넛다크초코롤","리얼듬뿍피칸파이","레드벨벳케익",
            "초콜릿무스케익","생크림카스테라","블루베리 치즈케익"};
    int [] gift_price = {6000,6900,6800,5800,6300,7000,6200,4500,4800,8700,5300,5700,5300,6200};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift);

        giftlist.clear();

        //recyclerview 어댑터 set
        recyclerView = findViewById(R.id.recyclerView_sendgift);
        adapter = new SendGiftViewAdapter(giftlist);
        recyclerView.setAdapter(adapter);

        for (int i=0;i<14;i++){
            giftlist.add (new GiftData(gift_img[i], gift_name[i], gift_price[i]));
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        gift_bag = findViewById(R.id.gift_bag);

        gift_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),receive_gift.class);
                startActivity(intent);
            }
        });
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}