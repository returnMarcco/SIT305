package com.example.lost_and_found_app_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Implement OnMapReadyCallback.
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList itemLatList;
    private ArrayList itemLngList;
    private ArrayList itemLocationList;

    private ArrayList itemDescriptionList;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.map_activity);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.idMapFragment);
        mapFragment.getMapAsync(this);

        DbHelper dbHelper = new DbHelper(this);
        itemLatList = dbHelper.getItemLat();
        itemLngList = dbHelper.getItemLng();
        itemLocationList = dbHelper.getItemLocation();
        itemDescriptionList = dbHelper.getItemDescription();
    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
              int iteratorFlagLat = 0;
              int iteratorFlagLng = 0;
              int iteratorFlagItemLocation = 0;
              int iteratorFlagItemDescription = 0;

              String latStr;
              String lngStr;

              while(itemLatList.size() > iteratorFlagLat &&
                      itemLngList.size() > iteratorFlagLng &&
                      itemLocationList.size() > iteratorFlagItemLocation &&
                      itemDescriptionList.size() > iteratorFlagItemDescription) {
                  latStr = itemLatList.get(iteratorFlagLat).toString();
                  lngStr = itemLngList.get(iteratorFlagLng).toString();

                  googleMap.addMarker(new MarkerOptions()
                          .position(new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr)))
                          .title("Location: " + itemLocationList.get(iteratorFlagItemLocation).toString() +
                                  " | " + "Item: " + itemDescriptionList.get(iteratorFlagItemDescription))
                  );

                  iteratorFlagLat++;
                  iteratorFlagLng++;
                  iteratorFlagItemLocation++;
                  iteratorFlagItemDescription++;
              }
        }
    }