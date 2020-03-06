package com.presentation.app.dealsnest.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.utils.CommonUtils;


public class NoInternetDialogFragment extends DialogFragment
        implements View.OnClickListener {

    private OnInternetConnectionCallback mListener;

    public static NoInternetDialogFragment newInstance() {
        NoInternetDialogFragment dialog = new NoInternetDialogFragment();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInternetConnectionCallback) {
            mListener = (OnInternetConnectionCallback) context;
        } else if (getTargetFragment() instanceof OnInternetConnectionCallback) {
            mListener = (OnInternetConnectionCallback) getTargetFragment();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTripDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initViews(inflater.inflate(R.layout.activity_no_internet, container, false));
    }

    private View initViews(View view) {
        view.findViewById(R.id.btnContinueShopping).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinueShopping:
                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    mListener.onRetry();
                    dismiss();
                }
                break;
        }
    }
}
