package com.example.project1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.MyfavViewAdapter;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class myfav_menu extends AppCompatActivity {

    public static Context con;

    ImageButton shopping_bag, add_favmenu, delete_myfav;
    Button menu_regis;
    Button select_store;
    TextView store_select_name;

    int fav_img;
    String fav_name = "";

    private SQLiteDatabase mDb3;
    public RecyclerView recyclerView;
    public MyfavViewAdapter adapter;
    RelativeLayout emptyView, emptyView2;
    Cursor cur;
    String s_store = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfav_menu);

        con = this;


        setContentView(R.layout.activity_myfav_menu);

        shopping_bag = findViewById(R.id.shopping_bag);
        add_favmenu = findViewById(R.id.add_favmenu);
        delete_myfav = findViewById(R.id.delete_myfav);
        menu_regis = findViewById(R.id.menu_regis);
        recyclerView = findViewById(R.id.recyclerView_cart);
        store_select_name = findViewById(R.id.store_select_name);


        emptyView = findViewById(R.id.empty_view);
        emptyView2 = findViewById(R.id.empty_view2);


        Intent intent = getIntent();
        SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);

        if (!TextUtils.isEmpty(intent.getStringExtra("name"))) {

            s_store = getIntent().getExtras().getString("name");

            SharedPreferences.Editor editor = pref.edit();

            editor.putString("key", s_store);

            editor.commit();
        }


        String result = pref.getString("key", "");
        if (result.equals("")) {
            store_select_name.setText("매장을 선택해 주세요.");
        } else {
            store_select_name.setText(result + "으로 주문합니다.");
        }


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


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
        adapter.swapCursor(getAllGuests());


        viewvisible();


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

        delete_myfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myfav_menu.this);
                builder.setTitle("상품삭제");
                builder.setMessage("전체 목록을 삭제하시겠습니까?");


                //  setPositiveButton -> "OK"버튼
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAll(mDb3);
                        adapter.swapCursor(getAllGuests());

                        visible2();
                    }
                });

                //  setNegativeButton -> "Cancel" 버튼  //
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();

            }
        });


        select_store = findViewById(R.id.select_store);
        select_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), select_store.class);

                intent.putExtra("activity", "fav");

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
        intToByte[0] |= (byte) ((a & 0xFF000000) >> 24);
        intToByte[1] |= (byte) ((a & 0xFF0000) >> 16);
        intToByte[2] |= (byte) ((a & 0xFF00) >> 8);
        intToByte[3] |= (byte) (a & 0xFF);
        return intToByte;

    }

    public void update() {
        Cursor cursor = getAllGuests();
        adapter = new MyfavViewAdapter(this, cursor);
        adapter.notifyDataSetChanged();
        recyclerView = findViewById(R.id.recyclerView_cart);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.swapCursor(getAllGuests());
    }

    public void viewvisible() {
        //장바구니 비었는지 확인
        if (adapter.getItemCount() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyView2.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.swapCursor(getAllGuests());
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView2.setVisibility(View.VISIBLE);
        }
    }

    public void visible1() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        emptyView2.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.swapCursor(getAllGuests());
    }

    public void visible2() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView2.setVisibility(View.VISIBLE);

    }

    public int count() {
        int cnt = 0;
        Cursor cursor = mDb3.rawQuery("SELECT * FROM myfavlist", null);
        cnt = cursor.getCount();
        return cnt;
    }


    public void deleteAll(SQLiteDatabase database) {
        database.execSQL("DELETE FROM myfavlist");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), main_screen.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}