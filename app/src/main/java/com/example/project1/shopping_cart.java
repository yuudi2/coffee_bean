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

import Adapter.CartViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class shopping_cart extends AppCompatActivity {

    int cart_img;
    String cart_name = "";
    int cart_price;
    String cart_size = "";
    String cart_cup = "";
    String cart_cream = "";
    int cart_count;
    int cart_total_price;

    private SQLiteDatabase mDb;
    private RecyclerView recyclerView;
    private CartViewAdapter adapter;
    PreferenceManager pref;

    //private ArrayList<CartData> cartlist;

    //ArrayList<CartData> cartlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();
        // 자동적으로 다섯명의 손님을 DB에 추가
        //TestUtil.insertFakeData(mDb);
        //커서에 결과를 저장
        Cursor cursor = getAllGuests();

        recyclerView = findViewById(R.id.recyclerView_cart);

        // 데이터를 표시할 커서를 위한 어댑터 생성
        adapter = new CartViewAdapter(this, cursor);
        // 리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);


        //장바구니에 담은 커피 정보
        cart_img = getIntent().getExtras().getInt("img");
        cart_name = getIntent().getExtras().getString("name");
        cart_price = getIntent().getExtras().getInt("price");
        cart_size = getIntent().getExtras().getString("size");
        cart_cup = getIntent().getExtras().getString("cup");
        cart_cream = getIntent().getExtras().getString("cream");
        cart_count = getIntent().getExtras().getInt("count");
        cart_total_price = getIntent().getExtras().getInt("total_price");


        //cartlist = new ArrayList<>();


        //cartlist.add(new CartData(cart_img, cart_name, cart_price, cart_size, cart_cup, cart_count, cart_total_price));

        //adapter = new CartViewAdapter(cartlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        byte[] img_b = intToByte(cart_img);

        addNewCart(img_b, cart_name, cart_price, cart_size,cart_cup,cart_count,cart_total_price);

        // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
        adapter.swapCursor(getAllGuests());

    }
    private Cursor getAllGuests() {
        // 두번째 파라미터 (Projection array)는 여러 열들 중에서 출력하고 싶은 것만 선택해서 출력할 수 있게 한다.
        // 모든 열을 출력하고 싶을 때는 null 을 입력한다.
        return mDb.query(
                CartlistContract.CartlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CartlistContract.CartlistEntry.COLUMN_TIMESTAMP
        );
    }

    private long addNewCart(byte[] img, String name, int pirce, String size, String cup, int count, int total_price) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */

        cv.put(CartlistContract.CartlistEntry.COLUMN_IMG, img);
        cv.put(CartlistContract.CartlistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.CartlistEntry.COLUMN_PRICE, pirce);
        cv.put(CartlistContract.CartlistEntry.COLUMN_SIZE, size);
        cv.put(CartlistContract.CartlistEntry.COLUMN_CUP, cup);
        cv.put(CartlistContract.CartlistEntry.COLUMN_COUNT, count);
        cv.put(CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE, total_price);

        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        return mDb.insert(CartlistContract.CartlistEntry.TABLE_NAME, null, cv);
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
