package com.example.leopardleap;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TourGuide_Landing extends AppCompatActivity {


    int count = 0;
    Button end_btn;
    Button start_btn;
    ImageButton count_btn;
    TextView counter;


    static String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide__landing);

        //Counter to show how many users are in the group
        counter = (TextView)findViewById(R.id.counter);
        counter.setText(getString(R.string.counter) + " 00");

        //Button labeled "End Tour"
        end_btn = (Button)findViewById(R.id.end_btn);
        //Button labeled "Begin Tour"
        start_btn = (Button)findViewById(R.id.start_btn);

        //TEMPORARY BUTTON; SHOULD INCREMENT WITH NFC IN FINAL VERSION
        count_btn = (ImageButton)findViewById(R.id.nfc);




        count_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Counter clicked");
                count++;
                if(count < 10){
                    counter.setText(getString(R.string.counter) + " 0" + count);
                }
                else {
                    counter.setText(getString(R.string.counter) + " " + count);
                }
            }
        });

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
