package com.example.leopardleap;

import android.app.Activity;
import android.widget.ImageView;

public class VisitHandler implements Runnable {
    private String message;
    private Activity activity;
    public VisitHandler(String m, Activity a){message = m; activity = a;}
    @Override
    public void run() {
        ImageView leopard_check = activity.findViewById(R.id.check2);
        ImageView tansey_check = activity.findViewById(R.id.check3);
        ImageView beatty_check = activity.findViewById(R.id.check);
        ImageView baker_check = activity.findViewById(R.id.check4);
        ImageView watson_check = activity.findViewById(R.id.check1);
        ImageView huntington_check = activity.findViewById(R.id.check5);

        switch(message) {
            case "1001":
                leopard_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            case "1003":
                beatty_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            case "1005":
                tansey_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            case "1007":
                huntington_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            case "1009":
                baker_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            case "1011":
                watson_check.setElevation(5 * activity.getResources().getDisplayMetrics().density);
                break;
            default:
                break;
        }
    }
    public String getMessage()
    {
        return message;
    }
}
