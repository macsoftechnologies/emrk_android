package com.macsoftech.ekart.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.BaseActivity;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.adapter.ComapnyNameAdapter;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentHomeSearchBinding;
import com.macsoftech.ekart.model.LocationData;
import com.macsoftech.ekart.model.LocationResponseRoot;
import com.macsoftech.ekart.model.search.ListOfVendorsData;
import com.macsoftech.ekart.model.search.ListOfVendorsResponse;
import com.macsoftech.ekart.model.search.SearchRootResponse;
import com.macsoftech.ekart.model.search.UserProdResponse;
import com.macsoftech.ekart.model.sizes.SizeModel;
import com.macsoftech.ekart.model.sizes.SizeModelRootResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeSearchFragment extends BaseFragment {

    @BindView(R.id.chip_group)
    ChipGroup chipGroup;

    ImageView iv_search;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.lengthlayout)
    LinearLayout lengthlayout;

    @BindView(R.id.locationlayout)
    LinearLayout locationlayout;

    List<ListOfVendorsData> list = new ArrayList<>();
    private FragmentHomeSearchBinding binding;

    public HomeSearchFragment() {
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
        return inflater.inflate(R.layout.fragment_home_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binding = FragmentHomeSearchBinding.bind(view);
        iv_search = view.findViewById(R.id.iv_search);
        //sizelayout = view.findViewById(R.id.sizelayout);
        // recyclerView = view.findViewById(R.id.recyclerView);

        binding.sizelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLength();
            }
        });
        binding.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                findByQty(editable.toString().trim());
            }
        });
//        binding.llQty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showQty();
//            }
//        });
        lengthlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthAlertDialog();
//                showLength();
            }
        });

        locationlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAlertDialog();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callSearchApi();
                String str = editable.toString().trim();
                if (str.length() > 0) {
                    chipGroup.setVisibility(View.VISIBLE);
                } else {
                    chipGroup.setVisibility(View.GONE);
                }
            }
        });
        //
//        SettingsPreferences.save
        chipGroup.setVisibility(View.GONE);
//        loadGroup();
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chipGroup.setVisibility(chipGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//            }
//        });
//        binding.txtLength.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLength();
//            }
//        });
    }

    private void findByQty(String value) {
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("quantity", value);
        RestApi.getInstance().getService()
                .getVendorProductByQuantity(map)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }

    @Override
    public void showProgress(String title) {
//        super.showProgress(title);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        super.hideDialog();
        binding.progressBar.setVisibility(View.GONE);
    }


    private void showLength() {
        RestApi.getInstance().getService().getProductSizes()
                .enqueue(new Callback<SizeModelRootResponse>() {
                    @Override
                    public void onResponse(Call<SizeModelRootResponse> call, Response<SizeModelRootResponse> response) {
                        if (response.isSuccessful()) {
                            showLengthPopup(response.body().getData().getSizesList());
                        }
                    }

                    @Override
                    public void onFailure(Call<SizeModelRootResponse> call, Throwable t) {

                    }
                });
    }

    public void showLengthPopup(List<SizeModel> sizesList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ArrayAdapter<SizeModel> adapter = new ArrayAdapter<SizeModel>(getActivity(), android.R.layout.simple_list_item_1,
                sizesList);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                callProductsSearch(sizesList.get(position).toString());
            }
        });
        builder.setTitle("Select Size");
        builder.setPositiveButton("Cancel", null);
        builder.create().show();
    }

    private void callProductsSearch(String size) {
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("size", size);
        RestApi.getInstance().getService().getVendorProductBySize(map)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }

    private void callSearchApi() {
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("productName", et_search.getText().toString().trim());
        RestApi.getInstance().getService().searchProducts(map)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getUserProdResponse());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }

    public void loadGroup(List<UserProdResponse> userProdResponse) {
        chipGroup.setVisibility(View.VISIBLE);
        chipGroup.clearCheck();
        chipGroup.removeAllViews();
        chipGroup.setSelectionRequired(false);
        ComapnyNameAdapter adapter = (ComapnyNameAdapter) recyclerView.getAdapter();
        if(adapter!=null){
            adapter.clear();
        }
        for (int i = 0; i < userProdResponse.size(); i++) {
            Chip chip1 = (Chip) LayoutInflater.from(getActivity()).inflate(R.layout.tag_cloud, chipGroup, false);
            String productCode = userProdResponse.get(i).getProductCode();
            if (productCode == null) {
                productCode = "";
            }
            chip1.setText(userProdResponse.get(i).getProductName() + " - " + productCode);
            chip1.setTag(userProdResponse.get(i));
            chip1.setId(i + 1);
            chipGroup.addView(chip1);
        }
        chipGroup.setOnCheckedChangeListener(null);
        chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            @SuppressLint("ResourceType") Chip chip = ((Chip) chipGroup.getChildAt(chipGroup.getCheckedChipId() - 1));
            if (chip != null) {
                // showPopUp(id, String.valueOf(chip.getTag()));
                chip.setChecked(false);
                chipAlertDialog(id, (UserProdResponse) chip.getTag());
            }
        });
    }

//    void showPopUp(int id, UserProdResponse tag) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Info");
//        builder.setMessage(tag.getProductName());
//        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                chipGroup.setVisibility(View.GONE);
//                // DashboardActivity activity = (DashboardActivity) getActivity();
////                activity.replaceBackStackFragment();
//                displayDetails(getActivity());
//
//            }
//        });
//        builder.setNegativeButton("Cancel", null);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    private void loadDetails(UserProdResponse item) {
        //
        showProgress();
        list = new ArrayList<>();
        ComapnyNameAdapter listAdapter = new ComapnyNameAdapter(list, getActivity());
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(listAdapter);
        listAdapter.onItemClickListener(clickListener);

        Map<String, String> body = new HashMap<>();
        body.put("productId", item.getProductId());
//        body.put("productId", "aba1f2d8-5c14-4008-b76f-ddf8746fd94f");
        RestApi.getInstance().getService()
                .getVendorProduct(body)
                .enqueue(new Callback<ListOfVendorsResponse>() {
                    @Override
                    public void onResponse(Call<ListOfVendorsResponse> call, Response<ListOfVendorsResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            list.addAll(response.body().getData());
                            listAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListOfVendorsResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            ListOfVendorsData item = list.get(position);
            //  Intent intent = new Intent(getActivity(), TestActivity.class);
//            intent.putExtra("brandLogo", item.getLogo());
//            intent.putExtra("brandName", item.getBrandName());
//            intent.putExtra("data", item);
            //startActivity(intent);
            Fragment fragment = new SearchEntityDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable("data", item);
            fragment.setArguments(args);
            DashboardActivity activity = (DashboardActivity) getActivity();
            activity.replaceBackStackFragment(fragment);

            //displayDetails(getActivity());


        }
    };


    /**
     * length dialog
     */
    private void lengthAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alertdialog_size, null);
        //
        EditText txt_max_num = alertView.findViewById(R.id.txt_max_num);
        EditText txt_max_dec = alertView.findViewById(R.id.txt_max_dec);
        EditText txt_min_num = alertView.findViewById(R.id.txt_min_num);
        EditText txt_min_dec = alertView.findViewById(R.id.txt_min_dec);
        //
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertView);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.show();

        alertView.findViewById(R.id.linearcancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        alertView.findViewById(R.id.linearok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BaseActivity.hideKeyboard(getActivity());
                String max_num = txt_max_num.getText().toString().trim();
                String max_dec = txt_max_dec.getText().toString().trim();
                String min_num = txt_min_num.getText().toString().trim();
                String min_dec = txt_min_dec.getText().toString().trim();
                if (TextUtils.isEmpty(max_num)) {
                    max_num = "0";
                }
                if (TextUtils.isEmpty(max_dec)) {
                    max_dec = "0";
                }
                if (TextUtils.isEmpty(min_num)) {
                    min_num = "0";
                }
                if (TextUtils.isEmpty(min_dec)) {
                    min_dec = "0";
                }

                filterProductsBySize(max_num + "." + max_dec, min_num + "." + min_dec);
            }
        });

        alertView.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void filterProductsBySize(String max, String min) {
        showProgress();
        RestApi.getInstance().getService().getVendorProductByLength(min, max)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }

    List<LocationData> mLocationData = new ArrayList<>();

    /**
     * location dialog
     */
    private void locationAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_location, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(states));
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
                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, new ArrayList<>());
                sp_mandal.setAdapter(emptyAdapter);
                sp_village.setAdapter(emptyAdapter);
                //
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(districts));
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(districts));
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(districts));
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
                String state = spState.getSelectedItem() == null ? "" : spState.getSelectedItem().toString();
                String district = spDistrict.getSelectedItem().toString().equalsIgnoreCase("--Select--")
                        ? "" : spDistrict.getSelectedItem().toString();
                String mandal = sp_mandal.getSelectedItem().toString().equalsIgnoreCase("--Select--") ? ""
                        : sp_mandal.getSelectedItem().toString();
                String village = sp_village.getSelectedItem().toString().equalsIgnoreCase("--Select--") ? ""
                        : sp_village.getSelectedItem().toString();
                findByLocation(state, district, mandal, village);

            }
        });
        alertLayout.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    void findByLocation(String state, String district, String mandal, String village) {
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("district", district);
        map.put("mandal", mandal);
        map.put("village", village);
        map.put("location", village);
        RestApi.getInstance().getService()
                .getVendorProductByLocation(map)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        hideDialog();
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {
                        hideDialog();
                    }
                });
    }


    /**
     * length dialog
     *
     * @param id
     * @param item
     */
    private void chipAlertDialog(int id, UserProdResponse item) {
        BaseActivity.hideKeyboard(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_chip, null);
        ImageView iv_product = alertLayout.findViewById(R.id.iv_product);
        TextView txt_title = alertLayout.findViewById(R.id.txt_title);
        txt_title.setText(item.getProductName());
        if (!item.getProductImage().isEmpty()) {
            String image = item.getProductImage().get(0);
            if (image.contains(",")) {
                image = image.split(",")[0];
            }
            Glide.with(getActivity())
                    .load(RestApi.BASE_URL + image)
                    .into(iv_product);
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
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
//                displayDetails(getActivity(), item);
                loadDetails(item);
                chipGroup.setVisibility(View.GONE);
            }
        });

        alertLayout.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}