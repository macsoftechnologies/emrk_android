package com.macsoftech.ekart.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.ActivityRegistrationContinuousBinding;
import com.macsoftech.ekart.model.CommonErrorResponse;
import com.macsoftech.ekart.model.register.RegistrationRootResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationContinuousActivity extends BaseActivity {

    private ActivityRegistrationContinuousBinding binding;
    private boolean isProfile;
    private File profile;
    private File entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration_continuous);
        binding = ActivityRegistrationContinuousBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ButterKnife.bind(this, binding.getRoot());
        getSupportActionBar().hide();

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isProfile = true;
                openCamera();
            }
        });

        binding.ivEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isProfile = false;
                openCamera();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300
                && resultCode == Activity.RESULT_OK
        ) {
            new Thread(() -> {
                try {
                    compressFile();
//                    runOnUiThread(() -> setData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }

    private void compressFile() throws IOException {
        Compressor compressor = new Compressor(this.getApplicationContext());
        final File file = compressor.compressToFile(((BaseActivity) this).photoFile);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isProfile) {
                    profile = file;
                    Glide.with(RegistrationContinuousActivity.this)
                            .load(file)
                            .into(binding.ivProfile);
                } else {
                    entity = file;
                    Glide.with(RegistrationContinuousActivity.this)
                            .load(file)
                            .into(binding.ivEntity);
                }
            }
        });
    }


    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        Bundle extras = getIntent().getExtras();
        Map<String, String> map = new HashMap<>();
        for (String key : extras.keySet()) {
            map.put(key, extras.getString(key));
        }
        map.put("entityName", binding.etEntityName.getText().toString());
        map.put("primaryLocation", binding.etPrimary.getText().toString());
        String[] str = binding.etLocation.getText().toString().split(",");
        if (str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                map.put("availableLocation[" + i + "]", str[i].trim());
            }
        }

        showProgress();
        RestApi.getInstance().getService().register(
                RestApi.prepareFilePart("userImage", profile.getAbsolutePath(), null),
                RestApi.prepareFilePart("entityImage", entity.getAbsolutePath(), null),
                RestApi.prepareBodyPart(map)
        ).enqueue(new Callback<RegistrationRootResponse>() {
            @Override
            public void onResponse(Call<RegistrationRootResponse> call, Response<RegistrationRootResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    startActivity(new Intent(RegistrationContinuousActivity.this, OtpVerificationActivity.class));
                    finish();
                } else {
                    CommonErrorResponse errorResponse = new Gson().fromJson(response.errorBody().charStream(), CommonErrorResponse.class);
                    showToast(errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RegistrationRootResponse> call, Throwable t) {
                hideDialog();
                showToast("Failed to register.");
            }
        });


        //=====
//        startActivity(new Intent(this, OtpVerificationActivity.class));
    }

    @OnClick(R.id.btn_back)
    public void onBackClick() {
        finish();
    }
}