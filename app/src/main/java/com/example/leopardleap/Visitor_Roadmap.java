package com.example.leopardleap;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Visitor_Roadmap extends AppCompatActivity implements View.OnClickListener {

    static String TAG = "MyApp";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor__roadmap);

        Button end_btn = (Button) findViewById(R.id.end_btn);
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Visitor_Roadmap.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mContext = getApplicationContext();
        mActivity = Visitor_Roadmap.this;

        //Necessary XML widgets (layout + buttons)
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.rm_layout);
        tansey = (Button) findViewById(R.id.button1);
        watson = (Button) findViewById(R.id.button2);
        beatty = (Button) findViewById(R.id.button3);
        leopard = (Button) findViewById(R.id.button6);
        baker = (Button) findViewById(R.id.button5);
        huntington = (Button) findViewById(R.id.button4);

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
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
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
                intent.setClass(Visitor_Roadmap.this, Directions.class);
                Bundle bundle = new Bundle();
                bundle.putString("Title", title.getText().toString());
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

}
