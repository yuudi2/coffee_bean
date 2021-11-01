package com.example.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import Data.CartlistDBHelper;

public class main_screen extends AppCompatActivity {

    ImageButton arrow, arrow2, menu, order2, mymenu2, gift2;
    ImageView charge2, list2;
    Button order, mymenu, gift;
    TextView info, charge, list, money_have;
    CardView cardView, cardView2;
    LinearLayout hiddenView, hiddenView2;
    RelativeLayout layout1;

    private long backpressedTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        //메인화면에서 어플이 종료될 수 있게 로그인 엑티비티 종료
        MainActivity mactivity = (MainActivity)MainActivity.activity;
        mactivity.finish();


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

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hiddenView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_icon_down);
                }
                else{
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
                if(hiddenView2.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(cardView2, new AutoTransition());
                    hiddenView2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.drawable.ic_icon_down);
                }
                else{
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
                Intent intent = new Intent(getApplicationContext(),menu_choice.class);
                startActivity(intent);
            }
        });

        order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),menu_choice.class);
                startActivity(intent);
            }
        });

        mymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),myfav_menu.class);
                startActivity(intent);
            }
        });

        mymenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),myfav_menu.class);
                startActivity(intent);
            }
        });

        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),send_gift.class);
                startActivity(intent);
            }
        });

        gift2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),send_gift.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        SharedPreferences pref = getSharedPreferences("username", MODE_PRIVATE);

        //회원 이름 불러오기
        if(!TextUtils.isEmpty(intent.getStringExtra("user_name"))){

            String username = getIntent().getStringExtra("user_name");

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("name", username);
            editor.commit();
        }



        String username2 = pref.getString("name", "");

        final TextView user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(username2);


        //String id = getIntent().getStringExtra("user_id");
        SharedPreferences pref2 = getSharedPreferences("userid", MODE_PRIVATE);
        String id = pref2.getString("user_id", "");

        get_point(id);


        //navigation header 회원 이름
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.user_name2);

        nav_header_id_text.setText(username2 + "님.");



        //매장찾기 버튼
        ImageButton nav_header_find_store = (ImageButton) nav_header_view.findViewById(R.id.find_store);
        nav_header_find_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),find_store.class);
                startActivity(intent);
                DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
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
                logout(view);
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),my_info.class);
                startActivity(intent);
            }
        });



       final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

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
            for (int i = 0 ; i < WIDTH ; ++i)
                for (int j = 0 ; j < HEIGHT ; ++j) {
                    bitmap.setPixel(i, j, bytemap.get(i,j) ? Color.BLACK : Color.WHITE);
                }

            ImageView view = (ImageView)findViewById(R.id.barcode);
            view.setImageBitmap(bitmap);
            view.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }


        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),money_charge.class);
                startActivity(intent);
            }
        });

        charge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),money_charge.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()  > backpressedTime + 2000){
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(System.currentTimeMillis() <=  backpressedTime + 2000 ){
            finish();
        }
    }


    //포인트 가져오기
    public void get_point(String id){
        CartlistDBHelper dbHelper = new CartlistDBHelper(this);
        Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT point FROM mypoint WHERE user ='" +id + "'", null);
        while (c.moveToNext()) {
            int point = c.getInt(0);
            money_have.setText(point + "원");
            break;
        }
    }


    //로그아웃
    public void logout(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MainActivity.uncheck();
                        Intent i = new Intent(main_screen.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
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