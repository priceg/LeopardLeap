package com.example.leopardleap;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class VisitorConnect extends AsyncTask<String, Void, String> {
    Context context;
    Socket s;
    DataOutputStream dout;
    DataInputStream din;
    String ip;
    String message;
    boolean connected;
    public VisitorConnect(Context c)
    {
        context = c;
    }
    @Override
    protected String doInBackground(String... strings) {
        ip = strings[0];
        message = strings[1];
        try {
            s = new Socket(ip, 10001);
            connected = true;
            dout = new  DataOutputStream(s.getOutputStream());
            dout.writeUTF(message);
            dout.close();

            while(connected)
            {
               /** NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.abc).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);**/
                din = new DataInputStream(s.getInputStream());
                message = din.readUTF();
                if(message.equals("notify"))
                {
                    Toast.makeText(context, "notification", Toast.LENGTH_SHORT).show();
                }
            }
            if(!connected) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect()
    {
        try {
            message = "WE DONE!";
            dout = new  DataOutputStream(s.getOutputStream());
            dout.writeUTF(message);
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connected = false;
    }
}
