package com.example.leopardleap;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    public BackgroundWorker(Context c)
    {
        context = c;
    }

    @Override
    protected String doInBackground(String... args) {
        String type = args[0];
        String dbSite = "http://192.168.1.212/login.php";
        if(type.equals("login"))
        {
            try {
                String username = args[1];
                String password = args[2];
                URL url = new URL(dbSite);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream sout = connection.getOutputStream();
                BufferedWriter bwrite = new BufferedWriter(new OutputStreamWriter(sout, "UTF-8"));
                String postData = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        +         URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
                bwrite.write(postData);
                Log.v("GP2", "This is what it wrote " + postData);
                bwrite.flush();
                bwrite.close();
                sout.close();
                InputStream stream_in = connection.getInputStream();
                BufferedReader bread = new BufferedReader(new InputStreamReader(stream_in, "iso-8859-1"));
                String result = new String();
                String line = "";
                while((line = bread.readLine()) != null)
                {
                    Log.v("GP2", "Entered the loop");
                    result+=line;
                }
                bread.close();
                stream_in.close();
                connection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                Log.v("GP2", "Malformed Error");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("GP2", "IO Error");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
