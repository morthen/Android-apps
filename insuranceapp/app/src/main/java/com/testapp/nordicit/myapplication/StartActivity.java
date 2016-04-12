package com.testapp.nordicit.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    public void OnClickMain(View b) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
       // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}