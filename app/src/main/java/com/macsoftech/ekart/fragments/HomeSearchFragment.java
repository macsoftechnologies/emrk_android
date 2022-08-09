package com.macsoftech.ekart.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.activities.TestActivity;
import com.macsoftech.ekart.adapter.ComapnyNameAdapter;
import com.macsoftech.ekart.model.CompanyName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeSearchFragment extends BaseFragment {

    @BindView(R.id.chip_group)
    ChipGroup chipGroup;

    ImageView iv_search;
    EditText et_search;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.lengthlayout)
    LinearLayout lengthlayout;

    @BindView(R.id.locationlayout)
    LinearLayout locationlayout;

    List<CompanyName>   list ;

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
        ButterKnife.bind(this,view);

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

        et_search = view.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
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
        loadGroup();
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.setVisibility(chipGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    public void loadGroup() {
        //chip_group
        chipGroup.setSelectionRequired(false);
        for (int i = 0; i < 7; i++) {
            Chip chip1 = (Chip) LayoutInflater.from(getActivity()).inflate(R.layout.tag_cloud, chipGroup, false);
            chip1.setText("Adidas " + (i + 1));
            chip1.setTag(chip1.getText());
            chip1.setId(i + 1);
            chipGroup.addView(chip1);
        }

        chipGroup.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = ((Chip) chipGroup.getChildAt(chipGroup.getCheckedChipId() - 1));
            if (chip != null) {
               // showPopUp(id, String.valueOf(chip.getTag()));
                chipAlertDialog(id, String.valueOf(chip.getTag()));
            }
        });
    }

    void showPopUp(int id, String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Info");
        builder.setMessage(tag);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chipGroup.setVisibility(View.GONE);
                // DashboardActivity activity = (DashboardActivity) getActivity();
//                activity.replaceBackStackFragment();
                   displayDetails(getActivity());

            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void displayDetails(Context mContext) {

         list = new ArrayList<CompanyName>();
         CompanyName campanyName = new CompanyName();
        campanyName.setCompanyName("Skml Pt Ltd");
        campanyName.setMobileNo("999998766");
        campanyName.setQty("9999");
        list.add(campanyName);


        CompanyName campanyName2 = new CompanyName();
        campanyName2.setCompanyName("Skml Pt Ltd");
        campanyName2.setMobileNo("999998766");
        campanyName2.setQty("9999");
        list.add(campanyName2);

        CompanyName campanyName3 = new CompanyName();
        campanyName3.setCompanyName("Skml Pt Ltd");
        campanyName3.setMobileNo("999998766");
        campanyName3.setQty("9999");
        list.add(campanyName3);

        CompanyName campanyName4 = new CompanyName();
        campanyName4.setCompanyName("Skml Pt Ltd");
        campanyName4.setMobileNo("999998766");
        campanyName4.setQty("9999");
        list.add(campanyName4);

        CompanyName campanyName5 = new CompanyName();
        campanyName5.setCompanyName("Skml Pt Ltd");
        campanyName5.setMobileNo("999998766");
        campanyName5.setQty("9999");
        list.add(campanyName5);

        ComapnyNameAdapter listAdapter = new ComapnyNameAdapter(list, mContext);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(listAdapter);
        listAdapter.onItemClickListener(clickListener);
    }



    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            //CompanyName item = list.get(position);
          //  Intent intent = new Intent(getActivity(), TestActivity.class);
//            intent.putExtra("brandLogo", item.getLogo());
//            intent.putExtra("brandName", item.getBrandName());
//            intent.putExtra("data", item);
            //startActivity(intent);

             DashboardActivity activity = (DashboardActivity) getActivity();
             activity.replaceBackStackFragment(new SearchEntityDetailFragment());

            //displayDetails(getActivity());


        }
    };


    /**
     * length dialog
     */
    private void lengthAlertDialog(){

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
    private void locationAlertDialog(){

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
     * @param id
     * @param s
     */
    private void chipAlertDialog(int id, String s){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_chip, null);
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
                displayDetails(getActivity());
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