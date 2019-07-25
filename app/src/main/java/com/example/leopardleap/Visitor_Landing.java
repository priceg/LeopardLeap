package com.example.leopardleap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Visitor_Landing extends AppCompatActivity {

    VisitorConnect vc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor__landing);

        Button end_btn = (Button)findViewById(R.id.end_btn);
        ImageButton temp = (ImageButton)findViewById(R.id.nfc);

        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass( Visitor_Landing.this, MainActivity.class);
                startActivity(intent);
            }
        });

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass( Visitor_Landing.this, Visitor_Roadmap.class);
                startActivity(intent);
            }
        });



    }

    protected void connect(View v)
    {
        vc = new VisitorConnect(getApplicationContext());
        vc.execute("192.168.1.207", "The connection works");
    }

    protected void quit(View v)
    {
        vc.disconnect();
    }
}
