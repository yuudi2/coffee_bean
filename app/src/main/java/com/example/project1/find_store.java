package com.example.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.StoreViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class find_store extends AppCompatActivity {

    private SQLiteDatabase mDb2;
    private RecyclerView recyclerView;
    private StoreViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_store);


        recyclerView = findViewById(R.id.recyclerView_store);

        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb2 = dbHelper.getWritableDatabase();
        //커서에 결과를 저장
        Cursor cursor = getAllGuests();


        // 데이터를 표시할 커서를 위한 어댑터 생성
        //adapter = new StoreViewAdapter(this, cursor);
        adapter = new StoreViewAdapter(this, cursor  );
        // 리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        byte[] store_img1 = intToByte(R.drawable.store1);
        byte[] store_img2 = intToByte(R.drawable.store2);
        byte[] store_img3 = intToByte(R.drawable.store3);

        deleteAll(mDb2);

        addStore( "테헤란로하이닉스뒷점","02-538-9927","서울시 강남구 테헤란로 70길 12 1층" , "평일 07:00~21:00 | 주말,공휴일 08:00~21:00" , 37.504702,   127.053144, store_img1 );
        addStore( "선릉역3번출구세방빌딩점","02-2051-9328","서울특별시 강남구 선릉로 433 세방빌딩 1층" , "월-금 07:00~22:00 | 주말,공휴일 09:00-21:00" , 37.503773,   127.048916, store_img2 );
        addStore( "선릉KSA한국표준협회점","02-3141-7948","서울시 강남구 테헤란로69길 5 DT센터점 1층" , "월-금 07:00~21:00 | 주말08:30~21:00" , 37.506195,   127.052403, store_img3 );
        addStore( "삼성루첸타워점","02-994-8879","서울 강남구 대치동 943-2 루첸타워 1층 로비" , "월-금 07:00~20:00 | 주말,공휴일 휴점" , 37.507063,   127.058776, store_img1 );
        addStore( "선정릉역점","02-2058-3028","서울시 강남구 봉은사로 331 SH빌딩점 B1-1층" , "월-금 07:00~22:00 | 주말,공휴일 09:00~22:00" , 37.510281,   127.042971, store_img2 );
        addStore( "테헤란로비젼타워점","02-558-7101","서울시 강남구 테헤란로 312 707-2번지1층" , "월-금 07:00~22:00 | 주말,공휴일 11:00~19:00" , 37.502902,   127.045034, store_img3 );


        // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
        adapter.swapCursor(getAllGuests());
    }

    public Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb2.query(
                CartlistContract.StorelistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CartlistContract.StorelistEntry.COLUMN_TIMESTAMP
        );
    }

    public void addStore(String name, String tel, String address, String open, Double lat, Double lng, byte[] img) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */

        cv.put(CartlistContract.StorelistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.StorelistEntry.COLUMN_TEL, tel);
        cv.put(CartlistContract.StorelistEntry.COLUMN_ADDRESS, address);
        cv.put(CartlistContract.StorelistEntry.COLUMN_OPEN, open);
        cv.put(CartlistContract.StorelistEntry.COLUMN_LAT, lat);
        cv.put(CartlistContract.StorelistEntry.COLUMN_LNG, lng);
        cv.put(CartlistContract.StorelistEntry.COLUMN_IMG, img);


        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb2.insert(CartlistContract.StorelistEntry.TABLE_NAME, null, cv);
    }

    public void deleteAll(SQLiteDatabase database) {
        database.execSQL("DELETE FROM storelist");
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
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}