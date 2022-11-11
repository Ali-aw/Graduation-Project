package com.example.senior1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ImageProfile extends AppCompatActivity {
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
   ArrayList<String> prf;
   Button ok;


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    imageView.setImageURI(result);
                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference();
                    StorageReference ref = storageReference.child("images/"+(prf.get(prf.size()-1)));
                    ref.putFile(result);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_profile);
        TextView textView=findViewById(R.id.textView_profile);
        ok=findViewById(R.id.ok);

        imageView = findViewById(R.id.profile_image2);
        Intent i=getIntent();
       prf=i.getStringArrayListExtra("profile");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetContent.launch("image/*");
            }
        });

        textView.setTextSize(20);
        textView.setTextColor(Color.BLUE);
        textView.setText(  "  First Name: " + prf.get(0) +
                "\n  last name: " + prf.get(2)  +
                "\n  Phone: " + prf.get(3)  +
                "\n  Email: " + prf.get(0) );

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent C = new Intent(getApplicationContext(), ListDriver.class);
                startActivity(C);

            }
        });


    }





}