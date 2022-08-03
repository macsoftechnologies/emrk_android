package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;

import com.macsoftech.ekart.R;
import com.poovam.pinedittextfield.PinField;
import com.poovam.pinedittextfield.SquarePinField;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity {

    @BindView(R.id.squareField)
    SquarePinField squareField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register2);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        squareField.setOnTextCompleteListener(new PinField.OnTextCompleteListener() {
            @Override
            public boolean onTextComplete(String str) {
                if (str.equalsIgnoreCase("1234")) {
                    startActivity(new Intent(LoginRegisterActivity.this, DashboardActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
//        finish();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterContinueClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
//        finish();
    }
}