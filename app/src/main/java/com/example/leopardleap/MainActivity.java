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

        //Button labeled "Visitor"
        Button visitor_btn = (Button)findViewById(R.id.visitor_btn);
        //Button labeled "Tour Guide"
        Button guide_btn = (Button)findViewById(R.id.guide_btn);

        //This will take the user down the "Visitor" path; no login required
        visitor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Visitor_Landing.class);
                startActivity(intent);
            }
        });

        //This will take the user down the "Tour Guide" path; login required
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
