package com.highstreets.user.ui.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.address.adapter.AddressAdapter;
import com.highstreets.user.ui.address.add_address.AddAddressActivity;
import com.highstreets.user.ui.address.model.Address;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.CartActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity implements AddressViewInterface{

    private AddressPresenterInterface addressPresenterInterface;
    private String userId;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvAddresses)
    RecyclerView rvAddresses;
    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;

    public static Intent start(Context context) {
        return new Intent(context, AddressActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvToolbarText.setText(R.string.addresses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        addressPresenterInterface = new AddressPresenter(this);
        addressPresenterInterface.getAllAddress(userId);

        btnAddAddress.setOnClickListener(view -> startActivity(AddAddressActivity.start(AddressActivity.this)));
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        dismissProgress();
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        showProgress();
    }

    @Override
    public void setAllAddress(List<Address> addressList) {
        if (addressList != null && addressList.size() > 0){
            rvAddresses.setAdapter(new AddressAdapter(addressList));
        }
    }
}
