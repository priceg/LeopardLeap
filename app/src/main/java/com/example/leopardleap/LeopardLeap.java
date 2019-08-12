package com.example.leopardleap;

import android.app.Application;

import com.example.leopardleap.TourGuideConnect;

public class LeopardLeap extends Application {
    private TourGuideConnect server;


    public void setServer(TourGuideConnect tc)
    {
        server = tc;
    }

    public void startServer()
    {
        Thread thread = new Thread(server);
        thread.start();
    }

    public TourGuideConnect getServer()
    {
        return server;
    }
}
