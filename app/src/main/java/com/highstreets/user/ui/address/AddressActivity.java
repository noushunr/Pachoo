package com.highstreets.user.ui.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.highstreets.user.ui.place_order.PlaceOrderActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity implements AddressViewInterface{

    private AddressPresenterInterface addressPresenterInterface;
    private String userId;
    private List<Address> addressList;
    private Address selectedAddress;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvAddresses)
    RecyclerView rvAddresses;
    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;
    @BindView(R.id.btnContinue)
    Button btnContinue;

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
        clickHandles();
    }

    private void clickHandles() {
        btnAddAddress.setOnClickListener(view -> startActivity(AddAddressActivity.start(AddressActivity.this)));
        btnContinue.setOnClickListener(view -> {
            if (checkSelection()){
                Intent placeOrderIntent = PlaceOrderActivity.start(this);
                placeOrderIntent.putExtra(Constants.ADDRESS_ID, selectedAddress.getAddressId());
                startActivity(placeOrderIntent);
            } else {
                CommonUtils.showToast(this, getString(R.string.select_an_address));
            }
        });
    }

    private boolean checkSelection() {
        boolean isSelected = false;
        for (Address address : addressList){
            if (address.isSelected()) {
                this.selectedAddress = address;
                isSelected = true;
            }
        }
        return isSelected;
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
        this.addressList = addressList;
        if (addressList != null && addressList.size() > 0){
            rvAddresses.setAdapter(new AddressAdapter(addressList));
        }
    }
}
