package com.example.senior1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class profileFragment extends Fragment {
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<String> prf;
    Button ok;
    Uri imagep;
    SharedPreferences sp;
    private FirebaseAuth mAuth;
    public ValueEventListener valueEventListener;
    View view;
    Bitmap bitmap;
    private String generatedFilePath;

    DatabaseReference databasereference;
    FirebaseDatabase firebasedatabase;
    Profile p;
    SharedPreferences sh,saveimg;
    TextView textView;
    SharedPreferences.Editor e,esave;
    StorageReference ref;


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        Log.i("tg", "uuuuuuuu"+result);
                        e.putString("i",result.toString());
                        //Log.i("tg", ""+result);
                        e.apply();
                        e.commit();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(sp.getString("i", "")));
                            Log.i("tg", "myawwwwww" + bitmap);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getContext(), "Profile Empty", Toast.LENGTH_LONG).show();
                    }
                }
            });



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        sh = this.getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
        sp = this.getActivity().getSharedPreferences("imageprofile", Context.MODE_PRIVATE);


        esave=saveimg.edit();
         e =sp.edit();




        firebasedatabase = FirebaseDatabase.getInstance();








        Log.i("tg", "7777777777777777777777777: " + firebasedatabase.getReference("profile").child(sh.getString("id", "")));

        Log.i("tg", "88888888888888888888");


         textView= view.findViewById(R.id.textView_profile);
        ok = view.findViewById(R.id.ok);

        imageView = view.findViewById(R.id.profile_image2);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetContent.launch("image/*");
            }
        });



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent C = new Intent(getContext(), ListDriver.class);
                //startActivity(C);




//                    storage = FirebaseStorage.getInstance();
//                    storageReference = storage.getReference();
//                    ref= storageReference.child("images/" +bitmap);
//
//                    ref.putFile(bitmap);


                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();


                    storage = FirebaseStorage.getInstance();
                    storageReference = storage.getReference();
                    StorageReference ref = storageReference.child(""+mAuth.getCurrentUser().getUid()).child("images/"+bitmap);


                    ref.putFile(Uri.parse(sp.getString("i", "")))
                            .addOnSuccessListener(
                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                        }
                                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getContext(),
                                                    "Failed " + e.getMessage(),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }) .addOnProgressListener(
                                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                                        // Progress Listener for loading
                                        // percentage on the dialog box
                                        @Override
                                        public void onProgress(
                                                UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            double progress
                                                    = (100.0
                                                    * taskSnapshot.getBytesTransferred()
                                                    / taskSnapshot.getTotalByteCount());
                                            progressDialog.setMessage(
                                                    "Uploaded "
                                                            + (int)progress + "%");
                                        }
                                    });


                    esave.putString(mAuth.getCurrentUser().getUid(),BitMapToString(bitmap));

                esave.apply();
                esave.commit();





            }
        });



        return view;
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        saveimg = this.getActivity().getSharedPreferences("imgsave", Context.MODE_PRIVATE);

    }

    @Override
    public void onResume() {
        super.onResume();


            imageView.setImageBitmap(StringToBitMap(saveimg.getString(mAuth.getCurrentUser().getUid(),"")));
        Log.i("tg", "bitttt::  "+StringToBitMap(saveimg.getString(mAuth.getCurrentUser().getUid(),"")));

//       Log.i("tg", "sssss: "+StringToBitMap(saveimg.getString(mAuth.getCurrentUser().getUid(),"")));
        // imageView.setImageBitmap(bitmap);



      //  imageView.setVisibility(View.VISIBLE);
    }



    @Override
    public void onStart() {
        Log.i("tg", "1111111111111111111111111111111111111111 ");

        try {
            Log.i("tg", "helooooooooooooooooooooo: ");
            databasereference = firebasedatabase.getReference("profile").child(sh.getString("id", ""));

           databasereference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                   p = snapshot.getValue(Profile.class);
                   if (p != null) {

                       textView.setTextSize(20);
                       textView.setTextColor(Color.BLUE);
                       textView.setText("  First Name: " + p.getFName() +
                               "\n  last name: " + p.getLName() +
                               "\n  Phone: " + p.getPhoneNb() +
                               "\n  Email: " + p.getEmail());
                   }
                 assert p != null;

               }



               @Override
               public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {


                       System.out.println(error.getMessage() + "hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                       Log.i("tg", "hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii: ");
                   }


           });







        }catch (Exception e) {
            Log.i("tg", "Exception.. RegisterCustomer2" + e.getMessage());
            Toast.makeText(getContext(), "Exception.. RegisterCustomer2" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        super.onStart();


    }

}
