package com.macsoftech.ekart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.LoginActivity;
import com.macsoftech.ekart.helper.SettingsPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProdictDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProdictDetailFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String[] size = { "size1", "size2",
            "size3", "size4",
            "size5" };

    String[] location = { "location1", "location2",
            "location3", "location4",
            "location5" };

    @BindView(R.id.sprsize)
    Spinner sprsize;

    @BindView(R.id.sprlocation)
    Spinner sprlocation;

    public AddProdictDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProdictDetailFragment newInstance(String param1, String param2) {
        AddProdictDetailFragment fragment = new AddProdictDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        ArrayAdapter ad = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, size);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprsize.setAdapter(ad);
        sprsize.setPrompt("Size");

        sprsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter l1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, location);
        l1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprlocation.setAdapter(l1);
        sprlocation.setPrompt("Location");

        sprlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}