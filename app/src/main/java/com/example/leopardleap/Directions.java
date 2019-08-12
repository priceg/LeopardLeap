package com.example.leopardleap;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Directions extends AppCompatActivity {


    static String TAG = "MyApp";

    private NfcAdapter mNfcAdapter;
    private ImageView map;
    private TextView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        header = (TextView)findViewById(R.id.route);
        map = (ImageView)findViewById(R.id.map);
        initNFC();

        Button back = (Button)findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onNfcDetected(Ndef ndef){
        header.setText(getText(R.string.route_second));
        readFromNFC(ndef);
    }


    //1001 == Leopard Statue
    //1003 == Beatty Hall
    //1005 == Tansey Gym
    //1007 == 555 Huntington
    //1009 == Baker Hall
    //1011 == Watson Auditorium
    //Location IDs in Database
    private void readFromNFC(Ndef ndef) {

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();

            //String "message" contains the contents of the NFC tag in String form
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: " + message);

            Bundle bundle = this.getIntent().getExtras();
            String title = bundle.getString("Title");
            Log.d(TAG, "Bundle received");


            switch(message) {

                //From The Leopard Statue To ______
                case "1001":
                    switch (title) {
                        case "Beatty Hall":
                            map.setImageResource(R.drawable.statue_to_beatty);
                            break;
                        case "Tansey Gym":
                            map.setImageResource(R.drawable.statue_to_gym);
                            break;
                        case "555 Huntington Ave":
                            map.setImageResource(R.drawable.statue_to_555);
                            break;
                        case "Baker Hall":
                            map.setImageResource(R.drawable.statue_to_baker);
                            break;
                        case "Watson Auditorium":
                            map.setImageResource(R.drawable.statue_to_aud);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;

                //From Beatty Hall To ______
                case "1003":
                    switch (title) {
                        case "The Leopard Statue":
                            map.setImageResource(R.drawable.beatty_to_statue);
                            break;
                        case "Tansey Gym":
                            map.setImageResource(R.drawable.beatty_to_gym);
                            break;
                        case "555 Huntington Ave":
                            map.setImageResource(R.drawable.beatty_to_555);
                            break;
                        case "Baker Hall":
                            map.setImageResource(R.drawable.beatty_to_baker);
                            break;
                        case "Watson Auditorium":
                            map.setImageResource(R.drawable.beatty_to_aud);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;

                //From Tansey Gym To ______
                case "1005":
                    switch (title) {
                        case "The Leopard Statue":
                            map.setImageResource(R.drawable.gym_to_statue);
                            break;
                        case "Beatty Hall":
                            map.setImageResource(R.drawable.gym_to_beatty);
                            break;
                        case "555 Huntington Ave":
                            map.setImageResource(R.drawable.gym_to_555);
                            break;
                        case "Baker Hall":
                            map.setImageResource(R.drawable.gym_to_baker);
                            break;
                        case "Watson Auditorium":
                            map.setImageResource(R.drawable.gym_to_aud);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;

                //From 555 Huntington Ave To ______
                case "1007":
                    switch (title) {
                        case "The Leopard Statue":
                            map.setImageResource(R.drawable._555_to_statue);
                            break;
                        case "Beatty Hall":
                            map.setImageResource(R.drawable._555_to_beatty);
                            break;
                        case "Tansey Gym":
                            map.setImageResource(R.drawable._555_to_gym);
                            break;
                        case "Baker Hall":
                            map.setImageResource(R.drawable._555_to_baker);
                            break;
                        case "Watson Auditorium":
                            map.setImageResource(R.drawable._555_to_aud);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;

                //From Baker Hall To ______
                case "1009":
                    switch (title) {
                        case "The Leopard Statue":
                            map.setImageResource(R.drawable.baker_to_statue);
                            break;
                        case "Beatty Hall":
                            map.setImageResource(R.drawable.baker_to_beatty);
                            break;
                        case "Tansey Gym":
                            map.setImageResource(R.drawable.baker_to_gym);
                            break;
                        case "555 Huntington Ave":
                            map.setImageResource(R.drawable.baker_to_555);
                            break;
                        case "Watson Auditorium":
                            map.setImageResource(R.drawable.baker_to_aud);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;

                //From Watson Auditorium To ______
                case "1011":
                    switch (title) {
                        case "The Leopard Statue":
                            map.setImageResource(R.drawable.aud_to_statue);
                            break;
                        case "Beatty Hall":
                            map.setImageResource(R.drawable.aud_to_beatty);
                            break;
                        case "Tansey Gym":
                            map.setImageResource(R.drawable.aud_to_gym);
                            break;
                        case "555 Huntington Ave":
                            map.setImageResource(R.drawable.aud_to_555);
                            break;
                        case "Baker Hall":
                            map.setImageResource(R.drawable.aud_to_baker);
                            break;
                        default:
                            header.setText(getText(R.string.here));
                            map.setImageResource(R.drawable.map);
                            break;
                    }
                    break;
                default:
                    break;
            }
            Log.d(TAG, "Image set");
            ndef.close();

        } catch (IOException | FormatException e) {
            Log.d(TAG, "BIG ERROR");
            e.printStackTrace();

        }
    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {

        Log.d(TAG, "onResume entered");
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            Log.d(TAG, "DIRECTIONS: enableForegroundDispatch");
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause entered");
        super.onPause();
        if(mNfcAdapter!= null)
            Log.d(TAG, "Directions disabled");
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d(TAG, "onNewIntent: "+intent.getAction());

        if(tag != null) {
            Toast.makeText(this, getString(R.string.tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);
            onNfcDetected(ndef);
        }
    }
}
