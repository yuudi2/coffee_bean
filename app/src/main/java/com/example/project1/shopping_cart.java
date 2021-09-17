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



    private RecyclerView recyclerView;
    private CartViewAdapter adapter;

    private ArrayList<CartData> cartlist;

    //ArrayList<CartData> cartlist = new ArrayList<>();


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
        cart_total_price = getIntent().getExtras().getInt("total_price");

        recyclerView = findViewById(R.id.recyclerView_cart);
        cartlist = new ArrayList<CartData>();


        cartlist.add (new CartData(cart_img,cart_name,cart_price, cart_size,cart_cup, cart_count, cart_total_price));


        adapter = new CartViewAdapter(cartlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//
//
//        //쉐어드 모든 키 벨류 가져오기
//        SharedPreferences prefb =getSharedPreferences("cart_contain", MODE_PRIVATE);
//        Collection<?> col_val =  prefb.getAll().values();
//        Iterator<?> it_val = col_val.iterator();
//        Collection<?> col_key = prefb.getAll().keySet();
//        Iterator<?> it_key = col_key.iterator();
//
//        while(it_val.hasNext() && it_key.hasNext()) {
//
//            String key = (String) it_key.next();
//            String value = (String) it_val.next();
//
//            // value 값은 다음과 같이 저장되어있다
//            // "{\"title\":\"hi title\",\"content\":\"hi content\"}"
//            try {
//                // String으로 된 value를 JSONObject로 변환하여 key-value로 데이터 추출
//                JSONObject jsonObject = new JSONObject(value);
//                int img = (int) jsonObject.getInt("img");
//                String name = (String) jsonObject.getString("name");
//                int price = (int) jsonObject.getInt("price");
//                String size = (String) jsonObject.getString("size");
//                String cup = (String) jsonObject.getString("cup");
//                int count = (int) jsonObject.getInt("count");
//                int total_price = (int) jsonObject.getInt("total_price");
//
//                // 리사이클러뷰 어뎁터 addItem으로 목록 추가
//                adapter.addItem(new CartData(img, name, price, size, cup, count, total_price));
//            } catch (JSONException e) {
//
//            }
//
//            // 목록 갱신하여 뷰에 띄어줌
//            adapter.notifyDataSetChanged();
//        }



            adapter.notifyDataSetChanged();


//오또케오또케ㅔㅔㅠㅠㅠ
//        adapter.addItem(new CartData(cart_img, cart_name, cart_price, cart_size, cart_cup, cart_count, cart_total_price));
//        adapter.notifyDataSetChanged();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//            if (resultCode == RESULT_OK) {
//                // 전달 받은 값
//                Intent intent = getIntent();
//                int get_img = data.getExtras().getInt("img");
//                String get_name = data.getExtras().getString("name");
//                int get_price = data.getExtras().getInt("price");
//                String get_size = data.getExtras().getString("size");
//                String get_cup = data.getExtras().getString("cup");
//                int get_count = data.getExtras().getInt("count");
//                int get_total_price = data.getExtras().getInt("total_price");
//                // 리사이클러뷰 목록에 추가
//                adapter.addItem(new CartData(get_img,get_name,get_price,get_size,get_cup,get_count,get_total_price));
//                // 목록 갱신
//                adapter.notifyDataSetChanged();
//
//            }
//
//
//    }




    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}