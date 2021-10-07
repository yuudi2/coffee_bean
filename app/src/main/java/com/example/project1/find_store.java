package com.example.project1;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.StoreViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class find_store extends AppCompatActivity {

    private SQLiteDatabase mDb2;
    private RecyclerView recyclerView;
    private StoreViewAdapter adapter;

    private LocationManager locationManager;
    Location myLocation;
    Cursor cur;

    double latitude = 0;
    double longitude = 0;
    double distance = 0;

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

        byte[] store_img1 = intToByte(R.drawable.store1);
        byte[] store_img2 = intToByte(R.drawable.store2);
        byte[] store_img3 = intToByte(R.drawable.store3);


        //현재 내 좌표(위도, 경도) 구하기
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        ((find_store)find_store.context2).onLocationChanged(myLocation);


        //내 좌표
        double sLat =  Double.valueOf(myLocation.getLatitude());
        double sLng =  Double.valueOf(myLocation.getLongitude());

        //목표 좌표
        Double [] Lat_i = {37.504702,37.503773,37.506195,37.507063,37.510281,37.502902};
        Double [] Lng_i = {127.053144,127.048916,127.052403,127.058776,127.042971,127.045034};
        Double[] distancelist = new Double[6];


        for (int i=0;i<6;i++){
            double eLat = Lat_i[i];
            double eLng = Lng_i[i];

            Double s_distance = calcDistance(sLat, sLng, eLat, eLng);

            distancelist[i] = s_distance;
        }



        deleteAll(mDb2);

        addStore( "테헤란로하이닉스뒷점","02-538-9927","서울시 강남구 테헤란로 70길 12 1층" , "평일 07:00~21:00 | 주말,공휴일 08:00~21:00" , 37.504702,   127.053144, store_img1, distancelist[0] );
        addStore( "선릉역3번출구세방빌딩점","02-2051-9328","서울특별시 강남구 선릉로 433 세방빌딩 1층" , "월-금 07:00~22:00 | 주말,공휴일 09:00-21:00" , 37.503773,   127.048916, store_img2, distancelist[1] );
        addStore( "선릉KSA한국표준협회점","02-3141-7948","서울시 강남구 테헤란로69길 5 DT센터점 1층" , "월-금 07:00~21:00 | 주말08:30~21:00" , 37.506195,   127.052403, store_img3, distancelist[2] );
        addStore( "삼성루첸타워점","02-994-8879","서울 강남구 대치동 943-2 루첸타워 1층 로비" , "월-금 07:00~20:00 | 주말,공휴일 휴점" , 37.507063,   127.058776, store_img1, distancelist[3] );
        addStore( "선정릉역점","02-2058-3028","서울시 강남구 봉은사로 331 SH빌딩점 B1-1층" , "월-금 07:00~22:00 | 주말,공휴일 09:00~22:00" , 37.510281,   127.042971, store_img2, distancelist[4] );
        addStore( "테헤란로비젼타워점","02-558-7101","서울시 강남구 테헤란로 312 707-2번지1층" , "월-금 07:00~22:00 | 주말,공휴일 11:00~19:00" , 37.502902,   127.045034, store_img3, distancelist[5] );

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

    public void addStore(String name, String tel, String address, String open, Double lat, Double lng, byte[] img, Double distance) {
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
        cv.put(CartlistContract.StorelistEntry.COLUMN_DISTANCE, distance);


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


    //좌표 간 거리 계산산
    public static Double calcDistance(double lat1, double lon1, double lat2, double lon2){
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        double rslt = Math.round(Math.round(ret));

        return rslt;
    }





    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
    }
}