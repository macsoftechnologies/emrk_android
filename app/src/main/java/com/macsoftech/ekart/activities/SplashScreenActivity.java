package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.macsoftech.ekart.R;


public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(SplashScreenActivity.this, LanguageSelectionActivity.class);
//            startActivity(intent);
//            finish();
//        }, 1000);


        Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
        startActivity(intent);



    }
}