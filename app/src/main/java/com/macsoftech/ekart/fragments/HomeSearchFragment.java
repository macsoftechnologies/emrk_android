package com.macsoftech.ekart.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.helper.SettingsPreferences;

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
                showPopUp(id, String.valueOf(chip.getTag()));
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
                DashboardActivity activity = (DashboardActivity) getActivity();
//                activity.replaceBackStackFragment();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}