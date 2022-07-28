package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.macsoftech.ekart.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationContinuousActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continuous);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        startActivity(new Intent(this, OtpVerificationActivity.class));
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        finish();
    }
}