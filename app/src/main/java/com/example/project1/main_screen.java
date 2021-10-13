package com.example.project1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class main_screen extends AppCompatActivity {

    ImageButton arrow, arrow2, menu, order2, mymenu2;
    Button order, mymenu;
    CardView cardView, cardView2;
    LinearLayout hiddenView, hiddenView2;
    RelativeLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


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
                Intent intent = new Intent(getApplicationContext(),purple_order1.class);
                startActivity(intent);
            }
        });

        order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),purple_order1.class);
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


        //회원 이름 불러오기
        String username = getIntent().getStringExtra("user_name");

        final TextView user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText(username);


        //navigation header 회원 이름
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.user_name2);
        nav_header_id_text.setText(username + "님.");



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



       final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start에 지정된 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationView.setItemIconTintList(null);

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