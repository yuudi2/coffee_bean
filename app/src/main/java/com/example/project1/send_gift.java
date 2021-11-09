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
            R.drawable.coffee4, R.drawable.coffee5, R.drawable.coffee6, R.drawable.coffee7, R.drawable.coffee8,
            R.drawable.coffee9, R.drawable.coffee10, R.drawable.coffee11, R.drawable.coffee12, R.drawable.coffee13,
            R.drawable.coffee14, R.drawable.coffee15, R.drawable.coffee16, R.drawable.food1, R.drawable.food2,
            R.drawable.food3, R.drawable.food4, R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food8,
            R.drawable.food9, R.drawable.cake1, R.drawable.cake2, R.drawable.cake3, R.drawable.cake4, R.drawable.cake5,
            R.drawable.cake6, R.drawable.cake7, R.drawable.cake8};
    String [] gift_name = {"헤이즐넛아메리카노IB","단팥IB","인절미IB","블랙다이몬 아이스커피","블랙다이몬 카페라떼","블랙다이몬 카페수아",
            "아메리카노","카페라떼","바닐라라떼","모카라떼","헤이즐넛라떼","캐러멜 마끼아또","레몬 캐모마일티","얼 그레이","후레쉬 망고IB",
            "베리베리IB","트러플 치즈 샐러드", "그릭치킨 샌드위치", "쿡살라미 샌드위치", "플레인 베이글", "블루베리 베이글",
            "치즈 베이글", "크림치즈 머핀", "트리플 초코 머핀", "버터스카치 머핀",
             "바닐라슈크림케익","소보로얼그레이커스터드롤","크런치헤이즐넛다크초코롤",
            "리얼듬뿍피칸파이","레드벨벳케익", "초콜릿무스케익","생크림카스테라","블루베리 치즈케익"};
    int [] gift_price = {6000,6900,6800,5800,6300,7000,4800,5300,5800,5800,6300,6300,5000,5000,6500,6800,6300, 5200,
            5900, 3500, 3500, 3500, 3800, 3800, 3800,6200,4500,4800,8700,5300,5700,5300,6200};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift);

        giftlist.clear();

        //recyclerview 어댑터 set
        recyclerView = findViewById(R.id.recyclerView_sendgift);
        adapter = new SendGiftViewAdapter(giftlist);
        recyclerView.setAdapter(adapter);

        for (int i=0;i<33;i++){
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