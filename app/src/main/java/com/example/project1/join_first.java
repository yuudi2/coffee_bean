package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class join_first extends AppCompatActivity {

    ImageButton back;
    CheckBox every_check, service_check, card_check, locate_check, inform_check, mms_check, email_check, push_check;
    Button agree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_first);


        back = (ImageButton) findViewById(R.id.back);
        every_check = (CheckBox) findViewById(R.id.every_check);
        service_check = (CheckBox) findViewById(R.id.service_check);
        card_check = (CheckBox) findViewById(R.id.card_check);
        locate_check = (CheckBox) findViewById(R.id.locate_check);
        inform_check = (CheckBox) findViewById(R.id.inform_check);
        mms_check = (CheckBox) findViewById(R.id.mms_check);
        email_check = (CheckBox) findViewById(R.id.email_check);
        push_check = (CheckBox) findViewById(R.id.push_check);
        agree = (Button)findViewById(R.id.agree);

        every_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(every_check.isChecked()){
                    service_check.setChecked((true));
                    locate_check.setChecked((true));
                    inform_check.setChecked((true));
                    card_check.setChecked((true));
                    mms_check.setChecked((true));
                    email_check.setChecked((true));
                    push_check.setChecked((true));
                } else {
                    service_check.setChecked((false));
                    locate_check.setChecked((false));
                    inform_check.setChecked((false));
                    card_check.setChecked((false));
                    mms_check.setChecked((false));
                    email_check.setChecked((false));
                    push_check.setChecked((false));
                }
            }
        });


        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(service_check.isChecked() && inform_check.isChecked() && card_check.isChecked()){
                     startActivity(new Intent(join_first.this, join_second.class));
                } else {
                    Toast.makeText(getApplicationContext(),"필수 회원약관에 동의해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public void go_back(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}