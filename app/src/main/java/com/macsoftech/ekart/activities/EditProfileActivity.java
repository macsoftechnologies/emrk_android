package com.macsoftech.ekart.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.ActivityEditProfileBinding;
import com.macsoftech.ekart.helper.SettingsPreferences;
import com.macsoftech.ekart.model.LocationData;
import com.macsoftech.ekart.model.LocationResponseRoot;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.search.GetUserResponseRoot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity {

    private ActivityEditProfileBinding binding;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(binding.getRoot());
        loadData();
        binding.etPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationAlertDialog(binding.etPrimary);
            }
        });
        binding.txtAddAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationAlertDialog(null);
            }
        });
        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isProfile = true;
//                openCamera();
                showToast("Profile pic updating is restricted.");
            }
        });

        binding.ivEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isProfile = false;
//                openCamera();
                showToast("Entity pic updating is restricted.");
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    updateProfile();
                    isEdit = false;
                    binding.btnUpdate.setText("EDIT");
                    enableUIComponents();
                } else {
                    isEdit = true;
                    binding.btnUpdate.setText("SUBMIT");
                    enableUIComponents();
                }

            }
        });
        enableUIComponents();
    }

    private void enableUIComponents() {
        binding.etEntityName.setEnabled(isEdit);
        binding.etFirstName.setEnabled(isEdit);
        binding.etLastName.setEnabled(isEdit);
        binding.etPrimary.setEnabled(isEdit);
        binding.etMobile.setEnabled(isEdit);
        binding.etAltMobile.setEnabled(isEdit);
        binding.etEmail.setEnabled(isEdit);
    }


    private void loadData() {
        showProgress();
        Map<String, String> body = new HashMap<>();
        LoginResponse user = SettingsPreferences.getObject(this, SettingsPreferences.USER, LoginResponse.class);
        body.put("userId", user.getUserId());
        RestApi.getInstance().getService().getUser(body).enqueue(new Callback<GetUserResponseRoot>() {
            @Override
            public void onResponse(Call<GetUserResponseRoot> call, Response<GetUserResponseRoot> response) {
                hideDialog();
//                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {
                        LoginResponse user = response.body().getUserFeedbackResponse().get(0);
                        binding.etEntityName.setText(user.getEntityName());
                        binding.etFirstName.setText(user.getFirstName());
                        binding.etLastName.setText(user.getLastName());
                        binding.etPrimary.setText(user.getPrimaryLocation());
                        binding.etMobile.setText(user.getMobileNum());
                        binding.etAltMobile.setText(user.getAltNumber());
                        binding.etEmail.setText(user.getEmailId());
//                        binding.txtMobile.setText(user.getMobileNum());
                        Glide.with(EditProfileActivity.this)
                                .load(RestApi.BASE_URL + user.getUserImage())
                                .error(R.drawable.entity_profile)
                                .into(binding.ivProfile);
                        Glide.with(EditProfileActivity.this)
                                .load(RestApi.BASE_URL + user.getEntityImage())
                                .error(R.drawable.entity_profile)
                                .into(binding.ivEntity);
                        for (String location : user.getAvailableLocation()) {
                            addNewLocation(location);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponseRoot> call, Throwable t) {
                hideDialog();
//                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void onRegisterClick() {


//        Intent intent = new Intent(this, EditProfileActivity.class);
//        //
//        intent.putExtra("firstName", binding.etFirstName.getText().toString());
//        intent.putExtra("lastName", binding.etLastName.getText().toString());
//        intent.putExtra("mobileNum", binding.etMobile.getText().toString());
//        intent.putExtra("altNumber", binding.etAltMobile.getText().toString());
//        intent.putExtra("password", binding.etPwd.getText().toString());
//        intent.putExtra("emailId", binding.etEmail.getText().toString());
//        intent.putExtra("chooseLanguage", SettingsPreferences.getString(this, "lang"));
//        startActivity(intent);
    }

    private void updateProfile() {
        if (TextUtils.isEmpty(binding.etFirstName.getText().toString())) {
            showToast("Enter First name");
            return;
        }
        if (TextUtils.isEmpty(binding.etLastName.getText().toString())) {
            showToast("Enter Last name");
            return;
        }
        if (TextUtils.isEmpty(binding.etMobile.getText().toString())) {
            showToast("Enter Mobile Number");
            return;
        }
        if (TextUtils.isEmpty(binding.etAltMobile.getText().toString())) {
            showToast("Enter Alternative Mobile Number");
            return;
        }

        if (TextUtils.isEmpty(binding.etEmail.getText().toString())) {
            showToast("Enter Email Address");
            return;
        }
        if (TextUtils.isEmpty(binding.etEntityName.getText().toString())) {
            showToast("Enter entity name");
            return;
        }
        if (TextUtils.isEmpty(binding.etPrimary.getText().toString())) {
            showToast("Select Primary Location");
            return;
        }
        showProgress();
        Map<String, Object> body = new HashMap<>();
        LoginResponse user = SettingsPreferences.getObject(this, SettingsPreferences.USER, LoginResponse.class);
        body.put("userId", user.getUserId());
        body.put("firstName", binding.etFirstName.getText().toString());
        body.put("lastName", binding.etLastName.getText().toString());
        body.put("mobileNum", binding.etMobile.getText().toString());
        body.put("altNumber", binding.etAltMobile.getText().toString());
        body.put("emailId", binding.etEmail.getText().toString());
        body.put("entityName", binding.etEntityName.getText().toString());
        body.put("primaryLocation", binding.etPrimary.getText().toString());
        String[] availableLocation = new String[binding.llAnotherLocations.getChildCount()];
        if (binding.llAnotherLocations.getChildCount() > 0) {
            for (int i = 0; i < binding.llAnotherLocations.getChildCount(); i++) {
                EditText location = (EditText) binding.llAnotherLocations.getChildAt(i);
                availableLocation[i] = location.getText().toString();
//                body.put("availableLocation[" + i + "]", location.getText().toString());
            }
        }
        if (availableLocation.length != 0) {
            body.put("availableLocation", availableLocation);
        }
        RestApi.getInstance().getService().updateUser(body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            showToast("Updated Successfully");
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        hideDialog();
                    }
                });
    }

    List<LocationData> mLocationData = new ArrayList<>();

    /**
     * location dialog
     */
    private void locationAlertDialog(EditText editText) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_location, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        //
        Spinner spState = alertLayout.findViewById(R.id.sp_state);
        Spinner spDistrict = alertLayout.findViewById(R.id.sp_district);
        Spinner sp_mandal = alertLayout.findViewById(R.id.sp_mandal);
        Spinner sp_village = alertLayout.findViewById(R.id.sp_village);
        //
        RestApi.getInstance().getService().getLocations().enqueue(new Callback<LocationResponseRoot>() {
            @Override
            public void onResponse(Call<LocationResponseRoot> call, Response<LocationResponseRoot> response) {
                if (response.isSuccessful()) {
                    LocationResponseRoot res = response.body();
                    TreeSet<String> states = new TreeSet<>();
                    mLocationData = res.getData();
                    for (LocationData locationData : mLocationData) {
                        if (locationData.getState() != null) {
                            states.add(locationData.getState());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(states));
                    spState.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LocationResponseRoot> call, Throwable t) {

            }
        });
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TreeSet<String> districts = new TreeSet<>();
                districts.add("--Select--");
                for (LocationData locationData : mLocationData) {
                    if (spState.getSelectedItem().toString().equalsIgnoreCase(locationData.getState())) {
                        if (locationData.getDistrict() != null) {
                            districts.add(locationData.getDistrict());
                        }
                    }

                }
                //
                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(EditProfileActivity.this,
                        android.R.layout.simple_list_item_1, new ArrayList<>());
                sp_mandal.setAdapter(emptyAdapter);
                sp_village.setAdapter(emptyAdapter);
                //
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(districts));
                spDistrict.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TreeSet<String> districts = new TreeSet<>();
                districts.add("--Select--");
                for (LocationData locationData : mLocationData) {
                    if (spDistrict.getAdapter().getItem(i).toString().equalsIgnoreCase(
                            locationData.getDistrict())) {
                        if (locationData.getMandal() != null) {
                            districts.add(locationData.getMandal());
                        }

                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(districts));
                sp_mandal.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TreeSet<String> districts = new TreeSet<>();
                districts.add("--Select--");
                for (LocationData locationData : mLocationData) {
                    if (sp_mandal.getAdapter().getItem(i).toString().equalsIgnoreCase(
                            locationData.getMandal())) {
                        if (locationData.getVillage() != null) {
                            districts.add(locationData.getVillage());
                        }
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_list_item_1, new ArrayList<>(districts));
                sp_village.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //

        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.show();

        alertLayout.findViewById(R.id.linearcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        alertLayout.findViewById(R.id.linearok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String village = sp_village.getSelectedItem().toString().equalsIgnoreCase("--Select--") ? ""
                        : sp_village.getSelectedItem().toString();
                if (!TextUtils.isEmpty(village)) {
                    if (editText != null) {
                        editText.setText(village);
                    } else {
                        addNewLocation(village);
                    }
                }

            }
        });
        alertLayout.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void addNewLocation(String village) {
        EditText editText = (EditText) LayoutInflater.from(this)
                .inflate(R.layout.row_other_locations, binding.llAnotherLocations, false);
        editText.setText(village);
        editText.setEnabled(false);
        binding.llAnotherLocations.addView(editText);
    }
}