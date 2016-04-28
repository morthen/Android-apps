package com.example.martin.gpstracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Martin on 07/01/2016.
 */
public class RouteModel {

    private RouteDBHelper dbh;
    private SQLiteDatabase db;
    private Context context;

    public RouteModel(Context context) {
        this.context = context;
        dbh = new RouteDBHelper(context);
        open();
    }

    public void open() {
        db = dbh.getWritableDatabase();
    }

    public void close() {
        dbh.close();
    }

    public void insertRouteDB(Route route) {
        ContentValues values = new ContentValues();
        values.put(dbh.COLUMN_ROUTE_NAME,route.getRouteName());
        values.put(dbh.COLUMN_ROUTE_STARTTIME,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(route.getStartTime()));
        values.put(dbh.COLUMN_ROUTE_ENDTIME,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(route.getEndTime()));

        long insertedRouteId = db.insert(dbh.TABLE_ROUTE,null,values);
        if (insertedRouteId != -1) {
            //Now insert GeoPoints
            List<GeoPoint> geoPointList = route.getGeoPoints();
            for(GeoPoint g : geoPointList) {
                ContentValues geoPointValue = new ContentValues();
                geoPointValue.put(dbh.COLUMN_GEOPOINT_LATITUDE,g.getLatitude());
                geoPointValue.put(dbh.COLUMN_GEOPOINT_LONGITUDE,g.getLongitude());
                geoPointValue.put(dbh.COLUMN_GEOPOINT_TIMEUP, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(g.getTimeup()));
                geoPointValue.put(dbh.COLUMN_GEOPOINT_ROUTE_ID,insertedRouteId);
                long insertedGeoPointId = db.insert(dbh.TABLE_GEOPOINT,null,geoPointValue);
                if (insertedGeoPointId == -1) {
                    throw new SQLException("Not inserted GEOPOINT");
                }
            }
        }
        else {
            throw new SQLException("Not inserted ROUTE");
        }
    }

    public Cursor getRoutes() {
        return db.query(dbh.TABLE_ROUTE,null,null,null,null,null,dbh.COLUMN_ROUTE_NAME);
    }

    public Route getRouteById(int id) {
        Route route = null;
        String [] args = new String [] {String.valueOf(id)};
        Cursor cr = db.query(dbh.TABLE_ROUTE,null,dbh.COLUMN_ROUTE_ID+"=?",args,null,null,dbh.COLUMN_ROUTE_NAME);

        if( cr.moveToFirst()) {
            String routeName = cr.getString(cr.getColumnIndex(dbh.COLUMN_ROUTE_NAME));
            Timestamp startTime = Timestamp.valueOf(cr.getString(cr.getColumnIndex(dbh.COLUMN_ROUTE_STARTTIME)));
            Timestamp endTime = Timestamp.valueOf(cr.getString(cr.getColumnIndex(dbh.COLUMN_ROUTE_ENDTIME)));
            route = new Route(routeName);
            route.setStartTime(startTime);
            route.setEndTime(endTime);

            Cursor cg = db.query(dbh.TABLE_GEOPOINT,null,dbh.COLUMN_GEOPOINT_ROUTE_ID+"=?",args,null,null,dbh.COLUMN_GEOPOINT_ID);
            if (cg.moveToFirst()) {
                do {
                    double latitude = cg.getDouble(cg.getColumnIndex(dbh.COLUMN_GEOPOINT_LATITUDE));
                    double longitude = cg.getDouble(cg.getColumnIndex(dbh.COLUMN_GEOPOINT_LONGITUDE));
                    Timestamp timeup = Timestamp.valueOf(cg.getString(cg.getColumnIndex(dbh.COLUMN_GEOPOINT_TIMEUP)));
                    GeoPoint gp = new GeoPoint(latitude,longitude,timeup);
                    route.addGeoPoint(gp);
                } while (cg.moveToNext());
            }
        }
        return route;
    }

    public void removeRouteById(int id) {
        String[] whereArguments = {Integer.toString(id)};
        db.delete(dbh.TABLE_ROUTE,dbh.COLUMN_ROUTE_ID+"=?",whereArguments);
    }
}
