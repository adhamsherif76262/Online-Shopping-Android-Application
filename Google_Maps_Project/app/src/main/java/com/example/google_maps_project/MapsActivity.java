package com.example.google_maps_project;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.google_maps_project.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    LocationManager locManger;
    MyLocationListener locationListener;

    EditText addressText;
    Button Get_location;
    Button Done_Locating;

    private FusedLocationProviderClient fusedLocationClient;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 60000;

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                GPS();
                Toast.makeText(MapsActivity.this, "60 Seconds Have Passed ==> Location Updated",
                        Toast.LENGTH_LONG).show();
            }
        }, delay);
        super.onResume();
    }
    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        addressText = (EditText) findViewById(R.id.edit_text);

        Get_location = (Button) findViewById(R.id.btn);

        locationListener = new MyLocationListener(getApplicationContext());

        locManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fetchLastLocation();
        Done_Locating= (Button) findViewById(R.id.btn_finished);

        Done_Locating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Go To Order Confirmation Page",Toast.LENGTH_LONG).show();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 30));

        Get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPS();
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                // Real Time GPS Tracker
//                GetMarkerLocation(marker);
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                GetMarkerLocation(marker);
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
//                GetMarkerLocation(marker);
            }
        });

//         Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void fetchLastLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Nooo: ",Toast.LENGTH_LONG).show();
                return;
            }
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
            // Got last known location. In some rare situations this can be null.
                if (location != null) {
                  // Logic to handle location object
                  Toast.makeText(getApplicationContext(),"LAST LOCATION: "+ location.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission was denied, show alert to explain permission
                    showPermissionAlert();
                } else {
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        fetchLastLocation();
                    }
                }
            }
        }
    }

    private void showPermissionAlert() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        List<String> providers = locManger.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locManger.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location:
                bestLocation = l;
            }
        }

        return bestLocation;
    }

//    public Location getLocation() {
//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(),"Didn't Work ,, No Permission Granted",Toast.LENGTH_LONG).show();
//            }
//            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (lastKnownLocationGPS != null) {
//                return lastKnownLocationGPS;
//            } else {
//                Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                System.out.println("1::"+loc);
//                System.out.println("2::"+loc.getLatitude());
//                return loc;
//            }
//        } else {
//            return null;
//        }
//    }

    public  void GPS(){
        mMap.clear();
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> Address_List;
        Location loc = null;
        try {
//                    loc = locManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    loc = getLocation();

            loc = getLastKnownLocation();
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "You Did Not Allow Access To The Current Location", Toast.LENGTH_LONG).show();
        }
        if (loc != null) {
            LatLng My_Position = new LatLng(loc.getLatitude(), loc.getLongitude());
            try {
                Address_List = coder.getFromLocation(My_Position.latitude, My_Position.longitude, 1);
                if (!Address_List.isEmpty()) {
                    String Address = "";
                    for (int i = 0; i <= Address_List.get(0).getMaxAddressLineIndex(); i++) {
                        Address += Address_List.get(0).getAddressLine(i) + " , ";
                        mMap.addMarker(new MarkerOptions().position(My_Position).title("My Location").snippet(Address)).setDraggable(true);
                        addressText.setText(Address);
                    }
                }
            } catch (IOException IO) {
                mMap.addMarker(new MarkerOptions().position(My_Position).title("My Location"));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(My_Position, 15));
        } else {
            Toast.makeText(getApplicationContext(), "Please Wait Until Your Position Is Determined", Toast.LENGTH_LONG).show();
        }
    }
    public  void GetMarkerLocation(@NonNull Marker marker){
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> Address_List;
                try {
                    Address_List = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!Address_List.isEmpty()) {
                        String Address = "";
                        for (int i = 0; i <= Address_List.get(0).getMaxAddressLineIndex(); i++) {
                            Address += Address_List.get(0).getAddressLine(i) + " , ";
                            addressText.setText(Address);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "There Is No Address For This Location", Toast.LENGTH_LONG).show();
                        addressText.getText().clear();
                    }
                }
                catch (IOException IO) {
                    Toast.makeText(getApplicationContext(), "Can't Get The Address, Please Check Your Network", Toast.LENGTH_LONG).show();
                }
    }
}