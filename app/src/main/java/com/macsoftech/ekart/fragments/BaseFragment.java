package com.macsoftech.ekart.fragments;

import android.app.ProgressDialog;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;

    public void showProgress() {
        showProgress("Please wait..");
    }

    public void showProgress(String title) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setMessage(title);
        progressDialog.setCanceledOnTouchOutside(false);
        if (!getActivity().isFinishing()) {
            progressDialog.show();
        } else {
            progressDialog = null;
        }
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
