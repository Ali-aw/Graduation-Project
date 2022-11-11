package com.example.senior1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class spinnerActivity extends AppCompatActivity {

    // creating variables for
    // edit texts and button.
    android.widget.Spinner src_Governorate, src_District, src_name, dest_Governorate, dest_District, dest_name;
    Button trackBtn, btnSave,Search,Search_map;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3, databaseReference4;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase firebaseDatabase2;
    FirebaseDatabase firebaseDatabase3, firebaseDatabase4;
    FirebaseUser firebaseUser;
    android.widget.Spinner spinner;
    String source;
    String destination;
    ProgressDialog loadingBar;
    ProgressDialog loadingBar2;
    ProgressDialog loadingBar3;
    String select_gov_dest;
    String select_gov_src;
    String select_district_dest;
    String select_district_src;
    String select_name_dest;
    String select_name_src;
    locationArea lsrs, ldst;
    AlertDialog alert11;
    Profile currentUser;
    String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_src_dest);

        // initializing our edit text and buttons
        Search_map=(Button)findViewById(R.id.search_mapp);
        dest_Governorate = findViewById(R.id.dest_Governorate);
        dest_District = findViewById(R.id.dest_District);
        dest_name = findViewById(R.id.dest_name);
        src_name = findViewById(R.id.src_Name);
        src_District = findViewById(R.id.src_District);
        src_Governorate = findViewById(R.id.src_Governorate);
        trackBtn = findViewById(R.id.idBtnTrack);
        Search = findViewById(R.id.search);
        firebaseDatabase2 = FirebaseDatabase.getInstance();
        firebaseDatabase3 = FirebaseDatabase.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingBar = new ProgressDialog(this);
        loadingBar2 = new ProgressDialog(this);
        loadingBar3 = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        Intent mode = getIntent();
        m = mode.getStringExtra("mode");


        //Search

        Log.i("tg", "77777777777: " + m);
        // adding on click listener to our button.

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(select_name_src)) {
                    Toast.makeText(getApplicationContext(), "Please choose the source name", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(select_district_src)) {
                    Toast.makeText(getApplicationContext(), "Please choose the source district  ", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(select_gov_src)) {
                    Toast.makeText(getApplicationContext(), "Please choose the source Governorate", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(select_name_dest)) {
                    Toast.makeText(getApplicationContext(), "Please choose the destination name  ", Toast.LENGTH_SHORT).show();

                }
                if (TextUtils.isEmpty(select_district_dest)) {
                    Toast.makeText(getApplicationContext(), "Please choose the destination district", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(select_gov_dest)) {
                    Toast.makeText(getApplicationContext(), "Please choose the destination Governorate  ", Toast.LENGTH_SHORT).show();

                } else {
                    // calling a method to draw a track on google maps.
                    if (!source.isEmpty() && !destination.isEmpty())
                        drawTrack(source.toString(), destination.toString());
                }
            }
        });

        selectGovernorate(src_Governorate);
        System.out.println("loding all  Governorate");
        selectGovernorate(dest_Governorate);
        btnSave = findViewById(R.id.button3);
        if (m.equals("<Search...")) {

            btnSave.setEnabled(false);

        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("save succ...go to list of driver event???");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent r = new Intent(getApplicationContext(), tab.class);
                startActivity(r);
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert11 = builder1.create();

        databaseReference = firebaseDatabase.getReference("profile").child(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                currentUser = (Profile) snapshot.getValue(Profile.class);
                //   Log.i("tg", "name: "+snapshot.child("FName").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                System.out.println("onCancelled: " + error.getMessage());

            }
        });



        FragmentManager fm = this.getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(fm, "supportFragmentManager");
        FragmentManager mFragmentManager = fm;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(ft, "mFragmentManager.beginTransaction()");
        final FragmentTransaction mFragmentTransaction = ft;
        final fragmentList mFragment = new fragmentList();

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                u.putExtra("districtsrc",select_district_src);
//                u.putExtra("districtdest",select_district_dest);
//                f.mGetContent.launch(u);

                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("districtsrc",select_district_src);
                    bundle.putString("districtdest",select_district_dest);


                    mFragment.setArguments(bundle);
                    mFragmentTransaction.add(R.id.frameLayout, (Fragment) mFragment).commit();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                alert11.show();
            }

    });
        Search_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getApplicationContext(), lmaplgdede.class);
                startActivity(r);

            }

        });
}

    public void save(View view) {
        if (TextUtils.isEmpty(select_name_src)) {
            Toast.makeText(getApplicationContext(), "Please choose the source name", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(select_district_src)) {
            Toast.makeText(getApplicationContext(), "Please choose the source district  ", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(select_gov_src)) {
            Toast.makeText(getApplicationContext(), "Please choose the source Governorate", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(select_name_dest)) {
            Toast.makeText(getApplicationContext(), "Please choose the destination name  ", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(select_district_dest)) {
            Toast.makeText(getApplicationContext(), "Please choose the destination district", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(select_gov_dest)) {
            Toast.makeText(getApplicationContext(), "Please choose the destination Governorate  ", Toast.LENGTH_SHORT).show();

        } else {


            try {
                databaseReference3 = firebaseDatabase3.getReference("event");

                String date = LocalDate.now().toString();
                event event = new event(mAuth.getUid(), currentUser.getEmail(),
                        currentUser.getFName(), currentUser.getLName(), currentUser.getPhoneNb(), select_name_src,
                        select_district_src, select_gov_src, select_name_dest, select_district_dest, select_gov_dest, date);
                databaseReference3.push().setValue(event);
            } catch (DatabaseException e) {
                Toast.makeText(getApplicationContext(), "failed save " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            alert11.show();
        }

    }

    private void drawTrack(String source, String destination) {
        try {
            // create a uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);

            // initializing a intent with action view.
            Intent i = new Intent(Intent.ACTION_VIEW, uri);

            // below line is to set maps package name
            i.setPackage("com.google.android.apps.maps");

            // below line is to set flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // start activity
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // when the google maps is not installed on users device
            // we will redirect our user to google play to download google maps.
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");

            // initializing intent with action view.
            Intent i = new Intent(Intent.ACTION_VIEW, uri);

            // set flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // to start activity
            startActivity(i);
        }
    }

    private void selectGovernorate(android.widget.Spinner spinner) {

        List<String> plantsList = new ArrayList<>();
        plantsList.add("select Governorate");


        try {

//            loadingBar.setTitle("loding all  Governorate");
//            loadingBar.setMessage("please wait ...");
//            loadingBar.show();

            databaseReference2 = firebaseDatabase2.getReference("Governorate");
            //  databaseReference2.getClass();
            System.out.println("add");
            databaseReference2.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                 //   loadingBar.show();
                    System.out.println("add item");
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String d = (String) child.getKey();
                        plantsList.add(d);
                        System.out.println(d);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
            //loadingBar.dismiss();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            Toast.makeText(getApplicationContext(), "add : Governorate" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

       // loadingBar.dismiss();


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {// Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    if (src_Governorate == spinner) {
                        select_gov_src = selectedItemText;
                        selectDistrict("src", selectedItemText);
                    } else {
                        select_gov_dest = selectedItemText;
                        selectDistrict("dest", selectedItemText);
                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void selectDistrict(String type, String item) {

        List<String> plantsList = new ArrayList<>();

        plantsList.add(item + "  District");
        try {
//            loadingBar2.setTitle("loding district of " + item + " governover");
//            loadingBar2.setMessage("please wait ...");
//            loadingBar2.show();

            databaseReference2 = firebaseDatabase2.getReference("Governorate").child(item);
            //  databaseReference2.getClass();
            System.out.println("add");

            databaseReference2.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // loadingBar2.show();
                    System.out.println("add item");
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String d = (String) child.getKey();
                        plantsList.add(d);
                        System.out.println(d);
                        //loadingBar2.dismiss();
                    }
                   // loadingBar2.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });


        } catch (Exception e) {

            System.out.println(e.getMessage());

            Toast.makeText(getApplicationContext(), "add : Governorate" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {// Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        if (type.equals("src"))
            spinner = src_District;
        else
            spinner = dest_District;

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {

                    if (src_District == spinner) {
                        select_district_src = selectedItemText;
                        selectName("src", item, selectedItemText);
                    } else {
                        select_district_dest = selectedItemText;
                        selectName("dest", item, selectedItemText);
                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void selectName(String type, String Destrict_item, String name_item) {


        List<String> plantsList = new ArrayList<>();
        plantsList.add(name_item + "  location name");
        try {
            loadingBar3.setTitle("loding location name of" + Destrict_item + "district");
            loadingBar3.setMessage("please wait ...");
            loadingBar3.show();


            databaseReference2 = firebaseDatabase2.getReference("Governorate").child(Destrict_item).child(name_item);
            //  databaseReference2.getClass();
            System.out.println("add");
            databaseReference2.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println("add item");

                    for (DataSnapshot child : snapshot.getChildren()) {
                        String d = (String) child.getValue(String.class);
                        plantsList.add(d);
                        System.out.println(d);
                    }
                    loadingBar3.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());

            Toast.makeText(getApplicationContext(), "add : Governorate" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {// Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        if (type.equals("src"))
            spinner = src_name;
        else
            spinner = dest_name;

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    if (type.equals("src")) {
                        select_name_src = selectedItemText;
                        destination = selectedItemText;
                    } else {
                        select_name_dest = selectedItemText;
                        source = selectedItemText;
                    }

                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


/*
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.i("tg", "RegisterCustomer2RegisterCustomer2..if: ");

                if (task.isSuccessful()) {

                    handlermain.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent ChooseSrcDst = new Intent(getApplicationContext(), GetSrcDest.class);
                            startActivity(ChooseSrcDst);

                            Toast.makeText(getApplicationContext(), "Customer Register sucessfully", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), " unsucessfully insert data!!", Toast.LENGTH_SHORT).show();
                }

        });

    */

}
