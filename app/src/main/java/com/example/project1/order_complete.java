package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class order_complete extends AppCompatActivity {

    Button go_home, order_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        go_home = findViewById(R.id.go_home);
        order_list = findViewById(R.id.order_list);

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_screen.class);
                startActivity(intent);
            }
        });

        order_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), order_details.class);
                startActivity(intent);
            }
        });
    }
}