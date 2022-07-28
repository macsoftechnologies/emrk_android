package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.macsoftech.ekart.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpVerificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    //btn_login
    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}