package com.macsoftech.ekart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.helper.SettingsPreferences;
import com.macsoftech.ekart.model.LoginRootResponse;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etmail)
    EditText etmail;

    @BindView(R.id.etpwd)
    EditText etpwd;

    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        boolean isLoginSucces = SettingsPreferences.getBoolean(LoginActivity.this, "LOGIN");
        if (isLoginSucces) {
            btn_login.setVisibility(View.GONE);
        } else {
            btn_login.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
//        Intent intent = new Intent(this, DashboardActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();

        loginProcess();

    }

    private void loginProcess() {

        String emailId = etmail.getText().toString();
        String pwd = etpwd.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("emailId", emailId);
        map.put("password", pwd);
        //"emailId":"gowthami@gmail.com",
        //     "password":"gfdsdf"

        RestApi.getInstance().getService().login(map).enqueue(new Callback<LoginRootResponse>() {

            @Override
            public void onResponse(Call<LoginRootResponse> call, Response<LoginRootResponse> response) {
//                String result = response.body().toString();
                if (response.isSuccessful()) {
                    SettingsPreferences.saveBoolean(LoginActivity.this, "LOGIN", true);
                    SettingsPreferences.saveObject(LoginActivity.this, "user", response.body().loginRes);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<LoginRootResponse> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}