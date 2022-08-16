package com.macsoftech.ekart.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.macsoftech.ekart.model.search.ListOfVendorsData;
import com.macsoftech.ekart.model.search.ListOfVendorsResponse;
import com.macsoftech.ekart.model.search.SearchRootResponse;
import com.macsoftech.ekart.model.search.UserProdResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        iv_search = view.findViewById(R.id.iv_search);
        //sizelayout = view.findViewById(R.id.sizelayout);
        // recyclerView = view.findViewById(R.id.recyclerView);

        lengthlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthAlertDialog();
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
    }

    private void callSearchApi() {
        Map<String, String> map = new HashMap<>();
        map.put("productName", et_search.getText().toString().trim());
        RestApi.getInstance().getService().searchProducts(map)
                .enqueue(new Callback<SearchRootResponse>() {
                    @Override
                    public void onResponse(Call<SearchRootResponse> call, Response<SearchRootResponse> response) {
                        if (response.isSuccessful()) {
                            loadGroup(response.body().getUserProdResponse());
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRootResponse> call, Throwable t) {

                    }
                });
    }

    public void loadGroup(List<UserProdResponse> userProdResponse) {
        //chip_group
        chipGroup.clearCheck();
        chipGroup.removeAllViews();
        chipGroup.setSelectionRequired(false);
        for (int i = 0; i < userProdResponse.size(); i++) {
            Chip chip1 = (Chip) LayoutInflater.from(getActivity()).inflate(R.layout.tag_cloud, chipGroup, false);
            chip1.setText(userProdResponse.get(i).getProductName() + " - " + userProdResponse.get(i).getProductCode());
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
                        if (response.isSuccessful()) {
                            list.addAll(response.body().getData());
                            listAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListOfVendorsResponse> call, Throwable t) {

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
        View alertLayout = inflater.inflate(R.layout.alertdialog_size, null);
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
            }
        });

        alertLayout.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * location dialog
     */
    private void locationAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_location, null);
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
            }
        });
        alertLayout.findViewById(R.id.ivdelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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