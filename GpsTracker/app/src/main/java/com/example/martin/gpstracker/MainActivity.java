package com.example.martin.gpstracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.martin.gpstracker.model.Route;
import com.example.martin.gpstracker.model.RouteDBHelper;
import com.example.martin.gpstracker.model.RouteModel;
import com.example.martin.gpstracker.service.GPSService;

public class MainActivity extends AppCompatActivity {

    private boolean onGPSTracker;
    private ListView lv;
    private SimpleCursorAdapter sca;
    private RouteModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //service disabled
        if (savedInstanceState == null)
            onGPSTracker = false;

        //conect view with adapter and inflate
        String[] from = new String[] {RouteDBHelper.COLUMN_ROUTE_NAME,
                                RouteDBHelper.COLUMN_ROUTE_STARTTIME,
                                RouteDBHelper.COLUMN_ROUTE_ENDTIME};

        int[] to = new int[] {R.id.routeName, R.id.startTime, R.id.endTime};

        model = new RouteModel(this);
        sca = new SimpleCursorAdapter(this,R.layout.route_item,model.getRoutes(), from, to, 0);

        lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(sca);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleCursorAdapter ca = (SimpleCursorAdapter) parent.getAdapter();
                Cursor cursor = (Cursor) ca.getItem(position);

                int routeId = cursor.getInt(cursor.getColumnIndexOrThrow(RouteDBHelper.COLUMN_ROUTE_ID));

                Route selectedRoute = model.getRouteById(routeId);

                //Send selectedRoute to MapsActivity
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("selectedRoute", selectedRoute);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleCursorAdapter ca = (SimpleCursorAdapter) parent.getAdapter();
                Cursor cursor = (Cursor) ca.getItem(position);

                int routeId = cursor.getInt(cursor.getColumnIndexOrThrow(RouteDBHelper.COLUMN_ROUTE_ID));
                model.removeRouteById(routeId);
                recargar();
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("servicestatus", onGPSTracker);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onGPSTracker = savedInstanceState.getBoolean("servicestatus");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (onGPSTracker) {
            MenuItem item = menu.findItem(R.id.new_route);
            item.setIcon(R.drawable.ic_navigation_24dp_flash);
            ((AnimationDrawable) item.getIcon()).start();
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        if (onGPSTracker == false) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                onGPSTracker = true;
                startService(new Intent(MainActivity.this, GPSService.class));
                //Change icon and animate
                item.setIcon(R.drawable.ic_navigation_24dp_flash);
                ((AnimationDrawable) item.getIcon()).start();
            }
            else
                showGPSDisabledAlertToUser();
        }
        else {

            onGPSTracker = false;
            stopService(new Intent(MainActivity.this, GPSService.class));
            //Change icon default
            item.setIcon(R.drawable.ic_navigation_24dp);
        }
        return true;
    }

    public void recargar()
    {
        sca.changeCursor(model.getRoutes());
    }

    @Override
    public void onResume(){
        super.onResume();
        recargar();
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS is not Active");
        builder.setMessage("Please enable GPS");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                // Show location settings when the user acknowledges the alert dialog
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
