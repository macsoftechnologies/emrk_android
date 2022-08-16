package com.macsoftech.ekart.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentEntityProductDetailBinding;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.search.GetUserResponseRoot;
import com.macsoftech.ekart.model.search.UserProdResponse;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EntityDetailsFragment extends BaseFragment {

    UserProdResponse data;
    private FragmentEntityProductDetailBinding binding;

    public EntityDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entity_product_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binding = FragmentEntityProductDetailBinding.bind(view);
        data = getArguments().getParcelable("data");
        view.findViewById(R.id.txtviewentity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DashboardActivity activity = (DashboardActivity) getActivity();
//                activity.replaceBackStackFragment(new SearchEntityProductNameFragment());
            }
        });

        view.findViewById(R.id.txtviewentity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity activity = (DashboardActivity) getActivity();
                activity.replaceBackStackFragment(new SearchEntityProductNameFragment());

            }
        });
        view.findViewById(R.id.txt_view_contacts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactAlertDialog();
            }
        });

        view.findViewById(R.id.txt_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+91987654321";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });


        binding.txtProduct.setText(data.getProductName());
        binding.txtQty.setText(": " + data.getQuantity());
        binding.txtSize.setText(": " + data.getSize());
        binding.txtLocation.setText(": " + data.getLocation());
        binding.txtLength.setText(": " + data.getLength());

        if (!data.getProductImage().isEmpty()) {
            Glide.with(getActivity())
                    .load(RestApi.BASE_URL + data.getProductImage().get(0))
                    .into(binding.ivProduct);
        }

        loadEntityDetails();
    }

    private void loadEntityDetails() {
        Map<String, String> body = new HashMap<>();
        body.put("userId", data.getUserId());
        RestApi.getInstance().getService().getUser(body).enqueue(new Callback<GetUserResponseRoot>() {
            @Override
            public void onResponse(Call<GetUserResponseRoot> call, Response<GetUserResponseRoot> response) {
                if (response.isSuccessful()) {
                    try {
                        LoginResponse user = response.body().getUserFeedbackResponse().get(0);
                        binding.txtEntity.setText(user.getEntityName());
                        binding.txtVendorName.setText(user.getFirstName() + " " + user.getLastName());
                        binding.txtMobile.setText(user.getMobileNum());
                        Glide.with(getActivity())
                                .load(RestApi.BASE_URL + user.getEntityImage())
                                .error(R.drawable.entity_profile)
                                .into(binding.ivEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponseRoot> call, Throwable t) {

            }
        });
    }

    private void addContactAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_entity_contact, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();
    }


}