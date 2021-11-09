package com.example.project1;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.CartlistContract;
import Data.CartlistDBHelper;

public class select_menu_cake extends AppCompatActivity {

    ImageView cake_img;
    TextView cake_name, cake_price;
    TextView t_count, order_price;
    ImageButton minus, plus, mymenu;
    int count = 1;
    int total_count = count;
    Button shopping_cart, order_now;

    private Boolean mymenu_change = false;

    String ca_name = "";
    int ca_price;
    int ca_img;
    String size = null;
    String cup = null;
    String cream = null;

    int change_price;

    int code;

    private SQLiteDatabase mDb;
    private SQLiteDatabase mDb3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu_cake);

        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mDb3 = dbHelper.getWritableDatabase();

        //coffee 정보 불러오기
        cake_img = findViewById(R.id.coffe_img);
        cake_name = findViewById(R.id.coffee_name);
        cake_price = findViewById(R.id.coffee_price);

        ca_img = getIntent().getExtras().getInt("img");
        ca_name = getIntent().getExtras().getString("name");
        ca_price = getIntent().getExtras().getInt("price");

        cake_img.setImageResource(ca_img);
        cake_name.setText(ca_name);
        cake_price.setText(String.valueOf(ca_price)+"원");

        change_price = ca_price;

        code = getIntent().getExtras().getInt("code");


        //주문금액
        order_price = findViewById(R.id.order_price);
        order_price.setText(String.valueOf(ca_price)+"원");


        //수량 선택
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        t_count = findViewById(R.id.count);
        t_count.setText(count+"");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                t_count.setText(count+"");
                order_price.setText(String.valueOf(ca_price * count)+"원");
                total_count = count;
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count--;
                    t_count.setText(count+"");
                    order_price.setText(String.valueOf(ca_price * count)+"원");
                    total_count = count;
                }
            }
        });


        //장바구니
        shopping_cart = findViewById(R.id.shopping_cart);

        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(select_menu_cake.this);
                builder.setTitle("장바구니로 이동");
                builder.setMessage("장바구니로 이동하시겠습니까?");


                //  setPositiveButton -> "OK"버튼
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(view.getContext(), shopping_cart.class);

                        intent.putExtra("img", ca_img);
                        intent.putExtra("name", cake_name.getText());
                        intent.putExtra("price", change_price);
                        intent.putExtra("size", size);
                        intent.putExtra("cup", cup);
                        intent.putExtra("cream", cream);
                        intent.putExtra("count", total_count);
                        intent.putExtra("total_price", count * change_price );
                        finish();
                        view.getContext().startActivity(intent);


                        Toast.makeText(select_menu_cake.this, "확인 버튼을 눌렀습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                //  setNegativeButton -> "Cancel" 버튼  //
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        byte[] img_b = intToByte(ca_img);
                        addNewCart(img_b, cake_name.getText().toString(), change_price, size, cup, cream, total_count, count * change_price);

                        Toast.makeText(select_menu_cake.this, "취소 버튼을 눌렀습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();      //대화상자(dialog)화면 출력


            }
        });


        //마이메뉴
        mymenu = findViewById(R.id.mymenu);

        namecheck(ca_name);

        mymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mymenu_change == false){
                    mymenu.setImageResource(R.drawable.ic_icon_starfull);
                    byte[] img_b = intToByte(ca_img);
                    addNewFav(img_b, ca_name, ca_price);
                    if(code != 1) {
                        ((myfav_menu) myfav_menu.con).update();
                        if (count() > 0) {
                            ((myfav_menu) myfav_menu.con).visible1();
                        }
                    }
                    mymenu_change = true;
                }

                else{
                    mymenu.setImageResource(R.drawable.ic_icon_star2);
                    deleteFav(ca_name);
                    if(code != 1) {
                        ((myfav_menu) myfav_menu.con).update();
                        if (count() == 0) {
                            ((myfav_menu) myfav_menu.con).visible2();
                        }
                    }
                    mymenu_change =false;
                }
            }
        });

        //주문하기 버튼
        order_now = findViewById(R.id.order_now);

        order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("store", MODE_PRIVATE);
                String name = pref.getString("key", "");

                if(name.equals("")){
                    Toast.makeText(getApplicationContext(), "매장을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), select_store.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), menu_order_now.class);

                    intent.putExtra("type", "케익");
                    intent.putExtra("name", cake_name.getText());
                    intent.putExtra("count", total_count);
                    intent.putExtra("total_price", count * change_price);

                    startActivity(intent);
                }
            }
        });

    }

    public static byte[] intToByte(int a) {
        byte[] intToByte = new byte[4];
        intToByte[0] |= (byte)((a&0xFF000000)>>24);
        intToByte[1] |= (byte)((a&0xFF0000)>>16);
        intToByte[2] |= (byte)((a&0xFF00)>>8);
        intToByte[3] |= (byte)(a&0xFF);
        return intToByte;

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


    public void addNewFav(byte[] img, String name, int pirce) {
        // DB에 데이터를 추가를 하기 위해선 ContentValue 객체를 사용해야 한다.
        ContentValues cv = new ContentValues();
        /*
         * 열의 이름을 키로 해서 해당 값을 가리킨다.
         * 값들을 put 메서드를 사용해 입력한다.
         * 첫번째 파라미터는 열의 이름으로, Contract 로부터 가져올 수 있다.
         * 두번째 파라미터는 값이다.
         */

        cv.put(CartlistContract.MyfavlistEntry.COLUMN_IMG, img);
        cv.put(CartlistContract.MyfavlistEntry.COLUMN_NAME, name);
        cv.put(CartlistContract.CartlistEntry.COLUMN_PRICE, pirce);


        // cv에 저장된 값을 사용하여 새로운 행을 추가한다.
        mDb3.insert(CartlistContract.MyfavlistEntry.TABLE_NAME, null, cv);
    }


    //찜 목록에 해당 데이터가 있는지 확인
    private void namecheck(String favname) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT name FROM myfavlist", null);
        mymenu_change = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(favname)) {
                mymenu_change = true;
                mymenu.setImageResource(R.drawable.ic_icon_starfull);
                break;
            }
        }

    }


    public void deleteFav(String name) {
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb3 = dbHelper.getWritableDatabase();
        mDb3.execSQL("DELETE FROM myfavlist WHERE name = '" + name + "'");
        //mDb.close();
    }


    public int count(){
        int cnt = 0;
        Cursor cursor = mDb3.rawQuery("SELECT * FROM myfavlist", null);
        cnt = cursor.getCount();
        return cnt;
    }


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}