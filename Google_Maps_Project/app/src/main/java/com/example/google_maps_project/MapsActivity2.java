package com.example.google_maps_project;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity2 extends FragmentActivity {

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locManger;
    MyLocationListener locationListener;
    Button Get_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
//        addressText = (EditText) findViewById(R.id.edit_text2);
//        Get_location = (Button) findViewById(R.id.btn2);
//        locationListener = new MyLocationListener(getApplicationContext());
//        locManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        try {
//            locManger.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,locationListener);
//        }
//        catch (SecurityException ex){
//            Toast.makeText(getApplicationContext(), "You are Not Allowed To Access The Current Location", Toast.LENGTH_LONG).show();
//        }
//    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
//        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
}