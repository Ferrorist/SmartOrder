package com.cookandroid.registerlogin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cookandroid.registerlogin.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AutoPermissionsListener {

    static final Integer APP_PERMISSION = 1;
    private GoogleMap mMap;
    public LatLng user_latlng = new LatLng(0,0);
    private LocationManager locationManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoPermissions.Companion.loadAllPermissions(this,APP_PERMISSION);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.activity_maps);
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        else {
            setContentView(R.layout.permissions_frag);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        startMapService(googleMap);
        markRestaurant(googleMap);
    }

    public void startMapService(GoogleMap googleMap) {
        findUserLocation();
        googleMap.addMarker(new MarkerOptions().position(user_latlng).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user_latlng));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        mMap.animateCamera(zoom);
    }

    public void findUserLocation() { // ?????? ?????? ??????.
        Location location;
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                user_latlng = new LatLng(latitude, longitude); // ????????? ?????? ????????? ????????? ??????.
                Toast.makeText(this, "latitude : " + user_latlng.latitude + ", longitude: " + user_latlng.longitude, Toast.LENGTH_SHORT).show();
                GPSListener gps = new GPSListener();
                long minTime = 3000;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, 0 , gps);
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    user_latlng = new LatLng(latitude, longitude); // ????????? ?????? ????????? ????????? ??????.
                    GPSListener gps = new GPSListener();
                    long minTime = 3000;
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, 0, gps);
                }
            }
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    public void markRestaurant(GoogleMap googleMap) { // ?????? ?????? ??????
        MarkerOptions marker;
        List restaurant = new ArrayList();
        restaurant.add(new LatLng(35.89237,128.61321)); // ?????????????????? ??????
        restaurant.add(new LatLng(35.89191, 128.61121)); // ?????????????????? ??????
        restaurant.add(new LatLng(35.88916, 128.61448)); // ????????? ??????
        restaurant.add(new LatLng(35.88855, 128.61027)); // ??????12?????? ??????

        Iterator iterator = restaurant.iterator();

        while(iterator.hasNext()) {
            LatLng latLng = (LatLng) iterator.next();
            marker = new MarkerOptions();
            marker.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_30));

            googleMap.addMarker(marker);
        }
    }
    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDenied(int i, String[] strings) {
        Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
        Log.i("test", "?????? ??????");
    }

    @Override
    public void onGranted(int i, String[] strings) {
        Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
        Log.i("test", "?????? ??????");
    }
}