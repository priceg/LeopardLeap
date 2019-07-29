package com.example.leopardleap;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Visitor_Landing extends AppCompatActivity {


    VisitorConnect vc;
    TextView tester;
    String ip = "";
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


    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            ip = new String(message.getRecords()[0].getPayload());
            intent = new Intent();
            intent.setClass( Visitor_Landing.this, Visitor_Roadmap.class);
            intent.putExtra("ip address", ip);
            startActivity(intent);


        }
    }
}
