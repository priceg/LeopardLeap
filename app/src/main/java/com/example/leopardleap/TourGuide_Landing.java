package com.example.leopardleap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TourGuide_Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide__landing);

        //Button labeled "End Tour"
        Button end_btn = (Button)findViewById(R.id.end_btn);
        //Button labeled "Begin Tour"
        Button start_btn = (Button)findViewById(R.id.start_btn);

        /*  This will end the session and start everything over,
        requiring the Tour Guide to login again  */
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TourGuide_Landing.this, MainActivity.class);
                startActivity(intent);
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TourGuide_Landing.this, TourGuide_Roadmap.class);
                startActivity(intent);
            }
        });
    }
}
