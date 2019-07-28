package com.example.leopardleap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.loginButton);
    }
    public void tryLogin(View view)
    {
        String eusn = username.getText().toString();
        String epwd = password.getText().toString();
        Log.v("GP2", "This is the username: " + eusn);
        Log.v("GP2", "This is the password: " + epwd);
        String type = "login";
        BackgroundWorker bw = new BackgroundWorker(this);
        bw.execute(type, eusn, epwd);
    }
    public void tryData(View view)
    {
        Integer id = new Integer(2);
        String type = "data";
        BackgroundWorker bw = new BackgroundWorker(this);
        bw.execute(type, id.toString());
    }

}
