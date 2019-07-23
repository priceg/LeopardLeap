package com.example.leopardleap;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TourGuideConnect implements Runnable{
    Context context;
    ServerSocket ss;
    Socket client;
    DataInputStream din;
    BufferedReader bread;
    String message;
    Handler handler = new Handler();
    public TourGuideConnect(Context c)
    {
        context = c;
    }
    @Override
    public void run() {

        try {
            ss = new ServerSocket(10001);
            while(true)
            {
                client = ss.accept();
                din = new DataInputStream(client.getInputStream());
                message = din.readUTF();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}