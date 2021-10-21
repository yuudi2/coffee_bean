package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class receive_gift extends AppCompatActivity {

    ImageButton add_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_gift);

        add_coupon = findViewById(R.id.add_coupon);

        add_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),register_coupon.class);
                startActivity(intent);
            }
        });
    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), send_gift.class);
        startActivity(intent);
    }
}