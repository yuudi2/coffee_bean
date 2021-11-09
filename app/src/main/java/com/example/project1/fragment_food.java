package com.example.project1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.FoodViewAdapter;
import Data.FoodData;


public class fragment_food extends Fragment {

    private RecyclerView recyclerView = null;
    private FoodViewAdapter adapter = null;

    ArrayList<FoodData> foodlist = new ArrayList<>();

    int[] food_img = {R.drawable.food1, R.drawable.food2, R.drawable.food3,
            R.drawable.food4, R.drawable.food5, R.drawable.food6, R.drawable.food7, R.drawable.food8, R.drawable.food9};
    String[] food_name = {"트러플 치즈 샐러드", "그릭치킨 샌드위치", "쿡살라미 샌드위치", "플레인 베이글", "블루베리 베이글",
            "치즈 베이글", "크림치즈 머핀", "트리플 초코 머핀" , "버터스카치 머핀"};
    int[] food_price = {6300, 5200, 5900, 3500, 3500, 3500, 3800, 3800, 3800};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_food, container, false);

        foodlist.clear();

        //recyclerview 어댑터 set
        recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new FoodViewAdapter(foodlist);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 9; i++) {
            foodlist.add(new FoodData(food_img[i], food_name[i], food_price[i]));
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }
}