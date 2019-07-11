package com.example.leopardleap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button visitor_btn = (Button)findViewById(R.id.visitor_btn);
        Button guide_btn = (Button)findViewById(R.id.guide_btn);

        visitor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Visitor_Landing.class);
                startActivity(intent);
            }
        });

        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TourGuide_Landing.class);
                startActivity(intent);
            }
        });
    }
}
