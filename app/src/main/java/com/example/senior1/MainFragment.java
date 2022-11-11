package com.example.senior1;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

  public class MainFragment extends Fragment  implements ConnectionReceiver.ReceiverListener {

    // Initialize variable
    TextView textView;
    ImageButton imageButton;
    ImageButton imageButton2;
    boolean isConnected;
    Snackbar snackbar;
    View view;
    String message;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          view=inflater.inflate(R.layout.fragment_main, container, false);
        // Initialize view

        imageButton = view.findViewById(R.id.imageButton);
        imageButton2 = view.findViewById(R.id.imageButton2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login = new Intent(getContext(), LoginRegister.class);
                login.putExtra("ProfileType", "Driver");

                if(isConnected){
                    startActivity(login);
                }else{
                    showSnackBar(isConnected);

                }
            }
        });


        snackbar=Snackbar.make(container,message,Snackbar.LENGTH_LONG);


        //snackbar = Snackbar.make(view.findViewById(R.id.imageButton), message, Snackbar.LENGTH_LONG);
        //View view1 = snackbar.getView();

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getContext(), LoginRegister.class);
                login.putExtra("ProfileType", "Rider");

                if(isConnected){
                    startActivity(login);
                }else{
                    showSnackBar(isConnected);

                }
            }
        });

        checkConnection();
        return view;


    }


    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        // register receiver
       getActivity().registerReceiver(new ConnectionReceiver(), intentFilter);
        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manger
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        System.out.println("showSnackBar****************");
        int color;
        // check condition
        if (isConnected) {
            System.out.println("showSnackconnBar****************");
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
        snackbar.setText(message);
        snackbar.show();

        // initialize snack bar



    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }
}