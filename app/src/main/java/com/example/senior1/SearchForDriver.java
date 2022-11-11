package com.example.senior1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchForDriver extends AppCompatActivity {
    EditText srcedittext, dstedittext;
    Button searchbtn;
    DatabaseReference databaseReferencesearchsrc, databaseReferencesearchdst;
    FirebaseDatabase firebaseDatabase;
    public GeoFire geoFiregetsrc, geoFiregetdst;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private String driverid;
    List<Address> addressListdst, addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_driver);


        srcedittext = findViewById(R.id.searchsrc);
        dstedittext = findViewById(R.id.searchdst);
        searchbtn = findViewById(R.id.button2);
        firebaseDatabase = FirebaseDatabase.getInstance("https://senior1-99c95-default-rtdb.firebaseio.com/");
        databaseReferencesearchsrc = FirebaseDatabase.getInstance().getReference().child("Drivers: ").child("SourceLocation");
        databaseReferencesearchdst = FirebaseDatabase.getInstance().getReference().child("Drivers: ").child("DestinationLocation");
        driverid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void getData(View view) {


        geoFiregetsrc = new GeoFire(databaseReferencesearchsrc);
        geoFiregetdst = new GeoFire(databaseReferencesearchdst);
        geoFiregetsrc.getLocation(driverid, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                geoFiregetdst.getLocation(driverid, new LocationCallback() {
                    @Override
                    public void onLocationResult(String key, GeoLocation location) {
                        if (location != null) {
                            Geocoder geocoder1 = new Geocoder(SearchForDriver.this, Locale.getDefault());
                            try {
                                addressListdst = geocoder1.getFromLocation(location.latitude,
                                        location.longitude, LOCATION_PERMISSION_CODE);

                                if (addressListdst.get(0).getSubAdminArea().trim().equals(dstedittext.
                                        getText().toString().trim())) {
                                    Log.i("tg", ": " + addressListdst + "\n " + addressList);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error*****", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("tg", "databaseError: " + databaseError);

                    }
                });
                if (location != null) {

                    Geocoder geocoder = new Geocoder(SearchForDriver.this, Locale.getDefault());
                    try {
                        addressList = geocoder.getFromLocation(location.latitude,
                                location.longitude, LOCATION_PERMISSION_CODE);


                        if (addressList.get(0).getSubAdminArea().trim().equals(srcedittext.
                                getText().toString().trim())) {


                            Intent intent = new Intent(getApplicationContext(), DriverCall.class);
                            intent.putExtra("driverid", driverid);
                            startActivity(intent);

                        } else {
                            Log.i("tg", "maaaaawagadtohaaaaaa: ");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Error*****", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("tg", "databaseError: " + databaseError);

            }
        });


    }


}














