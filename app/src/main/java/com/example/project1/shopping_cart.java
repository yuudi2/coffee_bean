package com.example.project1;

import android.app.AlertDialog;
import android.content.ContentValues;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.CartViewAdapter;
import Data.CartData;
import Data.CartlistContract;
import Data.CartlistDBHelper;

public class shopping_cart extends AppCompatActivity {

    public static Context context;

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
    CartViewAdapter.ViewHolder viewHolder;
    private CartViewAdapter adapter;
    PreferenceManager pref;
    Cursor cur;

    //private ArrayList<CartData> cartlist;

    ArrayList<CartData> cartlist = new ArrayList<>();

    ImageButton delete_menu, delete_cart;
    TextView final_order_price;
    RelativeLayout emptyView;
    Button add_menu, final_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_shopping_cart);


        delete_menu = findViewById(R.id.delete_menu);
        delete_cart = findViewById(R.id.delete_cart);

        add_menu = findViewById(R.id.add_menu);
        final_order = findViewById(R.id.final_order);
        final_order_price = findViewById(R.id.final_order_price);

        recyclerView = findViewById(R.id.recyclerView_cart);
        emptyView = findViewById(R.id.empty_view);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        // 데이터를 DB에 채우기 위함
        mDb = dbHelper.getWritableDatabase();
        //커서에 결과를 저장
        Cursor cursor = getAllGuests();


        // 데이터를 표시할 커서를 위한 어댑터 생성
        adapter = new CartViewAdapter(this, cursor);
        // 리사이클러뷰에 어댑터를 연결
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();



        Intent intent = getIntent();

        //장바구니에 담지않고 바로 장바구니화면으로 intent 했을때 null값 확인
        if(!TextUtils.isEmpty(intent.getStringExtra("name"))){

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

            addNewCart(img_b, cart_name, cart_price, cart_size, cart_cup, cart_cream, cart_count, cart_total_price);

            // 어댑터에서 커서를 업데이트하여 UI를 트리거하여 새 목록을 표시한다.
            adapter.swapCursor(getAllGuests());



        } else {
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }



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


        //전체삭제
        delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(shopping_cart.this);
                builder.setTitle("상품삭제");
                builder.setMessage("전체 상품을 삭제하시겠습니까?");


                //  setPositiveButton -> "OK"버튼
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAll(mDb);
                        adapter.swapCursor(getAllGuests());

                        //전체 금액
                        final_order_price.setText("0원");

                        //장바구니 비었음
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
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


        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menu_choice.class);
                startActivity(intent);
            }
        });


        //전체 금액
        String totalsum = "SELECT SUM(total_price) FROM cartlist";
        cur = mDb.rawQuery(totalsum, null);
        cur.moveToNext();
        String sum = String.valueOf(cur.getInt(0));
        final_order_price.setText(sum + " 원");


        final_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);
                String name = pref.getString("key", "");

                if(name.equals("")){
                    Toast.makeText(getApplicationContext(), "매장을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), select_store.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), menu_order.class);
                    startActivity(intent);
                }
            }
        });
    }


    public Cursor getAllGuests() {
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

    public void addNewCart(byte[] img, String name, int pirce, String size, String cup, String cream, int count, int total_price) {
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
        cv.put(CartlistContract.CartlistEntry.COLUMN_CREAM, cream);
        cv.put(CartlistContract.CartlistEntry.COLUMN_COUNT, count);
        cv.put(CartlistContract.CartlistEntry.COLUMN_TOTAL_PRICE, total_price);

        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb.insert(CartlistContract.CartlistEntry.TABLE_NAME, null, cv);
    }

    public void price_update(){
        //전체 금액
        String totalsum = "SELECT SUM(total_price) FROM cartlist";
        cur = mDb.rawQuery(totalsum, null);
        cur.moveToNext();
        String sum = String.valueOf(cur.getInt(0));
        final_order_price.setText(sum + " 원");
    }


    //장바구니 비었는지 확인
    public void emptycheck(){
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
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

    public void deleteAll(SQLiteDatabase database) {
        database.execSQL("DELETE FROM cartlist");
    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}
