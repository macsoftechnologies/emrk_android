package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.helper.SettingsPreferences;
import com.poovam.pinedittextfield.PinField;
import com.poovam.pinedittextfield.SquarePinField;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpVerificationActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btn_login;


    @BindView(R.id.squareField)
    SquarePinField squareField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        squareField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
            @Override
            public boolean onTextComplete(String str) {
                if (str.equalsIgnoreCase("1234")) {
                    startActivity(new Intent(OtpVerificationActivity.this, DashboardActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

        boolean isLoginSucces = SettingsPreferences.getBoolean(OtpVerificationActivity.this,"LOGIN");
        if(isLoginSucces){
            btn_login.setVisibility(View.GONE);
        }else{
            btn_login.setVisibility(View.VISIBLE);
        }

    }

    //btn_login
    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}