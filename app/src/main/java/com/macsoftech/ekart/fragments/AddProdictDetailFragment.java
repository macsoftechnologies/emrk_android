package com.macsoftech.ekart.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.BaseActivity;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentAddproductdetailsBinding;
import com.macsoftech.ekart.helper.SettingsPreferences;
import com.macsoftech.ekart.model.LoginResponse;
import com.macsoftech.ekart.model.register.RegistrationRootResponse;
import com.macsoftech.ekart.model.search.GetUserResponseRoot;
import com.macsoftech.ekart.model.search.UserProdResponse;
import com.macsoftech.ekart.model.sizes.SizeModel;
import com.macsoftech.ekart.model.sizes.SizeModelRootResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.macsoftech.ekart.activities.BaseActivity.CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddProdictDetailFragment extends BaseFragment {

    UserProdResponse data;
    @BindView(R.id.sprsize)
    Spinner sprsize;

    @BindView(R.id.sprlocation)
    Spinner sprlocation;
    private FragmentAddproductdetailsBinding binding;

    public AddProdictDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addproductdetails, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binding = FragmentAddproductdetailsBinding.bind(view);
        data = getArguments().getParcelable("data");
        binding.txtProduct.setText(data.getProductName());
        if (!data.getProductImage().isEmpty())
            Glide.with(getActivity())
                    .load(RestApi.BASE_URL + data.getProductImage().get(0))
                    .into(binding.ivProduct);
        loadSizes();
        loadLocations();
        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashboardActivity) getActivity()).openCamera();
            }
        });
        binding.txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        binding.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void saveData() {
        Map<String, String> map = new HashMap<>();

        map.put("productId", data.getProductId());
        map.put("productName", data.getProductName());
        map.put("entityName", SettingsPreferences.getUser(getActivity()).getEntityName());
        map.put("mobileNum", SettingsPreferences.getUser(getActivity()).getMobileNum());
        map.put("quantity", binding.etQty.getText().toString());
        map.put("size", sprsize.getSelectedItem().toString());
        map.put("length", binding.etFeet.getText().toString() + "." + binding.etInch.getText().toString());
        map.put("location", sprlocation.getSelectedItem().toString());
        map.put("userId", SettingsPreferences.getUser(getActivity()).getUserId());

        showProgress();
        int index = 0;
        MultipartBody.Part[] images = new MultipartBody.Part[files.size()];
        for (String path : files) {
            MultipartBody.Part part = RestApi.prepareFilePart("productImage[" + index + "]", path, null);
            images[index] = part;
            index++;
        }
        RestApi.getInstance().getService()
                .addProduct(RestApi.prepareBodyPart(map),
                        images).enqueue(new Callback<RegistrationRootResponse>() {
            @Override
            public void onResponse(Call<RegistrationRootResponse> call, Response<RegistrationRootResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onFailure(Call<RegistrationRootResponse> call, Throwable t) {
                hideDialog();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        Compressor compressor = new Compressor(this.getActivity());
        final File file = compressor.compressToFile(((BaseActivity) getActivity()).photoFile);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addImage(file);
            }
        });
    }

    List<String> files = new ArrayList<>();

    private void addImage(File file) {
        ImageView view = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.row_add_product_image, binding.ivPlaceHolder, false);
        Glide.with(getActivity())
                .load(file)
                .into(view);
        files.add(file.getAbsolutePath());
        binding.ivPlaceHolder.addView(view);
    }

    private void loadLocations() {
        showProgress();
        Map<String, String> body = new HashMap<>();
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
                        ArrayAdapter l1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, user.getAvailableLocation());
                        l1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sprlocation.setAdapter(l1);
                        sprlocation.setPrompt("Location");
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

    private void loadSizes() {

        RestApi.getInstance().getService().getProductSizes()
                .enqueue(new Callback<SizeModelRootResponse>() {
                    @Override
                    public void onResponse(Call<SizeModelRootResponse> call, Response<SizeModelRootResponse> response) {
                        if (response.isSuccessful()) {
                            ArrayAdapter<SizeModel> ad = new ArrayAdapter<SizeModel>(getActivity(), android.R.layout.simple_spinner_item,
                                    response.body().getData().getSizesList());
                            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sprsize.setAdapter(ad);
                            sprsize.setPrompt("Size");
                        }
                    }

                    @Override
                    public void onFailure(Call<SizeModelRootResponse> call, Throwable t) {

                    }
                });
    }


}