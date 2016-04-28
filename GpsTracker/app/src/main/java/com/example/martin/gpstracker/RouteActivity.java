package com.example.martin.gpstracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.example.martin.gpstracker.model.Route;
import com.example.martin.gpstracker.model.RouteModel;

public class RouteActivity extends AppCompatActivity {

    private Route currentRoute;
    private String routeName;
    private RouteModel routeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        //Get current route
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            currentRoute = (Route) b.getSerializable("currentRoute");
            routeModel = new RouteModel(this);

            if (currentRoute.getGeoPoints().size() != 0) {
                //Set name to route and save
                showRouteNameDialog();
            }
            else {
                showEmptyRouteDialog();
            }
        }
    }

    private void showRouteNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add name for this Route");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                routeName = input.getText().toString();
                currentRoute.setRouteName(routeName);
                routeModel.insertRouteDB(currentRoute);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        builder.show();
    }

    private void showEmptyRouteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This Route does not have GeoPoints, you can't save that.");

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
    }
}
