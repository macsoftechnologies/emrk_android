package com.macsoftech.ekart.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentSearchEntityProductDetailBinding;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.search.GetUserResponseRoot;
import com.macsoftech.ekart.model.search.ListOfVendorsData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchEntityDetailFragment extends BaseFragment {

    FragmentSearchEntityProductDetailBinding binding;
    ListOfVendorsData data;

    public SearchEntityDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_entity_product_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = getArguments().getParcelable("data");
        binding = FragmentSearchEntityProductDetailBinding.bind(view);
        view.findViewById(R.id.txtviewentity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity activity = (DashboardActivity) getActivity();
                Fragment fragment = new SearchEntityProductNameFragment();
                Bundle args = new Bundle();
                args.putString("userId", data.getUserId());
                fragment.setArguments(args);
                activity.replaceBackStackFragment(fragment);

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
                if (currentUser != null) {
                    String phone = currentUser.getMobileNum();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }

            }
        });


        binding.txtProduct.setText(data.getProductName());
        binding.txtQty.setText(": " + data.getQuantity());
        binding.txtSize.setText(": " + data.getSize());
        binding.txtLocation.setText(": " + data.getLocation());
        binding.txtLength.setText(": " + data.getLength());

        if (!data.getProductImage().isEmpty()) {
            String image = data.getProductImage().get(0);
            if (image.contains(",")) {
                image = image.split(",")[0];
            }
            Glide.with(getActivity())
                    .load(RestApi.BASE_URL + image)
                    .into(binding.ivProduct);
        }

        loadEntityDetails();

    }

    LoginResponse currentUser;

    private void loadEntityDetails() {
        Map<String, String> body = new HashMap<>();
        body.put("userId", data.getUserId());
        RestApi.getInstance().getService().getUser(body).enqueue(new Callback<GetUserResponseRoot>() {
            @Override
            public void onResponse(Call<GetUserResponseRoot> call, Response<GetUserResponseRoot> response) {
                if (response.isSuccessful()) {
                    try {
                        currentUser = response.body().getUserFeedbackResponse().get(0);
                        binding.txtEntity.setText(currentUser.getEntityName());
                        binding.txtVendorName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
                        binding.txtMobile.setText(currentUser.getMobileNum());
                        Glide.with(getActivity())
                                .load(RestApi.BASE_URL + currentUser.getEntityImage())
                                .error(R.drawable.entity_profile)
                                .into(binding.ivEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.txtEntity.setText("");
                        binding.txtVendorName.setText("");
                        binding.txtMobile.setText("");
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
        LinearLayout ll_contacts = alertLayout.findViewById(R.id.ll_contacts);
        if (currentUser != null) {
            String[] contacts = new String[]{
                    currentUser.getMobileNum(),
                    currentUser.getAltNumber()
            };
            for (int i = 1; i <= 2; i++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_contacts, null);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView txt_mobile = view.findViewById(R.id.txt_mobile);
                txt_mobile.setText(contacts[i - 1]);
                tv_name.setText(i + ". " + currentUser.getFirstName() + " " + currentUser.getLastName());
                ll_contacts.addView(view);
            }
        }
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}