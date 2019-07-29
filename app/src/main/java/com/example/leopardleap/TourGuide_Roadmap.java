package com.example.leopardleap;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.ConstantCallSite;

public class TourGuide_Roadmap extends AppCompatActivity implements View.OnClickListener {

    static String TAG = "MyApp";

    private ImageView leopard_check;
    private ImageView beatty_check;
    private ImageView tansey_check;
    private ImageView huntington_check;
    private ImageView baker_check;
    private ImageView watson_check;


    //Buttons for checkpoints along Roadmap
    private Button tansey;
    private Button watson;
    private Button beatty;
    private Button leopard;
    private Button baker;
    private Button huntington;

    //Popup Window Related
    private PopupWindow mPopupWindow;
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout mConstraintLayout;

    private NfcAdapter mNfcAdapter;


    private TourGuideConnect server;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide__roadmap);

        initNFC();
        LeopardLeap map_context = ((LeopardLeap)getApplicationContext());
        server = map_context.getServer();
        server.setContext(getApplicationContext());
        Handler handler = new Handler();
        server.setHandler(handler);

        Button end_btn = (Button) findViewById(R.id.end_btn);
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TourGuide_Roadmap.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mContext = getApplicationContext();
        mActivity = TourGuide_Roadmap.this;

        //Necessary XML widgets (layout + buttons)
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.rm_layout);
        tansey = (Button) findViewById(R.id.button1);
        watson = (Button) findViewById(R.id.button2);
        beatty = (Button) findViewById(R.id.button3);
        leopard = (Button) findViewById(R.id.button6);
        baker = (Button) findViewById(R.id.button5);
        huntington = (Button) findViewById(R.id.button4);

        leopard_check = findViewById(R.id.check2);
        tansey_check = findViewById(R.id.check3);
        beatty_check = findViewById(R.id.check);
        baker_check = findViewById(R.id.check4);
        watson_check = findViewById(R.id.check1);
        huntington_check = findViewById(R.id.check5);

        Log.v(TAG, "Button1 is clicked");

        //Series of click listeners for each button, calls default onClick which contains switch
        tansey.setOnClickListener(this);
        watson.setOnClickListener(this);
        beatty.setOnClickListener(this);
        leopard.setOnClickListener(this);
        baker.setOnClickListener(this);
        huntington.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        Log.v(TAG, "Layout is inflated");
        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.custom_layout, null);

        // Initialize a new instance of the popup window
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
            Log.v(TAG, "Elevation set");
        }


        // Get references for the view elements in the popup (title, picture, blurb, 2 buttons)
        final ImageButton closeButton = (ImageButton) customView.findViewById(R.id.close);
        final TextView title = (TextView) customView.findViewById(R.id.title);
        final ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        final TextView blurb = (TextView) customView.findViewById(R.id.blurb);
        final Button directions = (Button) customView.findViewById(R.id.directions);


        // Set custom click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
                Log.v(TAG, "Window closed");
            }
        });

        //Set custom click listener for the "Get Directions" button
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TourGuide_Roadmap.this, Directions.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", title.toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Switch for each case for buttons, populates new popup instance with corresponding info
        switch (v.getId()) {

            case R.id.button1:
                title.setText("Tansey Gym");
                blurb.setText(getString(R.string.tansey_blurb));
                picture.setImageResource(R.drawable.tansey);
                Log.v(TAG, "Current Title: " + title.getText());
                break;

            case R.id.button2:
                title.setText("Watson Auditorium");
                blurb.setText(getString(R.string.watson_blurb));
                picture.setImageResource(R.drawable.wattson);
                Log.v(TAG, "Current Title: " + title.getText());
                break;

            case R.id.button3:
                title.setText("Beatty Hall");
                blurb.setText(getString(R.string.beatty_blurb));
                picture.setImageResource(R.drawable.beatty);
                Log.v(TAG, "Current Title: " + title.getText());
                break;
            case R.id.button4:
                title.setText("555 Huntington Ave");
                blurb.setText(getString(R.string.huntington_blurb));
                picture.setImageResource(R.drawable.huntington);
                Log.v(TAG, "Current Title: " + title.getText());
                break;

            case R.id.button5:
                title.setText("Baker Hall");
                blurb.setText(getString(R.string.baker_blurb));
                picture.setImageResource(R.drawable.baker);
                Log.v(TAG, "Current Title: " + title.getText());
                break;

            case R.id.button6:
                title.setText("The Leopard Statue");
                blurb.setText(getString(R.string.leopard_blurb));
                picture.setImageResource(R.drawable.leopard_statue);
                Log.v(TAG, "Current Title: " + title.getText());
                break;

            default:
                break;
        }

        //Show the popup window with title, image, blurb, etc
        mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER, 0, 0);
        Log.v(TAG, "It's here it's here");
    }

    public void onNfcDetected(Ndef ndef){
        readFromNFC(ndef);
    }

    private void readFromNFC(Ndef ndef) {

        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();

            //String "message" contains the contents of the NFC tag in String form
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: " + message);

            switch(message) {
                case "1001":
                    leopard_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                case "1003":
                    beatty_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                case "1005":
                    tansey_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                case "1007":
                    huntington_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                case "1009":
                    baker_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                case "1011":
                    watson_check.setElevation(5 * this.getResources().getDisplayMetrics().density);
                    break;
                default:
                    break;
            }

            sendMessage(message);

            ndef.close();


        } catch (IOException | FormatException e) {
            e.printStackTrace();

        }
    }


    private void sendMessage(String c)
    {
        server.getCommThread().sendMessage(message);
    }

    private void initNFC(){

            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }

        @Override
        protected void onResume() {
            super.onResume();
            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
            IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            if(mNfcAdapter!= null)
                mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);
        }

        @Override
        protected void onPause() {
            super.onPause();
            if(mNfcAdapter!= null)
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