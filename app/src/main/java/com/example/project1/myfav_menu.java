package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.MyfavViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class myfav_menu extends AppCompatActivity {

    public static Context context;

    ImageButton shopping_bag, add_favmenu;
    Button menu_regis;

    int fav_img;
    String fav_name = "";

    private SQLiteDatabase mDb3;
    private RecyclerView recyclerView;
    private MyfavViewAdapter adapter;
    RelativeLayout emptyView, emptyView2;
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfav_menu);

        context = this;

        setContentView(R.layout.activity_myfav_menu);

        shopping_bag = findViewById(R.id.shopping_bag);
        add_favmenu = findViewById(R.id.add_favmenu);
        menu_regis = findViewById(R.id.menu_regis);
        recyclerView = findViewById(R.id.recyclerView_cart);


        emptyView = findViewById(R.id.empty_view);
        emptyView2 = findViewById(R.id.empty_view2);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb3 = dbHelper.getWritableDatabase();
        //커서에 결과를 저장
        Cursor cursor = getAllGuests();



        // 데이터를 표시할 커서를 위한 어댑터 생성
        adapter = new MyfavViewAdapter(this, cursor);
        // 리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        //찜 목록에 담은 커피 정보
//        fav_img = getIntent().getExtras().getInt("img");
//        fav_name = getIntent().getExtras().getString("name");


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
        adapter.swapCursor(getAllGuests());


        //장바구니 비었는지 확인
        if(adapter.getItemCount()!=0){
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyView2.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.swapCursor(getAllGuests());
        } else{
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView2.setVisibility(View.VISIBLE);
        }


        menu_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), menu_choice.class);
                view.getContext().startActivity(intent);
            }
        });

        shopping_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), shopping_cart.class);
                view.getContext().startActivity(intent);

            }
        });

        add_favmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), menu_choice.class);
                view.getContext().startActivity(intent);

            }
        });

    }

    public Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb3.query(
                CartlistContract.MyfavlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CartlistContract.MyfavlistEntry.COLUMN_TIMESTAMP
        );
    }


    //이미지 저장을 위해 형 변환
    public static byte[] intToByte(int a) {
        byte[] intToByte = new byte[4];
        intToByte[0] |= (byte)((a&0xFF000000)>>24);
        intToByte[1] |= (byte)((a&0xFF0000)>>16);
        intToByte[2] |= (byte)((a&0xFF00)>>8);
        intToByte[3] |= (byte)(a&0xFF);
        return intToByte;

    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}