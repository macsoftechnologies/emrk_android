package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.helper.SettingsPreferences;


public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            moveToNext();
        }, 1000);


//        Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
//        startActivity(intent);


    }

    private void moveToNext() {
        boolean isLoginSucces = SettingsPreferences.getBoolean(this, "LOGIN");
        if (isLoginSucces) {
            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashScreenActivity.this, LanguageSelectionActivity.class);
            startActivity(intent);
            finish();
        }

    }
}