package com.example.martin.gpstracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Martin on 07/01/2016.
 */
public class Route implements Serializable {

    private String routeName;
    private Timestamp startTime;
    private Timestamp endTime;
    private List<GeoPoint> geoPoints;

    public Route(String routeName) {
        this.routeName = routeName;
        geoPoints = new ArrayList<>();
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRouteName() {
        return routeName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void addGeoPoint(GeoPoint gp) {
        geoPoints.add(gp);
    }

    public List<GeoPoint> getGeoPoints() {
        return geoPoints;
    }

    public GeoPoint getLastGeoPoint() {
        return geoPoints.get(geoPoints.size()-1);
    }
}
