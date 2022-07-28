package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.macsoftech.ekart.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}