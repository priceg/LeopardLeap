package com.example.leopardleap;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TourGuideConnect implements Runnable{
    public static final int SERVER_PORT = 10001;
    private CommunicationThread commThread;
    ServerSocket serverSocket;
    Context context;
    ServerSocket ss;
    Socket client;
    DataInputStream din;
    DataOutputStream dout;
    BufferedReader bread;
    String message;
    String notify;
    Handler handler = new Handler();
    boolean tapped;
    public TourGuideConnect(Context c, Handler h)
    {
        context = c;
        tapped = false;
        handler = h;
    }
    public void setContext(Context c)
    {
        context = c;
    }
    public void setHandler(Handler h)
    {
        handler = h;
    }
    @Override
    public void run() {
        Socket socket;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Starting Server :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (null != serverSocket) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    socket = serverSocket.accept();
                    commThread = new CommunicationThread(socket, context, handler);
                    new Thread(commThread).start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error Communicating to Client :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void tap()
    {
        tapped = true;
    }

    public CommunicationThread getCommThread() {
        return commThread;
    }
}
