package com.testapp.nordicit.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import camupload.CameraActivity;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    DropDown companiesDropDown;
    DropDown numbersDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        companiesDropDown = (DropDown) findViewById(R.id.main_companies_dropDown);

        numbersDropDown = (DropDown) findViewById(R.id.main_numbers_dropDown);
        // Set the items of the dropdown
        companiesDropDown.setItems(R.array.numbers);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_show_values_button:
                String toastMessage = "Choosen companie: ";
                // Get the string value of the selected item
                // Null will be returned, if an item has not yet been selected
                String countryString = companiesDropDown.getSelectedItemString();
                toastMessage += countryString == null ? getResources().getString(R.string.none) : countryString;

                toastMessage += "\nChoosen number: ";
                // Get the selected item
                Object city = numbersDropDown.getSelectedItem();
                // If an item was selected, get its string value
                toastMessage += city == null ? getResources().getString(R.string.none) : city.toString();

                // Show the selected values in a Toast
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }}

    public void OnClickCamera(View b) {
        Intent it = new Intent(this, CameraActivity.class);
        startActivity(it);


    }


}
