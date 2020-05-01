package com.highstreets.user.ui.orders.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowQRCodeDialogFragment extends BaseDialogFragment {

    private static final String PARAM_1 = "param_1";

    @BindView(R.id.ivQRCode)
    ImageView ivQRCode;

    public static ShowQRCodeDialogFragment newInstance(String qrImageUrl) {
        Bundle args = new Bundle();
        args.putString(PARAM_1, qrImageUrl);
        ShowQRCodeDialogFragment fragment = new ShowQRCodeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_q_r_code_dialog, container, false);
        ButterKnife.bind(this, view);
        if (getArguments().getString(PARAM_1) != null) {
            Glide.with(getContext())
                    .load(getArguments().getString(PARAM_1))
                    .into(ivQRCode);
        }
        return view;
    }
}
