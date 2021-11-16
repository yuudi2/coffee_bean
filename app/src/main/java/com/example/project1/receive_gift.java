package com.example.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.GiftViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class receive_gift extends AppCompatActivity {

    ImageButton add_coupon;
    RelativeLayout emptyView;

    private RecyclerView recyclerView;
    private GiftViewAdapter adapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_gift);

        add_coupon = findViewById(R.id.add_coupon);
        emptyView = findViewById(R.id.empty_view);
        recyclerView = findViewById(R.id.recyclerView_coupon);



        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();
        //커서에 결과를 저장
        Cursor cursor = getAllGuests();
        // 데이터를 표시할 커서를 위한 어댑터 생성
        adapter = new GiftViewAdapter(this, cursor);
        // 리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();



        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
        adapter.swapCursor(getAllGuests());

        viewvisible();

        add_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),register_coupon.class);
                startActivity(intent);
            }
        });


    }

    public void viewvisible(){
        //장바구니 비었는지 확인
        if(adapter.getItemCount()!=0){
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.swapCursor(getAllGuests());
        } else{
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }


    public Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        SharedPreferences pref = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref.getString("user_id", "");

        return mDb.query(
                CartlistContract.MycoulistEntry.TABLE_NAME,
                null,
                CartlistContract.MycoulistEntry.COLUMN_USERID +" = '" + id + "'",
                null,
                null,
                null,
                CartlistContract.MycoulistEntry.COLUMN_TIMESTAMP
        );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
        super.onBackPressed();
    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), send_gift.class);
        startActivity(intent);
    }
}