package com.example.gabriellefranca.mapas;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Loading extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        //loading screen
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent HomeIntent = new Intent(Loading.this,MapsActivity.class);
                startActivity(HomeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
