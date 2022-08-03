package com.macsoftech.ekart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.DashboardActivity;
import com.macsoftech.ekart.adapter.ComapnyNameAdapter;
import com.macsoftech.ekart.adapter.ProductNameAdapter;
import com.macsoftech.ekart.model.CompanyName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchEntityProductNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchEntityProductNameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;

    List<CompanyName> list ;

    public SearchEntityProductNameFragment() {
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
    public static SearchEntityProductNameFragment newInstance(String param1, String param2) {
        SearchEntityProductNameFragment fragment = new SearchEntityProductNameFragment();
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
        return inflater.inflate(R.layout.fragment_search_entity_productnamel, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView = view.findViewById(R.id.recycleView);
        displayProductNames(getActivity());
    }


    private void displayProductNames(Context mContext){

        list = new ArrayList<CompanyName>();
        CompanyName campanyName = new CompanyName();
        campanyName.setCompanyName("Product Name");
        campanyName.setMobileNo("Product Name1");
        campanyName.setQty("BC*DC");
        list.add(campanyName);


        CompanyName campanyName2 = new CompanyName();
        campanyName2.setCompanyName("Product Name");
        campanyName2.setMobileNo("Product Name1");
        campanyName2.setQty("BC*DC");
        list.add(campanyName2);

        CompanyName campanyName3 = new CompanyName();
        campanyName3.setCompanyName("Product Name");
        campanyName3.setMobileNo("Product Name1");
        campanyName3.setQty("BC*DC");
        list.add(campanyName3);

        CompanyName campanyName4 = new CompanyName();
        campanyName4.setCompanyName("Product Name");
        campanyName4.setMobileNo("roduct Name1");
        campanyName4.setQty("BC*DC");
        list.add(campanyName4);

        CompanyName campanyName5 = new CompanyName();
        campanyName5.setCompanyName("Product Name1");
        campanyName5.setMobileNo("Product Name1");
        campanyName5.setQty("BC*DC");
        list.add(campanyName5);

        ProductNameAdapter listAdapter = new ProductNameAdapter(list, mContext);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(listAdapter);
        listAdapter.onItemClickListener(clickListener);
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
//            int position = viewHolder.getAdapterPosition();

            DashboardActivity activity = (DashboardActivity) getActivity();
            activity.replaceBackStackFragment(new EntityDetailsFragment());
           // CompanyName item = list.get(position);


        }
    };

}