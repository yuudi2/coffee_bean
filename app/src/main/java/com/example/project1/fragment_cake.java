package com.example.project1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.CakeViewAdapter;
import Data.CakeData;


public class fragment_cake extends Fragment {


    private RecyclerView recyclerView = null;
    private CakeViewAdapter adapter = null;

    ArrayList<CakeData> cakelist = new ArrayList<>();

    int [] cake_img = {R.drawable.cake1, R.drawable.cake2, R.drawable.cake3,
            R.drawable.cake4, R.drawable.cake5, R.drawable.cake6, R.drawable.cake7, R.drawable.cake8};
    String [] cake_name = {"바닐라슈크림케익","소보로얼그레이커스터드롤","크런치헤이즐넛다크초코롤","리얼듬뿍피칸파이","레드벨벳케익",
            "초콜릿무스케익","생크림카스테라","블루베리 치즈케익"};
    int [] cake_price = {6200,4500,4800,8700,5300,5700,5300,6200};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cake, container, false);

        cakelist.clear();

        //recyclerview 어댑터 set
        recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new CakeViewAdapter(cakelist);
        recyclerView.setAdapter(adapter);

        for (int i=0;i<8;i++){
            cakelist.add (new CakeData(cake_img[i], cake_name[i], cake_price[i]));
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }

    public void additem(int img, String name, int price){
        CakeData item = new CakeData(img,name,price);

        item.setImg(img);
        item.setName(name);
        item.setprice(price);

        cakelist.add(item);
    }

}