package edu.csusb.wemo.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.csusb.wemo.R;

public class WemoSplashScreen extends AppCompatActivity {
    private static int TIME_OUT = 1000; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wemo_splash_screen);

        //final View myLayout = findViewById(R.id.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(WemoSplashScreen.this, WemoActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}

