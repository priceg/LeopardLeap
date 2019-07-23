package com.example.leopardleap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import org.w3c.dom.Text;

import java.lang.invoke.ConstantCallSite;

public class TourGuide_Roadmap extends AppCompatActivity implements View.OnClickListener{

    static String TAG = "MyApp";

    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;
    private Button tansey;
    private Button wattson;
    private Button beatty;
    private Button leopard;
    private Button baker;
    private Button huntington;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide__roadmap);

        Button end_btn = (Button) findViewById(R.id.end_btn);
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TourGuide_Roadmap.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = TourGuide_Roadmap.this;

        // Get the widgets reference from XML layout
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.rm_layout);
        tansey = (Button) findViewById(R.id.button1);
        wattson = (Button) findViewById(R.id.button2);
        beatty = (Button) findViewById(R.id.button3);
        leopard = (Button) findViewById(R.id.button6);
        baker = (Button) findViewById(R.id.button5);
        huntington = (Button) findViewById(R.id.button4);

        Log.v(TAG, "Button1 is clicked");

        // Set a click listener for the text view
        tansey.setOnClickListener(this);
        wattson.setOnClickListener(this);
        beatty.setOnClickListener(this);
        leopard.setOnClickListener(this);
        baker.setOnClickListener(this);
        huntington.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        Log.v(TAG, "Layout is inflated");
        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.custom_layout,null);

        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
            Log.v(TAG, "Elevation set");
        }


        // Get a reference for the custom view elements
        final ImageButton closeButton = (ImageButton) customView.findViewById(R.id.close);
        final TextView title = (TextView)customView.findViewById(R.id.title);
        final ImageView picture = (ImageView) customView.findViewById(R.id.picture);
        final TextView blurb = (TextView) customView.findViewById(R.id.blurb);
        final Button directions = (Button) customView.findViewById(R.id.directions);


        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
                Log.v(TAG, "Window closed");
            }
        });

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass( TourGuide_Roadmap.this, Directions.class);
                startActivity(intent);
            }
        });

        switch (v.getId()) {

            case R.id.button1:
                title.setText("Tansey Gym");
                blurb.setText(getString(R.string.tansey_blurb));
                picture.setImageResource(R.drawable.tansey);
                break;

            case R.id.button2:
                title.setText("Wattson Auditorium");
                blurb.setText(getString(R.string.wattson_blurb));
                picture.setImageResource(R.drawable.wattson);
                break;

            case R.id.button3:
                title.setText("Beatty Hall");
                blurb.setText(getString(R.string.beatty_blurb));
                picture.setImageResource(R.drawable.beatty);
                break;
            case R.id.button4:
                title.setText("555 Huntington Ave");
                blurb.setText(getString(R.string.huntington_blurb));
                picture.setImageResource(R.drawable.huntington);
                break;

            case R.id.button5:
                title.setText("Baker Hall");
                blurb.setText(getString(R.string.baker_blurb));
                picture.setImageResource(R.drawable.baker);
                break;

            case R.id.button6:
                title.setText("The Leopard Statue");
                blurb.setText(getString(R.string.leopard_blurb));
                picture.setImageResource(R.drawable.leopard_statue);
                break;

            default:
                break;
        }

        //Show the popup window with title, image, blurb, etc
        mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER,0,0);
        Log.v(TAG, "It's here it's here");
    }
}
