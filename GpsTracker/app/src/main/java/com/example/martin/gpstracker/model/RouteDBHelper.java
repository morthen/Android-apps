package com.example.martin.gpstracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 07/01/2016.
 */
public class RouteDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "routesmanager.db";

    // Table Names
    public static final String TABLE_GEOPOINT = "geopoint";
    public static final String TABLE_ROUTE = "route";

    //Columns of the GeoPoint Table
    public static final String COLUMN_ROUTE_ID = "_id";
    public static final String COLUMN_ROUTE_NAME = "name";
    public static final String COLUMN_ROUTE_STARTTIME = "starttime";
    public static final String COLUMN_ROUTE_ENDTIME = "endtime";

    //Columns of the Routes Table
    public static final String COLUMN_GEOPOINT_ID = "_id";
    public static final String COLUMN_GEOPOINT_LATITUDE = "latitude";
    public static final String COLUMN_GEOPOINT_LONGITUDE = "longitude";
    public static final String COLUMN_GEOPOINT_TIMEUP = "timeup";
    public static final String COLUMN_GEOPOINT_ROUTE_ID = "routeId";

    //Sql statement of the route table creation
    private static final String SQL_CREATE_TABLE_ROUTE = "CREATE TABLE "+ TABLE_ROUTE +" ("
            + COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ROUTE_NAME + " TEXT NOT NULL, "
            + COLUMN_ROUTE_STARTTIME + " TEXT NOT NULL, "
            + COLUMN_ROUTE_ENDTIME + " TEXT NOT NULL"
            + ");";

    //Sql statement of the geopoint table creation
    private static final String SQL_CREATE_TABLE_GEOPOINT = "CREATE TABLE "+TABLE_GEOPOINT+ " ("
            + COLUMN_GEOPOINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GEOPOINT_LATITUDE + " DOUBLE NOT NULL, "
            + COLUMN_GEOPOINT_LONGITUDE + " DOUBLE NOT NULL, "
            + COLUMN_GEOPOINT_TIMEUP + " TEXT NOT NULL, "
            + COLUMN_GEOPOINT_ROUTE_ID + " INT NOT NULL, "
            + "FOREIGN KEY("+COLUMN_GEOPOINT_ROUTE_ID+") REFERENCES "+ TABLE_ROUTE +"("+ COLUMN_ROUTE_ID +") ON DELETE CASCADE"
            + ");";

    public RouteDBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ROUTE);
        db.execSQL(SQL_CREATE_TABLE_GEOPOINT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ROUTE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GEOPOINT);

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
