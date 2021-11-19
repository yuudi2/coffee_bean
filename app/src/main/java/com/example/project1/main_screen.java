package com.example.project1;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class main_screen extends AppCompatActivity {

    ImageButton arrow, arrow2, menu, order2, mymenu2, gift2, notification;
    ImageView charge2, list2;
    Button order, mymenu, gift;
    TextView info, charge, list, money_have, point_count;
    CardView cardView, cardView2;
    LinearLayout hiddenView, hiddenView2;
    RelativeLayout layout1;

    private SQLiteDatabase mDb;
    private LocationManager locationManager;
    Location myLocation;
    Cursor cur;
    double latitude = 0;
    double longitude = 0;

    int count = 0;

    private long backpressedTime = 0;
    //private Context context = this;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //메인화면에서 어플이 종료될 수 있게 로그인 엑티비티 종료
        MainActivity mactivity = (MainActivity)MainActivity.activity;
        mactivity.finish();

        context = this;

        cardView = findViewById(R.id.base_cardview);
        arrow = findViewById(R.id.arrow_button);
        hiddenView = findViewById(R.id.hidden_view);
        cardView2 = findViewById(R.id.base_cardview2);
        arrow2 = findViewById(R.id.arrow_button2);
        hiddenView2 = findViewById(R.id.hidden_view2);
        layout1 = findViewById(R.id.layout1);

        menu = findViewById(R.id.menu_button);
        order = findViewById(R.id.order);
        order2 = findViewById(R.id.order2);
        mymenu = findViewById(R.id.my_menu);
        mymenu2 = findViewById(R.id.my_menu2);
        gift2 = findViewById(R.id.gift2);
        gift = findViewById(R.id.gift);

        list2 = findViewById(R.id.list2);
        list = findViewById(R.id.list);
        charge2 = findViewById(R.id.charge2);
        charge = findViewById(R.id.charge);
        info = findViewById(R.id.info);
        money_have = findViewById(R.id.money_have);
        notification = findViewById(R.id.notification);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenView.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_icon_down);
                } else {
                    TransitionManager.beginDelayedTransition(cardView,
                            new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_icon_up);
                }
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenView2.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
                    hiddenView2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.drawable.ic_icon_down);
                } else {
                    TransitionManager.beginDelayedTransition(cardView2,
                            new AutoTransition());
                    hiddenView2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.drawable.ic_icon_up);
                }
            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menu_choice.class);
                startActivity(intent);
            }
        });

        order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menu_choice.class);
                startActivity(intent);
            }
        });

        mymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), myfav_menu.class);
                startActivity(intent);
            }
        });

        mymenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), myfav_menu.class);
                startActivity(intent);
            }
        });

        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), send_gift.class);
                startActivity(intent);
            }
        });

        gift2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), send_gift.class);
                startActivity(intent);
            }
        });

//        SharedPreferences pref3 = getSharedPreferences("pointcount", MODE_PRIVATE);
//        boolean first = pref3.getBoolean("isFirst", false);
//        if(first==false){
//            SharedPreferences.Editor editor2 = pref3.edit();
//            editor2.putInt("count", 0);
//            editor2.putBoolean("isFirst",true);
//            editor2.commit();
//
//        }

        SharedPreferences pref4 = getSharedPreferences("ordernums", MODE_PRIVATE);
        boolean first1 = pref4.getBoolean("isFirst", false);
        if (first1 == false) {
            SharedPreferences.Editor editor3 = pref4.edit();
            editor3.putInt("nums", 100);
            editor3.putBoolean("isFirst", true);
            editor3.commit();

        }

        SharedPreferences pref5 = getSharedPreferences("notification", MODE_PRIVATE);
        boolean first2 = pref5.getBoolean("isFirst", false);
        if (first2 == false) {
            SharedPreferences.Editor editor4 = pref5.edit();
            editor4.putString("notification", "off");
            editor4.putBoolean("isFirst", true);
            editor4.commit();

        }

        String noti = pref5.getString("notification", "");
        if (noti.equals("off")) {
            notification.setImageResource(R.drawable.notification);

        } else {
            notification.setImageResource(R.drawable.notification2);

        }


        point_count = findViewById(R.id.point_count);

//        int pointcount = pref3.getInt("count", 0);
//        point_count.setText(String.valueOf(pointcount));


        Intent intent = getIntent();
        SharedPreferences pref = getSharedPreferences("username", MODE_PRIVATE);

        //회원 이름 불러오기
        if (!TextUtils.isEmpty(intent.getStringExtra("user_name"))) {

            String username = getIntent().getStringExtra("user_name");

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("name", username);
            editor.commit();
        }


        String username2 = pref.getString("name", "");

        final TextView user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(username2);
        user_name.setPaintFlags(user_name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //String id = getIntent().getStringExtra("user_id");
        SharedPreferences pref2 = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref2.getString("user_id", "");

        get_point(id);
        get_count(id);


        //navigation header 회원 이름
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.user_name2);

        nav_header_id_text.setText(username2 + "님.");

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if (id == R.id.nav_0) {
                    Intent intent = new Intent(getApplicationContext(), money_charge.class);
                    startActivity(intent);
                } else if (id == R.id.nav_1) {
                    Intent intent = new Intent(getApplicationContext(), point_use.class);
                    startActivity(intent);
                } else if (id == R.id.nav_2) {
                    Intent intent = new Intent(getApplicationContext(), menu_choice.class);
                    startActivity(intent);
                } else if (id == R.id.nav_5) {
                    Intent intent = new Intent(getApplicationContext(), order_details.class);
                    startActivity(intent);
                } else if (id == R.id.nav_3) {
                    Intent intent = new Intent(getApplicationContext(), send_gift.class);
                    startActivity(intent);
                } else if (id == R.id.nav_4) {
                    Intent intent = new Intent(getApplicationContext(), receive_gift.class);
                    startActivity(intent);
                } else if (id == R.id.nav_info) {
                    Intent intent = new Intent(getApplicationContext(), my_info.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    logout();
                }

                return true;
            }
        });

        //매장찾기 버튼
        ImageButton nav_header_find_store = (ImageButton) nav_header_view.findViewById(R.id.find_store);
        nav_header_find_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), find_store.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        //로그아웃
        TextView logout = (TextView) findViewById(R.id.logout);

        logout.setPaintFlags(logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);  //밑줄
        CheckBox check = findViewById(R.id.auto_login);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logout(view);
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), my_info.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start에 지정된 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationView.setItemIconTintList(null);


        //바코드
        MultiFormatWriter gen = new MultiFormatWriter();
        String data = "YOUR DATA";
        try {
            final int WIDTH = 480;
            final int HEIGHT = 180;
            BitMatrix bytemap = gen.encode(data, BarcodeFormat.CODE_128, WIDTH, HEIGHT);
            Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < WIDTH; ++i)
                for (int j = 0; j < HEIGHT; ++j) {
                    bitmap.setPixel(i, j, bytemap.get(i, j) ? Color.BLACK : Color.WHITE);
                }

            ImageView view = (ImageView) findViewById(R.id.barcode);
            view.setImageBitmap(bitmap);
            view.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }


        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), money_charge.class);
                startActivity(intent);
            }
        });

        charge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), money_charge.class);
                startActivity(intent);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), point_use.class);
                startActivity(intent);
            }
        });

        list2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), point_use.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notification.class);
                startActivity(intent);
            }
        });


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();


        byte[] store_img1 = intToByte(R.drawable.store1);
        byte[] store_img2 = intToByte(R.drawable.store2);
        byte[] store_img3 = intToByte(R.drawable.store3);


        //현재 내 좌표(위도, 경도) 구하기
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(myLocation);


        //내 좌표
        double sLat = Double.valueOf(myLocation.getLatitude());
        double sLng = Double.valueOf(myLocation.getLongitude());

        //목표 좌표
        Double[] Lat_i = {37.504702, 37.503773, 37.506195, 37.507063, 37.510281, 37.502902, 37.500318, 37.511946, 37.486696, 37.495634, 37.497615, 37.516577, 37.483663, 37.509353};
        Double[] Lng_i = {127.053144, 127.048916, 127.052403, 127.058776, 127.042971, 127.045034, 127.011825, 127.020432, 127.010534, 127.013538, 127.102532, 127.101224, 127.122250, 127.125789};
        Double[] distancelist = new Double[14];


        for (int i = 0; i < 14; i++) {
            double eLat = Lat_i[i];
            double eLng = Lng_i[i];

            Double s_distance = calcDistance(sLat, sLng, eLat, eLng);
            distancelist[i] = s_distance;
        }

        Cursor cc = dbHelper.getReadableDatabase().rawQuery("SELECT name FROM storelist", null);
        while (cc.moveToNext()) {
            count = cc.getCount();
            break;
        }

        if (count == 0) {
            addStore("테헤란로하이닉스뒷점", "02-538-9927", "서울시 강남구 테헤란로 70길 12 1층", "평일 07:00~21:00 | 주말,공휴일 08:00~21:00", 37.504702, 127.053144, store_img1, distancelist[0]);
            addStore("선릉역3번출구세방빌딩점", "02-2051-9328", "서울특별시 강남구 선릉로 433 세방빌딩 1층", "월-금 07:00~22:00 | 주말,공휴일 09:00-21:00", 37.503773, 127.048916, store_img2, distancelist[1]);
            addStore("선릉KSA한국표준협회점", "02-3141-7948", "서울시 강남구 테헤란로69길 5 DT센터점 1층", "월-금 07:00~21:00 | 주말08:30~21:00", 37.506195, 127.052403, store_img3, distancelist[2]);
            addStore("삼성루첸타워점", "02-994-8879", "서울 강남구 대치동 943-2 루첸타워 1층 로비", "월-금 07:00~20:00 | 주말,공휴일 휴점", 37.507063, 127.058776, store_img1, distancelist[3]);
            addStore("선정릉역점", "02-2058-3028", "서울시 강남구 봉은사로 331 SH빌딩점 B1-1층", "월-금 07:00~22:00 | 주말,공휴일 09:00~22:00", 37.510281, 127.042971, store_img2, distancelist[4]);
            addStore("테헤란로비젼타워점", "02-558-7101", "서울시 강남구 테헤란로 312 707-2번지1층", "월-금 07:00~22:00 | 주말,공휴일 11:00~19:00", 37.502902, 127.045034, store_img3, distancelist[5]);
            addStore("서초중앙로점", "02-537-2992", "서울시 서초구 서초중앙로 209, 해성빌딩 1층", "평일 07:00~22:00 | 주말 08:00~22:00", 37.500318, 127.011825, store_img1, distancelist[6]);
            addStore("논현역6번출구앞점", "02-755-3301", "서울시 서초구 강남대로 563 페이토플라자 1층", "월-금 07:00~21:00 | 주말,공휴일 08:00~21:00", 37.511946, 127.020432, store_img2, distancelist[7]);
            addStore("서초동점", "02-3665-9507", "서울시 서초구 반포대로70", "평일 07:00~21:00 | 토일/공휴일 08:00~21:00", 37.486696, 127.010534, store_img3, distancelist[8]);
            addStore("교대법원점", "02- 3664-7716", "서울시 서초구 서초중앙로 156, 1층", "월-금 07:00~21:00 | 주말/공휴일 10:00~21:00", 37.495634, 127.013538, store_img1, distancelist[9]);
            addStore("송파헬리오시티점", "02-777-3363", "서울시 송파구 송파대로 345 B1층", "월-금 07:00~21:00 | 토,일 07:00~21:00", 37.497615, 127.102532, store_img2, distancelist[10]);
            addStore("잠실향군타워점", "02-412-8812", "서울시 송파구 올림픽로35길 123 1층", "월-금 07:00~22:30 | 토,일/공휴일 08:00~22:00", 37.516577, 127.101224, store_img3, distancelist[11]);
            addStore("문정동환인제약빌딩점", "02-449-9569", "서울시 송파구 법원로6길 11 환인빌딩 1층", "월-금 07:00~21:00 | 토,일 09:00~21:00", 37.483663, 127.122250, store_img1, distancelist[12]);
            addStore("방이역4번출구점", "02-420-0386", "서울시 송파구 방이동 206-11", "매일 08:00-22:00", 37.509353, 127.125789, store_img2, distancelist[13]);
        }
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
        mDb.insert(CartlistContract.StorelistEntry.TABLE_NAME, null, cv);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();

            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    //이미지 저장을 위해 형 변환
    public static byte[] intToByte(int a) {
        byte[] intToByte = new byte[4];
        intToByte[0] |= (byte) ((a & 0xFF000000) >> 24);
        intToByte[1] |= (byte) ((a & 0xFF0000) >> 16);
        intToByte[2] |= (byte) ((a & 0xFF00) >> 8);
        intToByte[3] |= (byte) (a & 0xFF);
        return intToByte;

    }


    //현재 좌표 구하기
    public void onLocationChanged(Location location) {
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
    public static Double calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI / 180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        double rslt = Math.round(Math.round(ret));

        return rslt;
    }

    //포인트 가져오기
    public void get_point(String id) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" + id + "'", null);
        while (c.moveToNext()) {
            int point = c.getInt(0);
            money_have.setText(point + "원");
            break;
        }
    }

    //카운트 가져오기
    public void get_count(String id) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT count FROM mycount WHERE user ='" + id + "'", null);
        while (c.moveToNext()) {
            int count = c.getInt(0);
            point_count.setText(count + "");
            break;
        }
    }

    //로그아웃
    public void logout() {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MainActivity.uncheck();
                        Intent i = new Intent(main_screen.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

}