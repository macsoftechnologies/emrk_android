package com.macsoftech.ekart.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.macsoftech.ekart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyEntityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEntityFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.tvcontacts)
    TextView tvcontacts;

    @BindView(R.id.tvlocation)
    TextView tvlocation;

    public MyEntityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyEntityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyEntityFragment newInstance(String param1, String param2) {
        MyEntityFragment fragment = new MyEntityFragment();
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
        return inflater.inflate(R.layout.fragment_my_entity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        tvcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactAlertDialog();
            }
        });

        tvlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAlertDialog();
            }
        });

    }


    private void addContactAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_entity_contact, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void locationAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alertdialog_entity_location, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        alert.setCancelable(true);
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}