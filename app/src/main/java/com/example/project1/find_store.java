package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
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


    Cursor cur;

    double latitude = 0;
    double longitude = 0;


    public static Context context2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_store);

        context2 = this;

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


        cur = mDb2.rawQuery("SELECT * FROM storelist order by distance desc",null);
        cur.moveToNext();


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
                CartlistContract.StorelistEntry.COLUMN_DISTANCE + " ASC"
        );
    }


    //현재 좌표 구하기
    public void onLocationChanged(Location location){
//        String provider = location.getProvider();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
//        altitude = location.getAltitude();

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    public void onProviderEnabled(String provider) {

    }
    public void onProviderDisabled(String provider) {

    }



    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}