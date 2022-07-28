package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.macsoftech.ekart.R;

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
        startActivity(new Intent(this, LoginRegisterActivity.class));
        finish();
    }
}