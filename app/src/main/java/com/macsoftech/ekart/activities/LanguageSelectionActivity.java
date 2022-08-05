package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.helper.SettingsPreferences;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSelectionActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

    }

    @OnClick(R.id.btn_continue)
    public void onContinueClick() {

        boolean isLoginSucces = SettingsPreferences.getBoolean(LanguageSelectionActivity.this,"LOGIN");
        if(isLoginSucces){
            startActivity(new Intent(this, OtpVerificationActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginRegisterActivity.class));
            finish();
        }

    }
}