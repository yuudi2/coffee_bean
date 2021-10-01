package com.example.project1;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Data.CartlistContract;
import Data.CartlistDBHelper;
import Data.CoffeeData;

public class select_menu extends AppCompatActivity {

    ImageView coffee_img;
    TextView coffee_name, coffee_price;
    TextView t_count, order_price;
    ImageButton minus, plus;
    int count = 1;
    int total_count = count;
    Button small, regular, large, mugcup, oneusecup, cream_yes, cream_no, shopping_cart, order_now;

    String size = "";
    String cup = "";
    String cream = "";

    String c_name = "";
    int c_price;
    int c_img;

    int change_price;

    PreferenceManager pref;

    ArrayList<CoffeeData> itemlist = new ArrayList<CoffeeData>();

    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);


        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        //coffee 정보 불러오기
        coffee_img = findViewById(R.id.coffe_img);
        coffee_name = findViewById(R.id.coffee_name);
        coffee_price = findViewById(R.id.coffee_price);

        c_img = getIntent().getExtras().getInt("img");
        c_name = getIntent().getExtras().getString("name");
        c_price = getIntent().getExtras().getInt("price");

        coffee_img.setImageResource(c_img);
        coffee_name.setText(c_name + "(S)");
        coffee_price.setText(String.valueOf(c_price) + "원");

        change_price = c_price;


        //주문금액
        order_price = findViewById(R.id.order_price);
        order_price.setText(String.valueOf(c_price) + "원");


        //수량 선택
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        t_count = findViewById(R.id.count);
        t_count.setText(count + "");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                t_count.setText(count + "");
                order_price.setText(String.valueOf(change_price * count) + "원");
                total_count = count;
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    count--;
                    t_count.setText(count + "");
                    order_price.setText(String.valueOf(change_price * count) + "원");
                    total_count = count;
                }
            }
        });


        //사이즈 선택
        small = findViewById(R.id.size_small);
        regular = findViewById(R.id.size_regular);
        large = findViewById(R.id.size_large);

        //사이즈 디폴트값
        small.setSelected(true);
        size = small.getText().toString();

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                small.setBackgroundResource(R.drawable.btn_color3);
                small.setTextColor(Color.parseColor("#754fa0"));
                regular.setBackgroundResource(R.drawable.btn_color5);
                regular.setTextColor(Color.parseColor("#aaaaaa"));
                large.setBackgroundResource(R.drawable.btn_color5);
                large.setTextColor(Color.parseColor("#aaaaaa"));

                regular.setSelected(false);
                large.setSelected(false);

                coffee_name.setText(c_name + "(S)");

                change_price = c_price;
                order_price.setText(String.valueOf(change_price * count) + "원");
                coffee_price.setText(String.valueOf(change_price) + "원");

                size = small.getText().toString();

            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                small.setBackgroundResource(R.drawable.btn_color5);
                small.setTextColor(Color.parseColor("#aaaaaa"));
                regular.setBackgroundResource(R.drawable.btn_color3);
                regular.setTextColor(Color.parseColor("#754fa0"));
                large.setBackgroundResource(R.drawable.btn_color5);
                large.setTextColor(Color.parseColor("#aaaaaa"));

                small.setSelected(false);
                large.setSelected(false);

                coffee_name.setText(c_name + "(R)");

                change_price = c_price + 500;
                order_price.setText(String.valueOf(change_price * count) + "원");
                coffee_price.setText(String.valueOf(change_price) + "원");

                size = regular.getText().toString();
            }
        });

        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regular.setBackgroundResource(R.drawable.btn_color5);
                regular.setTextColor(Color.parseColor("#aaaaaa"));
                small.setBackgroundResource(R.drawable.btn_color5);
                small.setTextColor(Color.parseColor("#aaaaaa"));
                large.setBackgroundResource(R.drawable.btn_color3);
                large.setTextColor(Color.parseColor("#754fa0"));

                regular.setSelected(false);
                small.setSelected(false);

                coffee_name.setText(c_name + "(L)");

                change_price = c_price + 1000;
                order_price.setText(String.valueOf(change_price * count) + "원");
                coffee_price.setText(String.valueOf(change_price) + "원");

                size = large.getText().toString();
            }
        });

        //컵 선택
        mugcup = findViewById(R.id.mugcup);
        oneusecup = findViewById(R.id.oneusecup);

        mugcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mugcup.setBackgroundResource(R.drawable.btn_color3);
                mugcup.setTextColor(Color.parseColor("#754fa0"));
                oneusecup.setBackgroundResource(R.drawable.btn_color5);
                oneusecup.setTextColor(Color.parseColor("#aaaaaa"));

                oneusecup.setSelected(false);
                mugcup.setSelected(true);

                cup = mugcup.getText().toString();
            }
        });

        oneusecup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneusecup.setBackgroundResource(R.drawable.btn_color3);
                oneusecup.setTextColor(Color.parseColor("#754fa0"));
                mugcup.setBackgroundResource(R.drawable.btn_color5);
                mugcup.setTextColor(Color.parseColor("#aaaaaa"));

                mugcup.setSelected(false);
                oneusecup.setSelected(true);

                cup = oneusecup.getText().toString();
            }
        });


        //휘핑크림 선택
        cream_no = findViewById(R.id.cream_no);
        cream_yes = findViewById(R.id.cream_yes);

        cream_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cream_no.setBackgroundResource(R.drawable.btn_color3);
                cream_no.setTextColor(Color.parseColor("#754fa0"));
                cream_yes.setBackgroundResource(R.drawable.btn_color5);
                cream_yes.setTextColor(Color.parseColor("#aaaaaa"));

                cream_yes.setSelected(false);
                cream_no.setSelected(true);
                cream_no.getText();
                cream = null;
            }
        });

        cream_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cream_yes.setBackgroundResource(R.drawable.btn_color3);
                cream_yes.setTextColor(Color.parseColor("#754fa0"));
                cream_no.setBackgroundResource(R.drawable.btn_color5);
                cream_no.setTextColor(Color.parseColor("#aaaaaa"));

                cream_no.setSelected(false);
                cream_yes.setSelected(true);

                cream = "휘핑";
            }
        });


        //장바구니
        shopping_cart = findViewById(R.id.shopping_cart);
        pref = new PreferenceManager();

        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cream_no.isSelected() == true || cream_yes.isSelected() == true) {
                    if (oneusecup.isSelected() == true || mugcup.isSelected() == true) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(select_menu.this);
                        builder.setTitle("장바구니로 이동");
                        builder.setMessage("장바구니로 이동하시겠습니까?");


                        //  setPositiveButton -> "OK"버튼
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(view.getContext(), shopping_cart.class);

                                intent.putExtra("img", c_img);
                                intent.putExtra("name", coffee_name.getText());
                                intent.putExtra("price", change_price);
                                intent.putExtra("size", size);
                                intent.putExtra("cup", cup);
                                intent.putExtra("cream", cream);
                                intent.putExtra("count", total_count);
                                intent.putExtra("total_price", count * change_price);
                                setResult(RESULT_OK, intent);
                                finish();
                                view.getContext().startActivity(intent);

                            }
                        });

                        //  setNegativeButton -> "Cancel" 버튼  //
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                byte[] img_b = intToByte(c_img);
                                addNewCart(img_b, coffee_name.getText().toString(), change_price, size, cup, cream, total_count, count * change_price);

                            }
                        });

                        builder.show();      //대화상자(dialog)화면 출력


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(select_menu.this);
                        builder.setTitle("선택 필요");
                        builder.setMessage("선택되지 않은 항목이 있습니다.");

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(select_menu.this);
                    builder.setTitle("선택 필요");
                    builder.setMessage("선택되지 않은 항목이 있습니다.");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }


            }
        });


        //주문하기 버튼
        order_now = findViewById(R.id.order_now);

        order_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), change_price + count + "," + size + "," + cup + "," + cream, Toast.LENGTH_SHORT).show();


            }
        });

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


    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), menu_choice.class);
        startActivity(intent);
    }
}