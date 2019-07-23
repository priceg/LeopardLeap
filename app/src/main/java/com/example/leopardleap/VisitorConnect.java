package com.example.leopardleap;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VisitorConnect extends AsyncTask<String, Void, String> {
    Socket s;
    DataOutputStream dout;
    String ip;
    String message;
    @Override
    protected String doInBackground(String... strings) {
        ip = strings[0];
        message = strings[1];
        try {
            s = new Socket(ip, 10001);
            dout = new  DataOutputStream(s.getOutputStream());
            dout.writeUTF(message);
            dout.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
