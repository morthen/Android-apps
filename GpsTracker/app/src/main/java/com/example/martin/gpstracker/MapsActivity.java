package com.example.martin.gpstracker;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.martin.gpstracker.model.GeoPoint;
import com.example.martin.gpstracker.model.Route;
import com.example.martin.gpstracker.model.RouteModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Route selectedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get selectedRoute
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            selectedRoute = (Route) b.getSerializable("selectedRoute");
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Center map in last GeoPoint of the Route
        GeoPoint lastGp = selectedRoute.getLastGeoPoint();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lastGp.getLatitude(), lastGp.getLongitude()), 13));

        // Polylines are useful for marking paths and routes on the map.
        PolylineOptions rectOptions = new PolylineOptions().geodesic(true).color(Color.RED);
        for(GeoPoint gp : selectedRoute.getGeoPoints()) {

            rectOptions.add(new LatLng(gp.getLatitude(), gp.getLongitude()));

        }
        mMap.addPolyline(rectOptions);
    }
}
