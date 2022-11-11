package com.example.senior1;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.senior1.databinding.ActivityLmaplgdedeBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;



public class lmaplgdede extends FragmentActivity implements  LocationListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener,GoogleMap.OnMarkerDragListener
        ,GoogleMap.OnMarkerClickListener
{


    private ActivityLmaplgdedeBinding binding;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    LocationManager locationManager;
    ArrayList<LocationProvider> providers;
    private boolean isUpdatePosition;
    LatLng latLng1;
    int c=0;
    //public MarkerOptions marker;
    Marker mCurrentLocationMarker;
   public MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityLmaplgdedeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void mapDisplayPosition(){

        if ( ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
        {
            this.googleMap.setMyLocationEnabled(true);

            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            List<String> names = locationManager.getProviders(true);

            providers = new ArrayList<LocationProvider>();
            for(String name : names)
                providers.add(locationManager.getProvider(name));

            Criteria critere = new Criteria();

            // Pour indiquer la précision voulue
            // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
            critere.setAccuracy(Criteria.ACCURACY_FINE);

            // Est-ce que le fournisseur doit être capable de donner une altitude ?
            critere.setAltitudeRequired(true);

            // Est-ce que le fournisseur doit être capable de donner une direction ?
            critere.setBearingRequired(true);

            // Est-ce que le fournisseur peut être payant ?
            critere.setCostAllowed(false);

            // Pour indiquer la consommation d'énergie demandée
            // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
            critere.setPowerRequirement(Criteria.POWER_HIGH);

            // Est-ce que le fournisseur doit être capable de donner une vitesse ?
            critere.setSpeedRequired(true);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) { }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {}

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        this.googleMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
        Log.i("tg", "onMapReady: ");
        markerOptions = new MarkerOptions();
        markerOptions.draggable(true);

        this.googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerDragListener(this);

        mapDisplayPosition();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("tg", "onRequestPermissionsResult: ");
        mapDisplayPosition();
    }

    @Override
    public void onMapClick(LatLng latLng)
    {

        googleMap.clear();

        latLng1 = new LatLng(latLng.latitude,latLng.longitude);
        markerOptions.position(latLng1);
        markerOptions.title(latLng1.latitude + " : " + latLng1.longitude);//bs tkbs btll3on
        mCurrentLocationMarker=googleMap.addMarker(markerOptions);
        mCurrentLocationMarker.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       googleMap.animateCamera(CameraUpdateFactory.zoomTo(30));

          ///  markerOptions= new MarkerOptions().position(latLng);
          //  this.googleMap.addMarker(markerOptions);
        }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("Latitude",latLng1.latitude);
        intent.putExtra("Langitude",latLng1.longitude);
        setResult(1,intent);
        Log.i("tg", "onBackPressed: "+latLng1.latitude+""+latLng1.longitude);
        super.onBackPressed();
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker)
    {
        //googleMap.clear();
        Log.d("tg", "onMarkerDrag***...");
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker)
    {
        Log.d("tg", "onMarkerDragEnd...");


        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        latLng1 = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
        markerOptions.position(latLng1);
        markerOptions.title(latLng1.latitude + " : " + latLng1.longitude);
        mCurrentLocationMarker=googleMap.addMarker(markerOptions);
        mCurrentLocationMarker.showInfoWindow();
      //  Log.i("tg", "inside 2 "+addresses.get(0).getLocality());//Chaqra


    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker)
    {

        Log.d("tg", "onMarkerDrag start...");

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {


        return true;
    }

}