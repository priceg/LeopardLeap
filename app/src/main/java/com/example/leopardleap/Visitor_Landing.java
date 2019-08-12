package com.example.leopardleap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Visitor_Landing extends AppCompatActivity {

    static String TAG = "MyApp";

    private NfcAdapter mNfcAdapter;
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

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);



    }


    /**protected void onResume(){
        super.onResume();
        Intent intent = getIntent();

    }*/

    @Override
    protected void onResume() {

        Log.v(TAG, "onResume entered");
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndefDetected.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            Log.d(TAG, "DIRECTIONS: enableForegroundDispatch");
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause entered for Visitor");
        super.onPause();
        if(mNfcAdapter!= null)
            Log.d(TAG, "Directions disabled");
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: "+intent.getAction());
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            ip = new String(message.getRecords()[0].getPayload());
            intent = new Intent();
            intent.setClass( Visitor_Landing.this, Visitor_Roadmap.class);
            intent.putExtra("ip address", ip);
            Log.v("GP2","Starting new activity");
            startActivity(intent);


        }
    }

    protected void handleNFC(Ndef ndef)
    {
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();

            //String "message" contains the contents of the NFC tag in String form
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: " + message);

            Bundle bundle = this.getIntent().getExtras();
            String title = bundle.getString("Title");
             Log.d(TAG, "Bundle received");

             //insert stuff to do here
            ip = message;

            ndef.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (FormatException e)
        {
            e.printStackTrace();
        }
    }


}
