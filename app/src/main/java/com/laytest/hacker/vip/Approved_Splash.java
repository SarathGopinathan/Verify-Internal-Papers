package com.laytest.hacker.vip;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Approved_Splash extends AppCompatActivity {
    static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved__splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(Approved_Splash.this, HoD.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
