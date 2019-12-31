package com.xclusive.flightscheduleapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private Button map;
    private String origin,destination;
    private String latitude,longitude,latitude2,longitude2;
    private Spinner selectOrigin, selectDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = (Button)findViewById(R.id.map_btn);
        selectOrigin = (Spinner) findViewById(R.id.select_origin);
        selectDestination = (Spinner) findViewById(R.id.select_destination);
        selectOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                origin = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        selectDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                destination = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        */
        map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               validate();
            }
        });

    }
    // Checking if user has made a selection.
    private void validate()
    {

        if (origin.equals("Select_origin_airport_code"))
        {
            Toast.makeText(MainActivity.this, "please enter select your origin", Toast.LENGTH_LONG).show();
        }
        else if (destination.equals("Select_origin_airport_code"))
        {
            Toast.makeText(MainActivity.this, "please enter select your destination", Toast.LENGTH_LONG).show();
        }
        else
        {
            startMapActivity(origin,destination);
        }
    }
//Starting up the map activity
    private void startMapActivity(String origin, String destination)
    {
        Double lat = airportcordinates(MainActivity.this,origin+" airport").latitude;
        Double lng = airportcordinates(MainActivity.this,origin+" airport").longitude;
        Double lat2 = airportcordinates(MainActivity.this,destination+" airport").latitude;
        Double lng2 = airportcordinates(MainActivity.this,destination+" airport").longitude;

        latitude = lat.toString();
        longitude = lng.toString();
        latitude2 = lat2.toString();
        longitude2 = lng2.toString();

        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("lat",latitude);
        intent.putExtra("long",longitude);
        intent.putExtra("lat2",latitude2);
        intent.putExtra("long2",longitude2);
        startActivity(intent);
        finish();
    }
// Retrieve coordinates from airport codes
    public static LatLng airportcordinates(Context context, String city)
    {
        Geocoder geocoder = new Geocoder(context,context.getResources().getConfiguration().locale);
        List<Address> addresses = null;
        LatLng latLng = null;
        try
        {
            addresses = geocoder.getFromLocationName(city, 1);
            Address address = addresses.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return latLng;
    }
}
