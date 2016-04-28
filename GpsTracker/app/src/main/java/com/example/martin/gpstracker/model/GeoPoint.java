package com.example.martin.gpstracker.model;

import android.location.Location;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Martin on 07/01/2016.
 */
public class GeoPoint implements Serializable {
    private double latitude;
    private double longitude;
    private Timestamp timeup;

    public GeoPoint(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        timeup = new Timestamp(System.currentTimeMillis());
    }

    public GeoPoint(double latitude, double longitude, Timestamp timeup) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeup = timeup;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Timestamp getTimeup() {
        return timeup;
    }

}
