package com.example.senior1;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Welcome extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {



    @Override
    protected void onResume() {
        super.onResume();
        Log.i("tg", "888888888888888888onResume888888888888: ");

        SharedPreferences sp=getSharedPreferences("seprateuser", Context.MODE_PRIVATE);
        Log.i("tg", "onResume: "+sp.getBoolean("login",false));
        if(sp.getBoolean("login",false))
        {
            Log.i("tg", "onResume1111: "+sp.getBoolean("login",false));

            Intent i= new Intent(getApplicationContext(),tab.class);
            startActivity(i);
        }

    }
    String prevStarted="yes";
    ImageButton imageButton;
    ImageButton imageButton2;
    boolean isConnected;

    Button btn_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);

        // create method

        checkConnection();

    }

    public void NavigateToDriverLogin(View view) {
        Intent login = new Intent(getApplicationContext(), LoginRegister.class);
        login.putExtra("ProfileType", "Driver");
        if(isConnected){
            startActivity(login);
        }else{
            showSnackBar(isConnected);

        }
    }


    public void NavigateToRiderLogin(View view) {

        Intent login = new Intent(getApplicationContext(), LoginRegister.class);
        login.putExtra("ProfileType", "Rider");
        if(isConnected){
            startActivity(login);
        }else{
            showSnackBar(isConnected);

        }

    }


    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);
        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manger
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;
        // check condition
        if (isConnected) {
            // when internet is connected
            // set message
            message = "Connected to Internet";
            // set text color
            color = Color.WHITE;
        } else {
            // when internet
            // is disconnected
            // set message
            message = "Not Connected to Internet";
            // set text color
            color = Color.RED;
        }
        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.imageButton), message, Snackbar.LENGTH_LONG);
        // initialize view
        View view = snackbar.getView();
        // Assign variable
        TextView textView = new TextView(this);
        // set text color
        textView.setTextColor(color);
        // show snack bar
        snackbar.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }


}