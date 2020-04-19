package com.highstreets.user.ui.address.add_address;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.tvFirstName)
    TextView tvFirstName;
    @BindView(R.id.tvLastName)
    TextView tvLastName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvDistrict)
    TextView tvDistrict;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.tvPostcode)
    TextView tvPostcode;
    @BindView(R.id.rvAddresses1)
    TextView rvAddresses1;
    @BindView(R.id.rvAddresses2)
    TextView rvAddresses2;
    @BindView(R.id.btnAddAddress)

    Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void reloadPage() {

    }
}
