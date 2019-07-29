package com.example.leopardleap;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class VisitorConnect implements Runnable {

    public static final int SERVERPORT = 10001;
    public static final String INITMESSAGE = "You have connected with a visitor";
    private Socket socket;
    private BufferedReader input;
    private Context context;
    private String serverAddress;
    private Handler handler;
    private Activity activity;

    public VisitorConnect(Context c,String i, Handler h, Activity a)
    {
        context = c;
        serverAddress = i;
        handler = h;
        activity = a;
    }


    @Override
    public void run() {

        try {
            InetAddress serverAddr = InetAddress.getByName(serverAddress);
            socket = new Socket(serverAddr, SERVERPORT);
            sendMessage(INITMESSAGE);
            while (!Thread.currentThread().isInterrupted()) {

                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = input.readLine();
                if (null == message || "Disconnect".contentEquals(message)) {
                    Thread.interrupted();
                    message = "Server Disconnected.";
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    break;
                }
                checkCheckpoints(message);
                pushNotification(message);
                //showMessage("Server: " + message, clientTextColor);
            }

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != socket) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(message);
                        Log.v("GP2","Message sent: " + message);
                    }
                    Log.v("GP2","Null Socket");
                } catch (Exception e) {
                    Log.v("GP2","Message Error.");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void checkCheckpoints(String n)
    {
        handler.post(new VisitHandler(n, activity));

    }

    public void pushNotification(String n)
    {
        NotificationManager notif=(NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (activity.getApplicationContext()).setContentTitle("Leopard Leap").setContentText("Your tour guide has reached a checkpoint. Check the road map to catch up").
                setContentTitle("Tour reached a checkpoint").setSmallIcon(R.drawable.leopard).setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }
}
