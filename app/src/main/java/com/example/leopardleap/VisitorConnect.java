package com.example.leopardleap;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void checkCheckpoints(String n)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ImageView leopard_check = activity.findViewById(R.id.check2);
                ImageView tansey_check = activity.findViewById(R.id.check3);
                ImageView beatty_check = activity.findViewById(R.id.check);
                ImageView baker_check = activity.findViewById(R.id.check4);
                ImageView watson_check = activity.findViewById(R.id.check1);
                ImageView huntington_check = activity.findViewById(R.id.check5);

                switch(n) {
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
        });

    }

    public void pushNotification(String n)
    {

    }
}
