package com.macsoftech.ekart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.LoginActivity;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentProfileBinding;
import com.macsoftech.ekart.helper.SettingsPreferences;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.search.GetUserResponseRoot;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {


    @BindView(R.id.tvlogout)
    TextView tvlogout;

    private FragmentProfileBinding binding;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binding = FragmentProfileBinding.bind(view);
        loadEntityDetails();
    }


    private void loadEntityDetails() {
        showProgress();
        Map<String, String> body = new HashMap<>();
//        body.put("userId", getArguments().getString("userId"));
        LoginResponse user = SettingsPreferences.getObject(getActivity(), SettingsPreferences.USER, LoginResponse.class);
        body.put("userId", user.getUserId());
//        body.put("userId", "7d415ca3-22f3-421b-9f4e-df261ea0a655");
        RestApi.getInstance().getService().getUser(body).enqueue(new Callback<GetUserResponseRoot>() {
            @Override
            public void onResponse(Call<GetUserResponseRoot> call, Response<GetUserResponseRoot> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    try {
                        LoginResponse user = response.body().getUserFeedbackResponse().get(0);
                        binding.ownername.setText(user.getEntityName());
                        binding.edtprofilename.setText(user.getFirstName() + " " + user.getLastName());
                        binding.txtLocation.setText(TextUtils.join(", ", user.getAvailableLocation()).toUpperCase());
//                        binding.txtMobile.setText(user.getMobileNum());
                        Glide.with(getActivity())
                                .load(RestApi.BASE_URL + user.getUserImage())
                                .error(R.drawable.entity_profile)
                                .into(binding.profileicon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponseRoot> call, Throwable t) {
                hideDialog();
            }
        });
    }

    @OnClick(R.id.tvlogout)
    public void onLogoutClick() {
        SettingsPreferences.saveBoolean(getActivity(), "LOGIN", false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


}