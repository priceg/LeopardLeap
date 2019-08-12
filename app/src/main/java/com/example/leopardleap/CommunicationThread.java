package com.example.leopardleap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread implements Runnable {
    private Socket clientSocket;
    private Socket tempClientSocket;
    private BufferedReader input;
    private Context context;
    private Handler handler;

    public CommunicationThread(Socket clientSocket, Context c, Handler h) {
        this.clientSocket = clientSocket;
        tempClientSocket = clientSocket;
        context = c;
        handler = h;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error Connecting to Client!!");

        }
        showToast("Connected to Client!!");

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String read = input.readLine();
                if (null == read || "Disconnect".contentEquals(read)) {
                    Thread.interrupted();
                    read = "Client Disconnected";
                    showToast("Client : " + read);
                    break;
                }
                showToast("Client : " + read);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(final String message) {
        try {
            if (null != tempClientSocket) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrintWriter out = null;
                        try {
                            out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(tempClientSocket.getOutputStream())),
                                    true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        out.println(message);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void showToast(final String message)
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setHandler(Handler h)
    {
        handler = h;
    }
}
