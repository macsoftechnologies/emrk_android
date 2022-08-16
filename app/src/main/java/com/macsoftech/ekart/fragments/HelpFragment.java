package com.macsoftech.ekart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.macsoftech.ekart.R;
import com.macsoftech.ekart.activities.BaseActivity;
import com.macsoftech.ekart.api.RestApi;
import com.macsoftech.ekart.databinding.FragmentHelpBinding;
import com.macsoftech.ekart.helper.Helper;
import com.macsoftech.ekart.helper.SettingsPreferences;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HelpFragment extends BaseFragment {


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
    private FragmentHelpBinding binding;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(getActivity(), view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHelpBinding.bind(view);
        ll_content_1 = view.findViewById(R.id.ll_content_1);
        ll_content_2 = view.findViewById(R.id.ll_content_2);
        ll_content_3 = view.findViewById(R.id.ll_content_3);
        ll_content_4 = view.findViewById(R.id.ll_content_4);
        ll_content_5 = view.findViewById(R.id.ll_content_5);
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

        binding.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReport();
            }
        });

        binding.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFeedBack();
            }
        });
    }

    private void saveReport() {
        if (binding.etReport.getText().toString().trim().length() == 0) {
            return;
        }
        BaseActivity.hideKeyboard(getActivity());
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("userId", SettingsPreferences.getUser(getActivity()).getUserId());
        map.put("report", binding.etReport.getText().toString().trim());
        RestApi.getInstance().getService().addReport(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    binding.etReport.setText("");
                    Helper.showShortToast(getActivity(), "Reported Successfully.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideDialog();
            }
        });
    }

    private void saveFeedBack() {
        if (binding.etFeedback.getText().toString().trim().length() == 0) {
            return;
        }
        BaseActivity.hideKeyboard(getActivity());
        showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("userId", SettingsPreferences.getUser(getActivity()).getUserId());
        map.put("feedBack", binding.etFeedback.getText().toString().trim());
        RestApi.getInstance().getService().addUserFeedback(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    binding.etFeedback.setText("");
                    Helper.showShortToast(getActivity(), "Feedback Sent Successfully.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideDialog();
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