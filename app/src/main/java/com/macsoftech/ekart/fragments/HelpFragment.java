package com.macsoftech.ekart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.activities.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Nullable
    @BindView(R.id.txt_header_1)
    TextView txt_header_1;

    @Nullable
    @BindView(R.id.txt_header_2)
    TextView txt_header_2;

    @Nullable
    @BindView(R.id.txt_header_3)
    TextView txt_header_3;

    @Nullable
    @BindView(R.id.txt_header_4)
    TextView txt_header_4;

    @Nullable
    @BindView(R.id.txt_header_5)
    TextView txt_header_5;

    @Nullable
    @BindView(R.id.ll_content_1)
    LinearLayout ll_content_1;

    @Nullable
    @BindView(R.id.ll_content_2)
    LinearLayout ll_content_2;

    @Nullable
    @BindView(R.id.ll_content_3)
    LinearLayout ll_content_3;

    @Nullable
    @BindView(R.id.ll_content_4)
    LinearLayout ll_content_4;

    @Nullable
    @BindView(R.id.ll_content_5)
    LinearLayout ll_content_5;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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
        View view= inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(getActivity(),view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      ll_content_1 =   view.findViewById(R.id.ll_content_1);
      ll_content_2 =   view.findViewById(R.id.ll_content_2);
      ll_content_3 =   view.findViewById(R.id.ll_content_3);
      ll_content_4 =   view.findViewById(R.id.ll_content_4);
      ll_content_5 =   view.findViewById(R.id.ll_content_5);
        view.findViewById(R.id.txt_header_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontxtHeader1Click();
            }
        });


        view.findViewById(R.id.txt_header_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontxtHeader2Click();
            }
        });


        view.findViewById(R.id.txt_header_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontxtHeader3Click();
            }
        });

        view.findViewById(R.id.txt_header_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontxtHeader4Click();
            }
        });

        view.findViewById(R.id.txt_header_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ontxtHeader5Click();
            }
        });
    }

    @Optional
    @OnClick(R.id.txt_header_1)
    public void ontxtHeader1Click() {
        ll_content_1.setVisibility(View.VISIBLE);
        ll_content_2.setVisibility(View.GONE);
        ll_content_3.setVisibility(View.GONE);
        ll_content_4.setVisibility(View.GONE);
        ll_content_5.setVisibility(View.GONE);
    }


    @Optional
    @OnClick(R.id.txt_header_2)
    public void ontxtHeader2Click() {
        ll_content_1.setVisibility(View.GONE);
        ll_content_2.setVisibility(View.VISIBLE);
        ll_content_3.setVisibility(View.GONE);
        ll_content_4.setVisibility(View.GONE);
        ll_content_5.setVisibility(View.GONE);
    }


    @Optional
    @OnClick(R.id.txt_header_3)
    public void ontxtHeader3Click() {
        ll_content_1.setVisibility(View.GONE);
        ll_content_2.setVisibility(View.GONE);
        ll_content_3.setVisibility(View.VISIBLE);
        ll_content_4.setVisibility(View.GONE);
        ll_content_5.setVisibility(View.GONE);
    }

    @Optional
    @OnClick(R.id.txt_header_4)
    public void ontxtHeader4Click() {
        ll_content_1.setVisibility(View.GONE);
        ll_content_2.setVisibility(View.GONE);
        ll_content_3.setVisibility(View.GONE);
        ll_content_4.setVisibility(View.VISIBLE);
        ll_content_5.setVisibility(View.GONE);
    }

    @Optional
    @OnClick(R.id.txt_header_5)
    public void ontxtHeader5Click() {
        ll_content_1.setVisibility(View.GONE);
        ll_content_2.setVisibility(View.GONE);
        ll_content_3.setVisibility(View.GONE);
        ll_content_4.setVisibility(View.GONE);
        ll_content_5.setVisibility(View.VISIBLE);
    }



}