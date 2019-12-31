package com.xclusive.flightscheduleapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private String latitude,longitude, latitude2,longitude2;
    private Double lat, lng,lat2,lng2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
        latitude = getIntent().getExtras().get("lat").toString();
        longitude = getIntent().getExtras().get("long").toString();
        latitude2 = getIntent().getExtras().get("lat2").toString();
        longitude2 = getIntent().getExtras().get("long2").toString();
       // Toast.makeText(MapActivity.this, lats,Toast.LENGTH_LONG).show();
        lat = Double.parseDouble(latitude);
        lng = Double.parseDouble(longitude);
        lat2 = Double.parseDouble(latitude2);
        lng2 = Double.parseDouble(longitude2);

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng airport1 = new LatLng(lat, lng);
        LatLng airport2 = new LatLng(lat2, lng2);
        mMap.addMarker(new MarkerOptions().position(airport2).title("Marker in origin"));
        mMap.addMarker(new MarkerOptions().position(airport1).title("Marker in destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(airport2));
        mMap.addPolyline(
                new PolylineOptions().add(airport1).add(airport2).width(4f).color(Color.RED)
        );
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
