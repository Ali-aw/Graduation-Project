package com.example.senior1;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverCall extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView textView;
    String id;
    Profile p;
    EditText Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_call);
        firebaseDatabase = FirebaseDatabase.getInstance();
        textView=findViewById(R.id.textView6);
        Button Whatsapp=findViewById(R.id.GoToWhatsapp);
        Intent intent = getIntent();
         id =intent.getStringExtra("driverid");
        System.out.println("getIntent: " +id);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLUE);
         Phone=(EditText)findViewById(R.id.Phone);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager)getApplicationContext().
                        getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(textView.getText().toString());
                Toast.makeText(getApplicationContext(), "Copied :)" , Toast.LENGTH_SHORT).show();
            }
        });

        Whatsapp.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone="+Phone.getText();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);



        });

    }
    @Override
    protected void onStart() {

        System.out.println("onStart: " +id);
        try {
            databaseReference = firebaseDatabase.getReference("profile").child(id);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                         p = snapshot.getValue(Profile.class);

                    if (p != null) {
                        System.out.println("fname" + p.getFName());

                        textView.setText(  "  First Name: " + p.getFName() +
                                           "\n  last name: " + p.getLName() +
                                           "\n  Phone: " + p.getPhoneNb() +
                                           "\n  Email: " + p.email);
                        Phone.setText(p.getPhoneNb());
                    }
//                    assert p != null;

                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    System.out.println("onCancelled: " + error.getMessage());

                }
            });
            // loadingBar.dismiss();
        } catch (DatabaseException e) {
            Toast.makeText(getApplicationContext(), "failed save because" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        super.onStart();
    }


}
