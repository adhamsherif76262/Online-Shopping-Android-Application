package com.example.google_maps_project;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class MyLocationListener implements LocationListener {

    private Context Activity_Context;

    public  MyLocationListener(Context cont){
        Activity_Context = cont;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(Activity_Context,location.getAltitude()+" , "+ location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
        Toast.makeText(Activity_Context,"GPS Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
        Toast.makeText(Activity_Context, "GPS Disabled", Toast.LENGTH_LONG).show();
    }
}
